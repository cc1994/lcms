package lcms;

import lcms.struct.ListNode;

public class ListRelatedAnswer {
    // 判断两个单链表是否相交
    // 1.无环单链表是否相交的判断方法：stack/len2-len1处向后找
    // 2.有环单链表是否相交的判断方法：
    // 先比较两个链表的入环节点是否相等，若想等，则相交，
    // 若不想等，则从某个链表的入环节点开始循环一周，判断是否有节点等于另一个链表的入环节点，若等于，则相交，否则不相交。
    // 3.是否有环：快慢指针
    // 4.寻找环入口
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        // 退出时到链表尾部或者有环
        while(fast != null && fast.next != null){
            fast = fast.next.next;
            slow = slow.next;
            if(fast == slow){
                break;
            }
        }        
        //无环
        if (fast == null || fast.next == null){
            return null;
        }
        //有环
        slow = head;
        while (fast != slow) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }
}
