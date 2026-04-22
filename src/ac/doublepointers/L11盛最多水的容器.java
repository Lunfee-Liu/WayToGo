package ac.doublepointers;

public class L11盛最多水的容器 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 4, 9, 8, 7, 5};
        System.out.println(maxArea(nums));
    }
    public static int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;

        int maxArea = (right - left) * Math.min(height[left], height[right]);
        while (left < right) {
            if (height[left] < height[right]) {
                left++;
                if (height[left] <= height[left - 1]) {
                    continue;
                }
            } else {
                right--;
                if (height[right] <= height[right + 1]) {
                    continue;
                }
            }
            maxArea = Math.max(maxArea, (right - left) * Math.min(height[left], height[right]));
        }
        return maxArea;
    }
}
