package ac.huisu;

import java.util.ArrayList;
import java.util.List;

public class L78子集 {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        dfs(res, nums, new ArrayList<>(), 0);
        return res;
    }

    private void dfs(List<List<Integer>> res, int[] nums, List<Integer> item, int index) {
        res.add(new ArrayList<>(item));

        for (int i = index; i < nums.length; i++) {
            item.add(nums[i]);
            dfs(res, nums, item, i + 1);
            item.remove(item.size() - 1);
        }
    }
}
