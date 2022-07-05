import java.util.*;

class Program {
  // This is an input class. Do not edit.
  static class BST {
    public int value;
    public BST left = null;
    public BST right = null;

    public BST(int value) {
      this.value = value;
    }
  }

  public BST reconstructBst(ArrayList<Integer> preOrderTraversalValues) {
    // Write your code here.
    int ctr = 0; BST root = null;
    for(int x : preOrderTraversalValues) {
      BST node = new BST(x);
      if(ctr == 0) {
        root = insertInTree(node, null);
      } else
        root = insertInTree(node, root);
      ctr++;
    }
    return root;
  }
  public BST insertInTree(BST node, BST root) {
    if(root == null)
      return node;
    BST current = root, parent = root;
    while(current != null) {
      while(current != null && node.value < current.value) {
        parent = current;
        current = current.left;
      }
      if(current == null) {
        parent.left = node;
        return root;
      }
      parent = current;
      current = current.right;
    }
    parent.right = node;
    return root;
  }
}
