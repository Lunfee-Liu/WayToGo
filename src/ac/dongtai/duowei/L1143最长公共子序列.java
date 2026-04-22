package ac.dongtai.duowei;

public class L1143最长公共子序列 {
    public int longestCommonSubsequence1(String text1, String text2) {
        char[] chs1 = text1.toCharArray();
        char[] chs2 = text2.toCharArray();

        int[][] dp = new int[chs1.length + 1][chs2.length + 1];

        for(int i = 1; i <= chs1.length; i++) {
            for(int j = 1; j <= chs2.length; j++) {
                if(chs1[i - 1] == chs2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }else{
                    dp[i][j] = Math.max(dp[i][j - 1], dp[i  -1][j]);
                }
            }
        }

        return dp[chs1.length][chs2.length];
    }
    public int longestCommonSubsequence(String text1, String text2) {
        int l1 = text1.length();
        int l2 = text2.length();
        int[][] dp = new int[l1][l2];


        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                if (i == 0 && j == 0) {
                    dp[i][j] = text1.charAt(i) == text2.charAt(j) ? 1 : 0;
                } else if (i == 0) {
                    dp[i][j] = text1.charAt(i) == text2.charAt(j) ? 1 : dp[i][j - 1];
                } else if (j == 0) {
                    dp[i][j] = text1.charAt(i) == text2.charAt(j) ? 1 : dp[i - 1][j];
                } else {
                    dp[i][j] = text1.charAt(i) == text2.charAt(j) ? dp[i - 1][j - 1] + 1 : Math.max(dp[i][j - 1], dp[i - 1][j]);
                }
            }
        }
        return dp[l1 - 1][l2 - 1];
    }
}
