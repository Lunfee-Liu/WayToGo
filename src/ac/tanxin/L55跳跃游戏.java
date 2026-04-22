package ac.tanxin;

public class L55跳跃游戏 {
    // dp 超时，时间复杂度 n2
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        boolean[] dp = new boolean[nums.length];
        dp[0] = true;
        for (int i = 1; i < nums.length; i++) {
            int back = 1;
            while (i - back >= 0) {
                dp[i] |= dp[i - back] && nums[i - back] >= back;
                back++;
            }
        }
        return dp[nums.length - 1];
    }

    // 贪心
    public boolean canJump1(int[] nums) {
        int maxReach = 0;

        for (int i = 0; i < nums.length; i++) {
            if (i > maxReach) {
                return false;
            }
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= nums.length - 1) {
                return true;
            }
        }
        return false;
    }
}
