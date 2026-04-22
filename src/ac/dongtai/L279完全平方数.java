package ac.dongtai;

public class L279完全平方数 {
    public int numSquares(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 1; i <= n; i++) {
            int min = 4;
            for (int j = 0; j * j <= i; j++) {
                dp[i] = Math.min(dp[i - j * j] + 1, min);
            }
        }
        return dp[n];
    }
}
