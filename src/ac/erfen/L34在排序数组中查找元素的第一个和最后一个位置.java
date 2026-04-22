package ac.erfen;

public class L34在排序数组中查找元素的第一个和最后一个位置 {
    public int[] searchRange(int[] nums, int target) {
        if (nums.length == 0) {
            return new int[]{-1, -1};
        }
        int left = searchLeft(nums, target);
        if (left == -1) {
            return new int[]{-1, -1};
        }
        int right;
        for (right = left; right < nums.length; right++) {
            if (nums[right] != nums[left]) {
                right--;
                break;
            }
        }
        return new int[]{left, right};
    }

    private int searchLeft(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;

            if (target > nums[mid]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        if (nums[left] != target) {
            return -1;
        }
        return left;
    }
}
