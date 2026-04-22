package ac.hash;

import java.util.HashMap;

public class L1两数之和 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 4, 9, 8, 7, 5};
        int[] result = twoSum(nums, 10);
        for (int i : result) {
            System.out.println(i);
        }
    }

    public static int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> valueIndexMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (valueIndexMap.containsKey(target - nums[i])) {
                return new int[]{i, valueIndexMap.get(target - nums[i])};
            } else {
                valueIndexMap.put(nums[i], i);
            }
        }
        return null;
    }
}
