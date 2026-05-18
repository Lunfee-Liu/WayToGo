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
        LinkedList<Integer> deque = new LinkedList<>();
        for (int right = 0; right < nums.length; right++) {
            while (!deque.isEmpty() && nums[deque.peekLast()] <= nums[right]) {
                deque.pollLast();
            }
            deque.addLast(right);
            int left = right - k + 1;
            if (deque.peek() < left) {
                deque.poll();
            }
            if (right >= k - 1) {
                result[right - k + 1] = nums[deque.peek()];
            }
        }
        return result;
    }

}
