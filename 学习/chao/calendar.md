# 日历系统设计文档

## 一、整体架构

```
┌─────────────────────────────────────────────────────┐
│                   Client (Web/Mobile/CLI)            │
└───────────────────────────┬─────────────────────────┘
                            │ HTTPS / REST
┌───────────────────────────▼─────────────────────────┐
│                    API Gateway                       │
│  (Auth middleware · Rate limiting · Request routing) │
└──┬───────────┬───────────┬────────────┬─────────────┘
   │           │           │            │
┌──▼──┐  ┌────▼───┐  ┌────▼───┐  ┌────▼──────────┐
│User │  │Calendar│  │Invite  │  │Reminder       │
│Svc  │  │Svc     │  │Svc     │  │Svc            │
└──┬──┘  └────┬───┘  └────┬───┘  └────┬──────────┘
   │          │            │           │
   └──────────┴────────────┴─────┬─────┘
                                 │
                    ┌────────────▼────────────┐
                    │   Relational DB (SQL)   │
                    │   users / events /      │
                    │   attendees / reminders │
                    └─────────────────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   Message Queue         │
                    │  (Redis / RabbitMQ)     │
                    └────────────┬────────────┘
                                 │
                    ┌────────────▼────────────┐
                    │   Email Worker          │
                    │  (SMTP / SendGrid)      │
                    └─────────────────────────┘
```

---

## 二、核心模块

### 2.1 User Service
- 注册 / 登录（JWT Token 认证）
- 用户偏好：时区、语言、默认提醒时间
- OAuth 接入（Google / Microsoft）可选扩展

### 2.2 Calendar Service
- 每个用户拥有一或多个日历（个人 / 团队 / 订阅）
- 事件 CRUD，支持单次事件和重复事件（RFC 5545 RRULE）
- 所有时间存储为 **UTC**，展示时转为用户时区
- 支持 iCalendar (.ics) 导入导出

### 2.3 Invitation Service
- 邀请站内用户或外部邮件地址
- 每位受邀人持有独立状态机（见下）
- 邀请邮件含唯一 Token 链接，外部用户无需注册即可响应
- 组织者可看到实时出席率

### 2.4 Reminder Service
- 每个事件 × 每位参与者可独立配置提醒规则
- 定时任务（Cron / Celery Beat）扫描到期提醒并入队
- Worker 消费队列后发送邮件，并记录发送结果
- 支持重试（退避策略），去重防止重复发送

---

## 三、数据模型

```sql
-- 用户
users (
  id          UUID PRIMARY KEY,
  email       VARCHAR UNIQUE NOT NULL,
  name        VARCHAR,
  timezone    VARCHAR DEFAULT 'UTC',
  created_at  TIMESTAMPTZ
)

-- 日历
calendars (
  id          UUID PRIMARY KEY,
  owner_id    UUID REFERENCES users,
  name        VARCHAR,
  color       VARCHAR,
  is_public   BOOLEAN DEFAULT FALSE
)

-- 事件
events (
  id              UUID PRIMARY KEY,
  calendar_id     UUID REFERENCES calendars,
  creator_id      UUID REFERENCES users,
  title           VARCHAR NOT NULL,
  description     TEXT,
  location        VARCHAR,
  meeting_url     VARCHAR,
  start_time      TIMESTAMPTZ NOT NULL,
  end_time        TIMESTAMPTZ NOT NULL,
  timezone        VARCHAR,              -- 显示用，存原始时区名称
  recurrence_rule VARCHAR,             -- RFC 5545 RRULE 字符串
  status          VARCHAR CHECK (status IN ('confirmed','cancelled','tentative')),
  created_at      TIMESTAMPTZ,
  updated_at      TIMESTAMPTZ
)

-- 受邀人 / 出席状态
event_attendees (
  id          UUID PRIMARY KEY,
  event_id    UUID REFERENCES events,
  user_id     UUID REFERENCES users,   -- 站内用户，可为 NULL
  email       VARCHAR NOT NULL,         -- 外部用户或同步站内邮件
  role        VARCHAR CHECK (role IN ('organizer','attendee','optional')),
  status      VARCHAR CHECK (status IN ('pending','accepted','declined','tentative')),
  token       VARCHAR UNIQUE,           -- 外部响应一次性 Token
  notified_at TIMESTAMPTZ
)

-- 提醒规则
reminders (
  id              UUID PRIMARY KEY,
  event_id        UUID REFERENCES events,
  user_id         UUID REFERENCES users,
  remind_before   INTEGER NOT NULL,     -- 单位：分钟（如 15, 60, 1440）
  channel         VARCHAR DEFAULT 'email',
  status          VARCHAR CHECK (status IN ('pending','queued','sent','failed')),
  scheduled_at    TIMESTAMPTZ,          -- = event.start_time - remind_before * interval '1 min'
  sent_at         TIMESTAMPTZ,
  retry_count     INTEGER DEFAULT 0,
  idempotency_key VARCHAR UNIQUE        -- event_id + user_id + remind_before 的哈希
)
```

