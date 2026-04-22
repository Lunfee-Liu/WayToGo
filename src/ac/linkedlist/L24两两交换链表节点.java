package ac.linkedlist;

public class L24两两交换链表节点 {
    public ListNode swapPairs(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode ready = swapPairs(head.next.next);
        ListNode temp = head.next;
        head.next = ready;
        temp.next = head;
        return temp;
    }

    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode result = head.next;

        ListNode p1 = head;
        ListNode p2 = head.next;
        ListNode nextLevelHead = head.next.next;

        while (p1 != null && p2 != null) {
            p1.next = nextLevelHead == null || nextLevelHead.next == null? nextLevelHead : nextLevelHead.next;
            p2.next = p1;

            p1 = nextLevelHead;
            p2 = p1 == null ? null : p1.next;
            nextLevelHead = p2 == null ? null : p2.next;
        }
        return result;
    }
}
