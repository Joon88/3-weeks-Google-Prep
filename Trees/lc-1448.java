// LC-1448 (https://leetcode.com/problems/count-good-nodes-in-binary-tree/)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int count = 0;
    public int goodNodes(TreeNode root) {
        DFS(root, Integer.MIN_VALUE);
        return count;
    }

    private void DFS(TreeNode root, int maxSoFar) {
        if(root == null)
            return;

        if(root.val >= maxSoFar)
            count++;

        maxSoFar = Math.max(maxSoFar, root.val);
        DFS(root.left, maxSoFar);
        DFS(root.right, maxSoFar);
    }
}
