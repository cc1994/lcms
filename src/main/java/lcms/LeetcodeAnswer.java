package lcms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import lcms.struct.ListNode;

public class LeetcodeAnswer {
    private static ListNode head;
    private static ListNode start = head;

    static {
        head = new ListNode(1);
        head.next = new ListNode(2);
        head = head.next;
        head.next = new ListNode(3);
        head = head.next;
        head.next = new ListNode(4);
    }

 
}

class Solution {
    // 两个数的和
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode rtNode = result;

        // 进位
        int temp = 0;
        while (true) {
            if (l1 == null && l2 == null) {
                break;
            }
            int j = 0;
            if (l1 == null) {
                j = (l2.val + temp) % 10;
                temp = (l2.val + temp) / 10;
                l2 = l2.next;
            } else if (l2 == null) {
                j = (l1.val + temp) % 10;
                temp = (l1.val + temp) / 10;
                l1 = l1.next;
            } else {
                // 个位
                j = (l1.val + l2.val + temp) % 10;
                temp = (l1.val + l2.val + temp) / 10;
                l2 = l2.next;
                l1 = l1.next;
            }
            result.next = new ListNode(j);
            result = result.next;

        }
        if (temp != 0) {
            result.next = new ListNode(temp);
        }

        return rtNode.next;
    }

    // 在数组中找到和为指定数字的两个数
    public int[] twoSum(int[] nums, int target) {
        int[] rtn = new int[2];
        // <key, place>
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer j = 0; j < nums.length; j++) {
            int i = nums[j];
            Integer compensite = target - i;
            if (map.containsKey(compensite)) {
                rtn[0] = map.get(compensite);
                rtn[1] = j;
            }
            map.put(i, j);
        }

        return rtn;
    }

    //找到不含重复字符的最长子串的长度
    public int lengthOfLongestSubstring(String s) {
        if (s.length() < 1) {
            return 0;
        }
        int max = 0;
        int temp = 0;
        Queue<Character> queue = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            while (queue.contains(c)) {
                queue.poll();
                temp--;
            }
            queue.offer(c);
            temp++;

            if (max < temp) {
                max = temp;
            }
        }
        return max;
    }

    // 链表 每隔K个元素 翻转一次
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode rtn = new ListNode(0);
        ListNode result = rtn;
        Stack<ListNode> stack = new Stack<>();
        int i = 0;
        while (i < k && head != null) {
            stack.push(head);
            head = head.next;
            if (i == k - 1) {
                while (!stack.isEmpty()) {
                    ListNode node = stack.pop();
                    node.next = null;
                    rtn.next = node;
                    rtn = rtn.next;
                }
                i = 0;
                continue;
            }
            i++;
        }

        if (!stack.isEmpty()) {
            ListNode last = new ListNode(0);
            while (!stack.isEmpty()) {
                last = stack.pop();
            }
            rtn.next = last;
        }

        return result.next;
    }

    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode pre = dummy;
        ListNode end = dummy;

        while (end.next != null) {
            for (int i = 0; i < k && end != null; i++)
                end = end.next;
            if (end == null)
                break;
            ListNode start = pre.next;
            ListNode next = end.next;
            end.next = null;
            pre.next = reverse(start);
            start.next = next;
            pre = start;

            end = pre;
        }
        return dummy.next;
    }

    // 链表翻转
    private ListNode reverse(ListNode head) {
        ListNode pre = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    //将链表奇偶分组
    public ListNode oddEvenList(ListNode head) {
        ListNode odd = new ListNode(0);
        ListNode even = new ListNode(0);
        ListNode oddHead = odd;
        ListNode evenHead = even;
        int i = 0;
        while (head != null) {
            i++;
            if (i % 2 == 1) {
                odd.next = head;
                odd = odd.next;
            } else {
                even.next = head;
                even = even.next;

            }
            head = head.next;
        }
        even.next = null;
        odd.next = evenHead.next;
        return oddHead.next;
    }

    // 找到数组中缺失的最小的正整数
    public int firstMissingPositive(int[] nums) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                continue;
            }
            map.put(nums[i], nums[i]);
        }

        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (map.get(i) == null) {
                return i;
            }
        }
        return -1;
    }

    public int firstMissingPositive2(int[] nums) {
        int n = nums.length;

        // 基本情况
        int contains = 0;
        for (int i = 0; i < n; i++)
            if (nums[i] == 1) {
                contains++;
                break;
            }

        if (contains == 0)
            return 1;

        // nums = [1]
        if (n == 1)
            return 2;

        // 用 1 替换负数，0，
        // 和大于 n 的数
        // 在转换以后，nums 只会包含
        // 正数
        for (int i = 0; i < n; i++)
            if ((nums[i] <= 0) || (nums[i] > n))
                nums[i] = 1;

        // 使用索引和数字符号作为检查器
        // 例如，如果 nums[1] 是负数表示在数组中出现了数字 `1`
        // 如果 nums[2] 是正数 表示数字 2 没有出现
        for (int i = 0; i < n; i++) {
            int a = Math.abs(nums[i]);
            // 如果发现了一个数字 a - 改变第 a 个元素的符号
            // 注意重复元素只需操作一次
            if (a == n)
                nums[0] = -Math.abs(nums[0]);
            else
                nums[a] = -Math.abs(nums[a]);
        }

        // 现在第一个正数的下标
        // 就是第一个缺失的数
        for (int i = 1; i < n; i++) {
            if (nums[i] > 0)
                return i;
        }

        if (nums[0] > 0)
            return n;

        return n + 1;
    }

    // 给定数字交换一次变成最大数字
    public int maximumSwap2(int num) {
        // input = 2736
        int[] l = new int[9];
        // 1 将input按位(从低位到高位)取出，[6, 3, 7, 2]
        int len = 0;
        while(num>0){
            l[len] = num%10;
            num /= 10;
            len ++;
        }
        // 2 从高位起按位比较（倒序），找出每个高位后所有数字的最大值
        // 若最大值>高位值，交换， break 
        // 否则，continue
        for(int i=len-1;i>=0;i--){
            int max=0, id=0;
            for(int j=i-1;j>=0;j--){
                if(l[j]>=max){
                    max = l[j];
                    id = j;
                }
            }
            if(max>l[i]){
                l[id] = l[i];
                l[i] = max;
                break;
            }
        }
        // 3 输出结果
        int result = 0;
        for(int k=len-1;k>=0;k--){
            result = result*10 + l[k];
        }
        return result;
    }
    
    // 将字符串中空格替换为%20
    public static void replaceBlank() {
        String str = "we are happy";
        char[] charOld = str.toCharArray();
        char[] charNew = new char[100];
        for (int j = 0; j < charOld.length; j++) {
            charNew[j] = charOld[j];
        }
        int blank = 0;
        for (int i = 0; i < charNew.length; i++) {
            if (charNew[i] == ' ') {
                blank++;
            }
        }
        int lengthFront = charOld.length - 1;

        int lengthBack = charOld.length + 2 * blank - 1;

        while (lengthFront >= 0 && lengthBack >= 0) {
            if (charNew[lengthFront] != ' ') {
                charNew[lengthBack--] = charNew[lengthFront];
            } else {
                charNew[lengthBack--] = '0';
                charNew[lengthBack--] = '2';
                charNew[lengthBack--] = '%';
            }
            lengthFront--;
        }
        System.out.println(charNew);
    }
    // 查找数组中三数之和为0
    // 首先对数组进行从小到大排序，假设这3个数的数组下标为i、j、k. 对i采用一个循环进行遍历，i:0 —>length-1；而最开始 j=i+1，k=length-1。进行下面的操作：
    // 如果i、j、k对应的三数之和小于0，则j+1
    // 如果i、j、k对应的三数之和等于0，则这三个数满足要求，将去放入集合中。并j+1、k-1
    // 如果i、j、k对应的三数之和大于0，则k-1
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> rtn = new ArrayList<>();
        for(int i = 0; i < nums.length - 1; i++) {
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    List<Integer> subList = Arrays.asList(i, j, k);
                    rtn.add(subList);
                } else if (nums[i] + nums[j] + nums[k] < 0) {
                    j = j+1;
                } else if (nums[i] + nums[j] + nums[k] > 0) {
                    k = k - 1;
                }
            }
        }
        
        return rtn;
    }
    
    // 合并2个有序list
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode result = new ListNode(0);
        ListNode rtn = result;
        while(l1 != null && l2 !=null){
            if(l1.val >= l2.val){
                result.next = l2;
                l2 = l2.next;
            }else if(l1.val < l2.val){
                result.next = l1;
                l1 = l1.next;
            }
            result = result.next;
        }
        if(l1 != null){
            result.next = l1;
        }
        if(l2 != null){
            result.next = l2;
        }
        return rtn.next;
    }
    
    // 合并k个有序list， 分治法
    public ListNode mergeKLists(ListNode[] lists) {
        if(lists.length == 1 )
            return lists[0];
        else if(lists == null)
            return null;
        return MSort(lists,0,lists.length - 1);
    }
    
    public ListNode MSort(ListNode[] lists, int low, int high){ 
        if(low < high){  
            int mid = (low+high)/2;
            ListNode leftlist = MSort(lists,low,mid);
            ListNode rightlist = MSort(lists,mid+1,high);
            return mergeTwoLists(leftlist,rightlist);  
        }  
        else if(low == high)
        {
            return lists[low];
        }
        else 
            return null; 
    }
    
    public ListNode mergeKListsByPriorityQueue(ListNode[] lists) {
        if(lists == null || lists.length < 0){
            return null;
        }
         PriorityQueue<Integer> queue = new PriorityQueue();
         for(ListNode node:lists){
             while(node != null){
                 queue.add(node.val);
                 node = node.next;
             }
         }
         ListNode res = new ListNode(0);
         ListNode tmp= res;
         while(!queue.isEmpty()){
             ListNode temp = new ListNode(queue.poll());
             tmp.next = temp;
             tmp = tmp.next;
         }
         return res.next;
     }
    
    public int solution(int[] A) {
        // 奇数位1有几个
        int oddOneCount = 0;
        // 偶数位0有几个
        int evenZeroCount = 0;
        
        // 偶数位1有几个
        int evenOneCount = 0;
        // 奇数位0有几个
        int oddZeroCount = 0;
        
        for (int i = 0; i < A.length; i++) {
            if (A[i] == 0 && i % 2 == 1) {
                // 0-奇数位
                oddZeroCount++;
            }else if (A[i] == 0 && i % 2 == 0) {
                // 0-偶数位
                evenZeroCount++;
            } else if (A[i] == 1 && i % 2 == 1) {
                // 1-奇数位
                oddOneCount++;
            } else if (A[i] == 1 && i % 2 == 0) {
                // 1-偶数位
                evenOneCount++;
            }
        }

        return Math.min((oddZeroCount + evenOneCount), (oddOneCount + evenZeroCount));
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        int count = solution.solution(null);
        System.out.println(count);
    }
}