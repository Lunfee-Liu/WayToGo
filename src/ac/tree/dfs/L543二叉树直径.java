package ac.tree.dfs;

import ac.tree.TreeNode;

public class L543二叉树直径 {
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return 0;
        }
        int[] res = {0};
        maxDepth(root, res);
        return res[0];
    }

    private int maxDepth(TreeNode root, int[] res) {
        if (root == null) {
            return -1;
        }
        int maxL = maxDepth(root.left, res);
        int maxR = maxDepth(root.right, res);
        int max = Math.max(maxL, maxR) + 1;
        res[0] = Math.max(res[0], maxL + maxR);
        return max;
    }
}
