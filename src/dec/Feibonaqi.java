package dec;

public class Feibonaqi {
    public static void main(String[] args) {
        System.out.println(f(1));
    }

    private static int f(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        return f(n - 1) + f(n - 2);
    }

    private static int f2(int n) {
        if (n < 2) {
            return n;
        }
        int[] p = new int[n + 1];
        p[0] = 0;
        p[1] = 1;
        for (int i = 2; i <= n; i++) {
            p[i] = p[i - 1] + p [i - 2];
        }
        return p[n];
    }
}
