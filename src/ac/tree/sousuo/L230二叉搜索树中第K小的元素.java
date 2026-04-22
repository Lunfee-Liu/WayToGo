package ac.tree.sousuo;

import ac.tree.TreeNode;
public class L230二叉搜索树中第K小的元素 {
    private int rest;
    private int result;

    public int kthSmallest(TreeNode root, int k) {
        rest = k;
        dfs(root);
        return result;
    }

    private void dfs(TreeNode root) {
        if (root == null) {
            return;
        }
        dfs(root.left);
        rest--;
        if (rest == 0) {
            result = root.val;
        }
        dfs(root.right);
    }
}
