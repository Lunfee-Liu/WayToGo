package ac.tree.dfs;

import ac.tree.TreeNode;

public class L543二叉树直径 {
    public int res = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null || root.left == null && root.right == null) {
            return 0;
        }
        maxDepth(root);
        return res;
    }

    private int maxDepth(TreeNode root) {
        if (root == null) {
            return -1;
        }
        int maxL = maxDepth(root.left);
        int maxR = maxDepth(root.right);
        int max = Math.max(maxL, maxR) + 1;
        res = Math.max(res, maxL + maxR);
        return max;
    }
}
