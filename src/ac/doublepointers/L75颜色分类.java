package ac.doublepointers;

import java.util.Arrays;

public class L75颜色分类 {
    public static void main(String[] args) {
        int[] nums = new int[]{2,0,2,1,1,0};
        sortColors2(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int zeroNum = 0;
        while (left <= right) {
            if (nums[left] == 0) {
                zeroNum++;
                left++;
                continue;
            }
             if (nums[right] == 0) {
                 zeroNum++;
                 swapNums(nums, left, right);
                 left++;
             }
            right--;
        }

        left = zeroNum;
        right = nums.length - 1;
        while (left < right) {
            if (nums[left] == 1) {
                left++;
                continue;
            }
            if (nums[right] == 1) {
                swapNums(nums, left, right);
                left++;
            }
            right--;
        }


    }

    public static void sortColors2(int[] nums) {
        if (nums.length == 1) {return;}
        int left = 0;
        int right = nums.length - 1;

        int cur = 0;
        while (cur <= right) {
            if (nums[cur] == 0) {
                swapNums(nums, left, cur);
                 cur++;
                left++;
            } else if (nums[cur] == 2) {
                swapNums(nums, right, cur);
                right--;
            } else {
                cur++;
            }
        }

    }

    private static void swapNums(int[] nums, int left, int right) {
        int temp = nums[right];
        nums[right] = nums[left];
        nums[left] = temp;
    }
}
