package ac.huisu;

import java.util.ArrayList;
import java.util.List;

public class L39组合总数 {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {

        List<List<Integer>> res = new ArrayList<>();
        dfs(res, candidates, new ArrayList<>(), target, 0, 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, int[] nums, List<Integer> item, int target, int sum, int index) {
        if (target < sum) {
            return;
        }
        if (target == sum) {
            res.add(new ArrayList<>(item));
            return;
        }
        for (int i = index; i < nums.length; i++) {
            item.add(nums[i]);
            sum += nums[i];
            dfs(res, nums, item, target, sum, i);
            item.remove(item.size() - 1);
            sum -= nums[i];
        }
    }
}
