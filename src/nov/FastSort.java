package nov;

public class FastSort {
    // 5 2 9 6 4 1 7
    public int[] sort(int [] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int left, int right) {
        if (left >= right) {
            return;
        }
        int p = partition(nums, left, right);

        quickSort(nums, left, p - 1);
        quickSort(nums, p + 1, right);
    }

    private int partition(int[] nums, int left, int right) {
        int target = nums[right];
        int i = left;
        for (int j = left; j < right; j++) {
            if (nums[j] < target) {
                swap(nums, i, j);
                i++;
            }
        }
        swap(nums, i, right);
        return i;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

}
