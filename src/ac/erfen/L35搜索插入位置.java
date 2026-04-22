package ac.erfen;

public class L35搜索插入位置 {
    public int searchInsert(int[] nums, int target) {
        // left是第一个比target大的坐标，可能越界
        int left = 0;
        int right = nums.length;

        while (left < right) {
            int mid = (left + right) / 2;

            if (target > nums[mid]) {
                left = mid + 1;
            }
            // 有target，这里找到左边界
            // 无target，这里找到第一个大于target的坐标，可能越界
            else {
                right = mid;
            }
            System.out.println("left: " + left + ", right: " + right);
        }
        return left;
    }
}
