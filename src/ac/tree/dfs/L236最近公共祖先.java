package ac.tree.dfs;

import ac.tree.TreeNode;

public class L236最近公共祖先 {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return find(root, p, q);
    }

    private TreeNode find(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }


        TreeNode findLeft = find(root.left, p, q);
        TreeNode findRight = find(root.right, p, q);

        // 这个条件可以上移，因为只找到这个也算是祖先了
        if (root == p || root == q) {
            return root;
        }

        if (findLeft != null && findRight != null) {
            return root;
        }
        if (findLeft != null) {
            return findLeft;
        }
        if (findRight != null) {
            return findRight;
        }
        return null;

    }
}
