package dec;

public class ClimbStairs {
    public static void main(String[] args) {
        int steps = 10;
        int result = solutionsOfClimbing(steps);
        System.out.println(result);
    }

    private static int solutionsOfClimbing(int steps) {
        if (steps <= 2) {
            return steps;
        }
        int[] p = new int[steps];
        p[0] = 1;
        p[1] = 2;
        // here is the dynamic function
        for (int i = 2; i < steps; i++) {
            p[i] = p[i - 1] + p[i - 2];
        }
        return p[steps - 1];
    }
}
