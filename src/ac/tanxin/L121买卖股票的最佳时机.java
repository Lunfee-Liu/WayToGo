package ac.tanxin;

public class L121买卖股票的最佳时机 {
    public int maxProfit(int[] prices) {
        int[] dp = new int[prices.length];
        dp[0] = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            dp[i] = Math.max(dp[i - 1], prices[i] - min);
            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return dp[prices.length - 1];
    }
    public int maxProfit1(int[] prices) {
        int max = 0;
        int min = prices[0];

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] > min) {
                max = Math.max(max, prices[i] - min);
            }

            if (prices[i] < min) {
                min = prices[i];
            }
        }
        return max;
    }
}
