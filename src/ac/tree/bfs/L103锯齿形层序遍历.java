package ac.tree.bfs;

import ac.tree.TreeNode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 层序遍历
 */

public class L103锯齿形层序遍历 {
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            List<Integer> layer = new ArrayList<>();
            int layerSize = queue.size();
            while (layerSize-- > 0) {
                TreeNode cur = queue.poll();
                // 用奇偶行记录存入顺序
                if (res.size() % 2 == 0) {
                    layer.add(cur.val);
                } else {
                    layer.add(0, cur.val);
                }

                if (cur.left != null) {
                    queue.offer(cur.left);
                }
                if (cur.right != null) {
                    queue.offer(cur.right);
                }
            }
            res.add(layer);
        }

        return res;
    }
}
