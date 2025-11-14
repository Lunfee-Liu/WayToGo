package dec;

/**
 * dynamic programming
 */
public class LongestContinuousNonPrime {

    public static void main(String[] args) {
        int result = getLongestContinuousNonPrime(1, 100);
        System.out.println(result);
    }

    private static int getLongestContinuousNonPrime(int left, int right) {
        int length = right - left + 1;
        int[] p = new int[length];
        int max = 0;
        for (int i = 0; i < length; i++) {
            int currentNumber = left + i;
            // here is the dynamic function
            if (i == 0) {
                p[i] = isPrime(currentNumber) ? 0 : 1;
            }else if (isPrime(currentNumber)) {
                p[i] = 0;
            } else {
                p[i] = p[i - 1] + 1;
            }
            if (p[i] >= max) {
                max = p[i];
                System.out.println("current longest non prime number to:" + currentNumber);
            }
        }
        return max;
    }


    private static boolean isPrime(int number) {
        if (number <= 2) {
            return true;
        }
        for (int i = 2; i < number; i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
