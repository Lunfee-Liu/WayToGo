package ac.hash;

import java.util.HashSet;
import java.util.HashSet;
import java.util.HashSet;

public class L128最长连续序列 {
    public static void main(String[] args) {
        int[] nums = new int[]{0,3,7,2,5,8,4,6,0,1};
        System.out.println(longestConsecutive(nums));
    }
    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        int result = 1;
        for (int num : nums) {
            if (set.contains(num - 1)) {
                continue;
            }
            int max = 1;
            int cur = num;
            while (set.contains(cur + 1)) {
                max++;
                cur++;
            }
            result = Math.max(result, max);
        }
        return result;
    }
}
