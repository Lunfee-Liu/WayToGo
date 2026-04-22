package ac.erfen;

/**
 * 二分的题注意
 * 是不是返回有无：是 <= right = mid - 1
 * 是不是可能超出区间 是 right = nums.length
 *
 * 写的时候注意mid命中和非命中就行
 */
public class L704二分查找 {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = (left + right) / 2;

            if (target == nums[mid]) {
                return mid;
            }

            if (target > nums[mid]) {
                left = mid + 1;
            }
            else {
                right = mid - 1;
            }
        }
        return -1;
    }
}
