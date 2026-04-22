package ac.doublepointers;

import java.util.Arrays;

public class L2383移动零 {
    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,3,12};
        moveZeroes(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void moveZeroes(int[] nums) {
        int firstZero = 0;
        while (firstZero < nums.length && nums[firstZero] != 0) {
            firstZero++;
        }
        if (firstZero == nums.length) {
            return;
        }

        int left = firstZero;
        int right = left + 1;
        while (right < nums.length) {
            if (nums[right] != 0) {
                nums[left] = nums[right];
                left++;
                nums[right] = 0;
            }
            right++;
        }
    }
}
