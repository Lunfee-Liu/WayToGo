package ac.huisu;

import java.util.ArrayList;
import java.util.List;

public class L46全排列 {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, nums, new ArrayList<>(), new boolean[nums.length]);
        return res;
    }

    private void dfs(List<List<Integer>> res, int[] nums, List<Integer> item, boolean[] used) {
        if (item.size() == nums.length) {
            res.add(new ArrayList<>(item));
            return;
        }
        for (int i = 0; i < nums.length; i++) {
            if (used[i]) {
                continue;
            }
            item.add(nums[i]);
            used[i] = true;
            dfs(res, nums, item, used);
            item.remove(item.size() - 1);
            used[i] = false;
        }
    }
}
