package nov.nov;

import java.util.Random;

public class FindKthNum {

    public static void main(String[] args) {
        int[] a = new int[] {3,2,1,5,6,4};
        findKthLargest(a, 2);
    }
    public static int findKthLargest(int[] nums, int k) {
        int kthNumIndex = quickSearch(nums, 0, nums.length - 1, k);
        return nums[kthNumIndex];
    }

    private static int quickSearch(int[] nums, int left, int right, int k) {
        if (left >= right) {
            return left;
        }
        int p = partition(nums, left, right);

        if (p == k - 1) {
            return p;
        } else if (p > k - 1) {
            return quickSearch(nums, left, p - 1, k);
        } else {
            return quickSearch(nums, p + 1, right, k);
        }
    }

    private static int partition(int[] nums, int left, int right) {
        Random random = new Random();
        int ram = random.nextInt(right - left + 1) + left;
        swapNums(nums, ram, right);
        int base = nums[right];

        int i = left;
        for (int j = i; j < right; j++) {
            if (nums[j] > base) {
                swapNums(nums, i, j);
                i++;
            }
        }
        swapNums(nums, i, right);
        return i;
    }

    private static void swapNums(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
