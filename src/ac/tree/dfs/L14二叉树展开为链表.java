package ac.tree.dfs;

import ac.tree.TreeNode;

public class L14二叉树展开为链表 {
    public void flatten(TreeNode root) {
        if (root != null) {
            done(root);
        }
    }

    private TreeNode done(TreeNode node) {

        if (node.left == null && node.right == null) {
            return node;
        }

        if (node.left == null) {
            return done(node.right);
        }

        if (node.right == null) {
            TreeNode leftNewTail = done(node.left);
            node.right = node.left;
            node.left = null;
            return leftNewTail;
        }

        TreeNode leftNewTail = done(node.left);
        TreeNode rightNewTail = done(node.right);

        leftNewTail.right = node.right;
        node.right = node.left;
        node.left = null;
        return rightNewTail;
    }
}
