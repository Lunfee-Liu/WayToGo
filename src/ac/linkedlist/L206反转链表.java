package ac.linkedlist;

public class L206反转链表 {
    // 迭代法
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = head;
        ListNode next = head.next;
        cur.next = null;



        while (next != null) {
            ListNode temp = next.next;
            next.next = cur;

            cur = next;
            next = temp;
        }

        return cur;
    }

    public ListNode reverseList1(ListNode head) {
        // 注意这里返回的是反转链表的新的头节点
        if (head == null || head.next == null) {
            return head;
        }
        ListNode temp = head.next;
        head.next = null;
        ListNode newHead = reverseList1(temp);
        temp.next = head;
        return newHead;
    }
}
