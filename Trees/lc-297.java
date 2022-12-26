// LC-297 (https://leetcode.com/problems/serialize-and-deserialize-binary-tree/)
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class Codec {

    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        StringBuffer sb = new StringBuffer();

        DFS(root, sb);
        return new String(sb);
    }

    private void DFS(TreeNode root, StringBuffer sb) {
        if(root == null) {
            sb.append("X,");
            return;
        }
        sb.append(root.val + ",");
        DFS(root.left, sb);
        DFS(root.right, sb);
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        String[] str = data.split(",");
        return DFS1(str);
    }

    int idx = 0;
    private TreeNode DFS1(String[] arr) {

        if(arr[idx].equals("X")) {
            idx++;
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(arr[idx++]));
        node.left = DFS1(arr);
        node.right = DFS1(arr);

        return node;
    }
}

// Your Codec object will be instantiated and called as such:
// Codec ser = new Codec();
// Codec deser = new Codec();
// TreeNode ans = deser.deserialize(ser.serialize(root));
