package ac.doublepointers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 排序
 * 确定一个值后双指针找两数之和
 * 去重
 */

public class L15三数之和 {
    public static void main(String[] args) {
        int[] nums = new int[]{-100,-70,-60,110,120,130,160};
        System.out.println(threeSum(nums));
    }
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int base = nums[i];
            if (base > 0) {
                break;
            }
            if (i > 0 && base == nums[i - 1]) {
                continue;
            }
            List<List<Integer>> rest = find(nums, i + 1, base);
            if (!rest.isEmpty()) {
                result.addAll(rest);
            }
        }
        return result;
    }

    private static List<List<Integer>> find(int[] nums, int begin, int base) {
        int left = begin;
        int right = nums.length - 1;
        List<List<Integer>> result = new ArrayList<>();
        while (left < right) {
            if (nums[left] + nums[right] == -base) {
                result.add(Arrays.asList(base, nums[left], nums[right]));
                left++;
                right--;
                // ⚠️去重
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }
            } else if(nums[left] + nums[right] > -base) {
                right--;
            } else {
                left++;
            }
        }
        return result;
    }
}
