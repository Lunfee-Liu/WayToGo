package ac.linkedlist;

public class L2两数相加 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        if (l1 == null) {
            return l2;
        }
        if (l2 == null) {
            return l1;
        }

        ListNode p1 = l1;
        ListNode p2 = l2;
        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        int carry = 0;

        while (p1 != null && p2 != null) {
            int sum = p1.val + p2.val + carry;
            carry = sum  / 10;
            int value = sum % 10;

            cur.next = new ListNode(value);
            cur = cur.next;
            p1 = p1.next;
            p2 = p2.next;
        }

        while (p1 != null) {
            int sum = p1.val + carry;
            carry = sum  / 10;
            int value = sum % 10;

            cur.next = new ListNode(value);
            cur = cur.next;
            p1 = p1.next;
        }

        while (p2 != null) {
            int sum = p2.val + carry;
            carry = sum  / 10;
            int value = sum % 10;

            cur.next = new ListNode(value);
            cur = cur.next;
            p2 = p2.next;
        }

        if (carry == 1) {
            cur.next = new ListNode(1);
        }

        return dummy.next;
    }
}
