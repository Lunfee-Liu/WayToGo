package ac.linkedlist;

import java.util.List;

public class L234回文链表 {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }

        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        ListNode tail = reverse(slow);
        return isListEqual(head, tail);

    }

    private boolean isListEqual(ListNode head, ListNode tail) {
        ListNode p1 = head;
        ListNode p2 = tail;
        while (p1 != null && p2 != null) {
            if (p1.val != p2.val) {
                return false;
            }
            p1 = p1.next;
            p2 = p2.next;
        }
        return true;
    }

    private ListNode reverse(ListNode node) {
        if (node == null || node.next == null) {
            return node;
        }

        ListNode newHead = reverse(node.next);
        node.next.next = node;
        node.next = null;
        return newHead;

    }


}
