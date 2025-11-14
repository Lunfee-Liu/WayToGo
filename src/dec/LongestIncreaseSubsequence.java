package dec;

public class LongestIncreaseSubsequence {
    public static void main(String[] args) {
        int[] input = new int[]{0, 1, 0, 3, 2, 3};
        int result = getLongestIncreaseSubsequence(input);
        System.out.println(result);
    }

    private static int getLongestIncreaseSubsequence(int[] input) {
        if (input.length <= 1) {
            return input.length;
        }
        int[] p = new int[input.length];
        p[0] = 1;

        int max = 1;
        for (int i = 1; i < input.length; i++) {
            p[i] = 1;
            for (int j = i -1; j >= 0; j--) {
                if (input[i] > input[j]) {
                    p[i] = Math.max(p[i], p[j] + 1);
                    max = Math.max(max, p[i]);
                }
            }
        }
        return max;
    }
}
