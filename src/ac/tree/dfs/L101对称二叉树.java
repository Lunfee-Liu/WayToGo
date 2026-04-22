package ac.tree.dfs;

import ac.tree.TreeNode;

public class L101对称二叉树 {
    public boolean isSymmetric(TreeNode root) {
        return visit(root.left, root.right);
    }

    public boolean visit(TreeNode left, TreeNode right) {
        if (left == null && right == null) {
            return true;
        }
        if (left != null && right != null) {
            return left.val == right.val && visit(left.left, right.right) && visit(left.right, right.left);
        }
        return false;
    }
}
