package ac.window;

import java.util.Arrays;
import java.util.LinkedList;

public class L239滑动窗口最大值 {
    public static void main(String[] args) {
        int[] nums = new int[] {1,3,-1,-3,5,3,6,7};
        System.out.println(Arrays.toString(maxSlidingWindow(nums, 3)));

    }
    public static int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        int left = 0;
        int right = k - 1;
        int index = 0;
        LinkedList<Integer> deque = new LinkedList<>();
        for(int i = left; i < right; i++) {
            while(!deque.isEmpty() && nums[deque.peekLast()] <= nums[i]) {
                deque.pollLast();
            }
            deque.addLast(i);
        }
        while(right < nums.length) {
            //
            while(!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                deque.pollLast();
            }
            deque.addLast(right);
            while(deque.peek() < left) {
                deque.poll();
            }
            result[index++] = nums[deque.peek()];
            //
            left++;
            right++;
        }
        return result;
    }

}
