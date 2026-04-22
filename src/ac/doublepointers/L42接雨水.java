package ac.doublepointers;

public class L42接雨水 {
    public static void main(String[] args) {
        int[] nums = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trap(nums));
    }
    public static int trap(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxL = 0;
        int maxR = 0;

        int res = 0;
        while (left < right) {
            maxL = Math.max(maxL, height[left]);
            maxR = Math.max(maxR, height[right]);

            if(maxL < maxR) {
                res += maxL - height[left];
                left++;
            } else {
                res += maxR - height[right];
                right--;
            }
        }
        return res;
    }
}
