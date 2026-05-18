# 单品 500 TPS 库存扣件系统设计

## 核心问题

1. **数据库行锁竞争** — 500 TPS 全部 UPDATE 同一行，MySQL InnoDB 行锁 + MVCC 下，实际吞吐天花板远低于 500 TPS（单行约 100-200 TPS 已接近极限）
2. **超卖（Overselling）** — 并发读写下，不加正确锁必然超出库存
3. **重复扣减** — 客户端重试 / 超时重试会多扣
4. **库存准确性 vs 高性能的矛盾** — 强一致 = 慢，最终一致 = 可能超卖
5. **热点 key 的 Redis 瓶颈** — 单 key 单分片，Redis 单实例 QPS 能扛 5w+，但网络 IO 和命令排队仍是问题

---

## 推荐方案：Redis LUA + 异步对账

### 整体架构

```
Client → Gateway (限流/鉴权) → MQ (削峰) → Cache Service (Redis LUA) → DB (异步落库 + 对账)
```

### 分层设计

#### 1. 接入层 — 限流 + 前置过滤

- 用户粒度限流（令牌桶）：同一用户每秒最多 N 次
- 水位限流：当前库存 ≤ 0 直接返回「已售罄」，不再放行
- 防重 token：每个请求携带唯一 token，Redis setnx 去重

#### 2. 缓存层 — Redis LUA 脚本（核心扣减）

核心思路：用 LUA 保证 `check + deduct` 的原子性，避免超卖。

```lua
-- inventory_deduct.lua
local key = KEYS[1]          -- 库存 key，如 "stock:item_123"
local deduct_qty = tonumber(ARGV[1])
local current = redis.call("GET", key)
if not current or tonumber(current) < deduct_qty then
    return -1  -- 库存不足
end
redis.call("DECRBY", key, deduct_qty)
return redis.call("GET", key)  -- 返回剩余库存
```

- Redis 单实例单 key 可承受 **5-10w QPS**，500 TPS 绰绰有余
- 热点 key 解决方案：若压力更大（如万级 TPS），可把库存拆成 N 个分片（`stock:item_123:0~15`），LUA 随机选分片扣减，DB 层最终汇总

#### 3. MQ 层 — 削峰 + 异步落库

扣减成功后，生产消息 → 消费者批量写入 DB。

- 发送时机：Redis 扣减成功立即发送 MQ
- 消费者：批量攒 100 条或 100ms 窗口，一次批量 UPDATE
- 目的：将 500 TPS 的随机写转化为 5 TPS 的批量写，DB 完全无压力

#### 4. 持久化层 — DB 异步落库 + 最终一致性

```sql
-- 批量扣减（消费者攒批后执行）
UPDATE inventory SET stock = stock - ? WHERE sku_id = ? AND stock >= ?;

-- 记录扣减流水用于对账
INSERT INTO inventory_flow (token, sku_id, qty, status) VALUES (?, ?, ?, 'DEDUCTED');
```

- 使用 `stock >= ?` 条件防止异步批量中的超卖（兜底）
- 唯一 token 做幂等

#### 5. 对账层 — 兜底

- 每隔 N 分钟，比对 Redis 中的剩余库存 vs DB 的 `总库存 - SUM(扣减流水)`
- 不一致则报警 + 自动校正（以 Redis 为准，因为 Redis 是实际扣减来源）

---

## 关键细节处理

### 超卖的最后一层防线

```
Redis 扣 + DB 扣，两层都有库存检查
```

即便 Redis 出现异常（如主从切换丢数据），DB 的 `WHERE stock >= qty` 也能兜住。

### 库存预热

启动时从 DB 加载库存到 Redis，LUA 里加一个 `EXISTS` 判断，不存在才加载，避免重复加载覆盖。

### 扣减回滚（订单取消）

用户下单后 30min 不支付 → 回滚库存。

- 方案：延迟消息（RocketMQ 延迟消息 / Redis ZSET 轮询）
- 回滚时重新执行 `INCRBY`，将 DB + Redis 同时恢复

### 监控指标

| 指标 | 说明 |
|------|------|
| Redis 扣减耗时 P99 | < 5ms |
| MQ 堆积量 | 持续上升则扩容消费者 |
| DB 写入延迟 | 批量写应 < 20ms |
| 对账差异率 | > 0.1% 告警 |

---

## 为什么不选其他方案

| 方案 | 缺点 |
|------|------|
| 纯 DB 乐观锁 | 500 TPS 下大量回滚重试，实际吞吐极低 |
| 纯 DB 悲观锁 `SELECT FOR UPDATE` | 行锁排队，吞吐 ≤ 200 TPS |
| 纯 Redis（不回写 DB） | Redis 宕机丢数据，无持久化保证 |
| 分布式锁 + DB | 锁开销大，故障时锁超时导致超卖 |

---

## 总结

```
客户端 → [限流 + token 防重] → [Redis LUA 原子扣减] → [MQ 异步落库] → [定时对账]
```

- **TPS 500**：Redis 单 key 轻松支撑
- **不超卖**：LUA 原子的 check-then-deduct + DB 层 WHERE 条件双重保障
- **不重复**：全局唯一 token 幂等
- **最终一致**：对账机制兜底

对于更高压力（单 SKU 万级 TPS），在上述基础上引入**库存分片**即可。
