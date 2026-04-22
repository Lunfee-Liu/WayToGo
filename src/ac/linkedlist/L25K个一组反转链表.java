package ac.linkedlist;

public class L25K个一组反转链表 {
    public ListNode reverseKGroup(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }
        // 创建一个指针，移动到第k个节点（从1开始）
        ListNode cur = head;
        int p = k;
        // 不足k个不反转（这里可以定制，不足的话也可以反转）
        while (--p > 0) {
            cur = cur.next;
            if (cur == null) {
                return head;
            }
        }

        // 记录下一批的头节点
        ListNode temp = cur.next;
        // 斩断尾节点
        cur.next = null;

        // 这里递归，先排后面的
        ListNode ready =  reverseKGroup(temp, k);
        // 反转当前的，返回新头节点
        ListNode newHead = reverseList(head);
        // 当前尾节点指向新头
        head.next = ready;
        return newHead;
    }

    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode temp = head.next;
        head.next = null;
        ListNode newHead = reverseList(temp);
        temp.next = head;
        return newHead;
    }
}
