package nov.nov;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class QuanPaiLie {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
            dfsV2(nums, new HashSet<>(), new ArrayList<>(), result);

        return result;
    }

    private void dfsV2(int[] nums, Set<Integer> visited, List<Integer> path, List<List<Integer>> result) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int num : nums) {
            if (!visited.contains(num)) {
                visited.add(num);
                path.add(num);
                dfsV2(nums, visited, path, result);

                visited.remove(num);
                path.remove(path.size() - 1);
            }
        }
    }
}