package ac.tanxin;

public class L45跳跃游戏2 {
    public int jump(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int[] dp = new int[nums.length];
        dp[0] = 0;
        for (int i = 1; i < nums.length; i++) {
            dp[i] = Integer.MAX_VALUE;
            int back = 1;
            while (i - back >= 0) {
                if (nums[i - back] >= back) {
                    dp[i] = Math.min(dp[i], dp[i - back] + 1);
                }
                back++;
            }
        }
        return dp[nums.length - 1];
    }
}