**受邀人状态机：**
```
pending → accepted
        → declined
        → tentative
```

---

## 四、API 设计

### 认证
所有接口（除 `PUT /invitations/respond`）均需在 Header 携带 `Authorization: Bearer <jwt>`。

### 事件管理

| Method | Path | 说明 |
|--------|------|------|
| `POST`   | `/events` | 创建事件，Body 可同时携带 `attendees` 和 `reminders` |
| `GET`    | `/events/{id}` | 获取事件详情（含参与者列表） |
| `PUT`    | `/events/{id}` | 更新事件（修改时间会重新计算 reminders.scheduled_at） |
| `DELETE` | `/events/{id}` | 软删除（status → cancelled），触发取消通知 |
| `GET`    | `/calendars/{id}/events` | 按时间范围查询，`?start=&end=&view=day\|week\|month` |

### 日程视图

| Method | Path | 说明 |
|--------|------|------|
| `GET` | `/users/me/schedule` | 聚合用户所有日历的事件，支持 `?start=&end=` |
| `GET` | `/users/me/schedule/conflicts` | 返回所有时间重叠的事件对 |

### 会议邀请

| Method | Path | 说明 |
|--------|------|------|
| `POST` | `/events/{id}/invitations` | 批量邀请，Body：`{emails: [...]}` |
| `PUT`  | `/events/{id}/invitations/{attendee_id}` | 更新 RSVP，Body：`{status: accepted\|declined\|tentative}` |
| `GET`  | `/events/{id}/invitations` | 查看出席情况（接受/拒绝/待定人数） |
| `PUT`  | `/invitations/respond?token={token}&status={status}` | 外部用户无需登录响应邀请 |

### 提醒

| Method | Path | 说明 |
|--------|------|------|
| `POST`   | `/events/{id}/reminders` | 添加提醒，Body：`{remind_before: 30}` |
| `DELETE` | `/reminders/{id}` | 删除提醒 |

---

## 五、关键流程

### 5.1 创建事件 + 发起邀请

```
Client ──POST /events──────────────────────────────────────────►
       {title, start_time, end_time,
        attendees: ["a@x.com","b@x.com"],
        reminders: [{remind_before: 30}]}

API Gateway
  │
  ├─ Calendar Svc ──► INSERT INTO events
  │
  ├─ Invite Svc ────► INSERT INTO event_attendees (status=pending, token=uuid)
  │                    × N 条（每位受邀人一条）
  │
  ├─ Reminder Svc ──► INSERT INTO reminders
  │                    scheduled_at = start_time - 30min
  │
  └─ 异步入队 "send_invitation_email" 任务
       │
       Email Worker ──► 渲染邀请邮件（含 RSVP 链接）──► SMTP/SendGrid
```

### 5.2 受邀人响应 RSVP

```
受邀人收到邮件，点击"接受"链接
  │
  PUT /invitations/respond?token=<uuid>&status=accepted
  │
  Invite Svc ──► UPDATE event_attendees SET status='accepted' WHERE token=<uuid>
  │
  └─ 可选：异步通知组织者出席状态变更
```

