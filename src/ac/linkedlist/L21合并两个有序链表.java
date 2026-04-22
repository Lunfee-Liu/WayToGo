package ac.linkedlist;

public class L21合并两个有序链表 {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }

        if (list2 == null) {
            return list1;
        }

        ListNode node1 = list1;
        ListNode node2 = list2;

        ListNode dummy = new ListNode();
        ListNode cur = dummy;
        while (node1 != null && node2 != null) {
            if (node1.val < node2.val) {

                cur.next = node1;
                node1 = node1.next;
            } else {
                cur.next = node2;
                node2 = node2.next;
            }
            cur = cur.next;
        }
        if (node1 != null) {
            cur.next = node1;
        }
        if (node2 != null) {
            cur.next = node2;
        }

        return dummy.next;
    }
}