### 5.3 会前提醒触发（核心异步流程）

```
Cron Job（每 1 分钟执行）
  │
  SELECT id FROM reminders
    WHERE status = 'pending'
      AND scheduled_at <= NOW() + INTERVAL '1 minute'
  │
  ├─ UPDATE reminders SET status='queued' WHERE id IN (...)
  │
  └─ PUBLISH 消息到 Queue（payload: reminder_id）

Email Worker（消费队列）
  │
  ├─ 查询 reminder + event + user 信息
  ├─ 渲染提醒邮件（"您的会议《X》将在 30 分钟后开始"）
  ├─ 发送邮件
  │
  ├─ 成功 ──► UPDATE reminders SET status='sent', sent_at=NOW()
  └─ 失败 ──► retry_count += 1，延迟重新入队（最多 3 次）
              retry_count >= 3 ──► status='failed'，记录错误日志
```

---

## 六、关键设计决策

| 问题 | 决策 | 理由 |
|------|------|------|
| **时区处理** | 所有时间存 UTC，前端负责本地化展示 | 跨时区会议不产生歧义 |
| **重复事件** | 存 RRULE 字符串（RFC 5545），查询时内存展开 | 避免数据库行数爆炸 |
| **外部受邀人** | 唯一 Token 链接，无需注册即可 RSVP | 降低参与门槛 |
| **提醒去重** | `idempotency_key = hash(event_id + user_id + remind_before)` | Worker 崩溃重试时不重复发邮件 |
| **提醒扫描粒度** | Cron 每 1 分钟，提前 1 分钟入队 | 简单可靠，误差 < 1 分钟 |
| **软删除** | DELETE → `status='cancelled'`，不物理删除 | 保留历史记录，支持发送取消通知 |
| **事件更新联动** | 修改 start_time 时重新计算并更新所有关联 reminders.scheduled_at | 确保提醒时间始终与事件同步 |

---

## 七、目录结构建议（以 Python/FastAPI 为例）

```
calendar/
├── api/
│   ├── routers/
│   │   ├── events.py
│   │   ├── invitations.py
│   │   ├── reminders.py
│   │   ├── schedule.py
│   │   └── users.py
│   └── middleware/
│       └── auth.py
├── services/
│   ├── calendar_service.py
│   ├── invite_service.py
│   └── reminder_service.py
├── models/
│   └── db.py               # ORM 模型（User, Calendar, Event, EventAttendee, Reminder）
├── workers/
│   └── email_worker.py     # 消费队列，发送邮件
├── scheduler/
│   └── cron.py             # 每分钟扫描到期提醒
├── templates/
│   ├── invitation.html
│   └── reminder.html
└── config.py
```

---

## 八、扩展方向（不在初版范围）

- **Google Calendar / Outlook 双向同步**（CalDAV / Microsoft Graph API）
- **视频会议集成**（Zoom / Teams 自动生成 `meeting_url`）
- **团队可用性视图**（Free/Busy 聚合查询）
- **WebSocket 实时推送**（日程变更即时通知参与者）
- **移动端推送**（APNs / FCM）
- **细粒度权限**（日历共享、只读 / 编辑权限）

---

## 九、验证方式

1. **事件创建**：`POST /events` → 确认 DB 写入、邀请邮件发出、reminders 记录生成
2. **RSVP 流程**：点击邮件 Token 链接 → 确认 `event_attendees.status` 从 `pending` 变为 `accepted`
3. **日程查看**：`GET /users/me/schedule?start=X&end=Y` → 返回正确事件列表，时区转换正确
4. **会前提醒**：创建 `start_time = now+2min` 的事件，设 `remind_before=1`，约 1 分钟后邮件到达
5. **重复事件**：创建 `recurrence_rule=FREQ=WEEKLY` 的事件，跨周查询能正确展开多个实例
6. **冲突检测**：创建两个时间重叠事件，`GET /schedule/conflicts` 返回冲突信息
7. **提醒去重**：模拟 Worker 崩溃重启，确认同一提醒邮件只发送一次
