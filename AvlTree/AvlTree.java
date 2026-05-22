import java.util.Collection;

public class AvlTree {

  private static final class Node {
    int value, height;
    Node left, right;

    Node(int value) {
      this.value = value;
      this.height = 1;
    }
  }

  private Node root;

  public void insert(int value) {
    root = insert(root, value);
  }

  public void build(Collection<Integer> values) {
    for (int val : values)
      insert(val);
  }

  private Node insert(Node node, int value) {
    if (node == null)
      return new Node(value);

    if (value < node.value) {
      node.left = insert(node.left, value);
    } else if (value > node.value) {
      node.right = insert(node.right, value);
    } else {
      return node;
    }

    updateHeight(node);
    return rebalance(node);
  }

  private Node rebalance(Node n) {
    int balance = getBalance(n);

    if (balance > 1) {
      if (getBalance(n.left) < 0)
        n.left = rotateLeft(n.left);
      return rotateRight(n);
    }
    if (balance < -1) {
      if (getBalance(n.right) > 0)
        n.right = rotateRight(n.right);
      return rotateLeft(n);
    }
    return n;
  }

  private Node rotateRight(Node y) {
    Node x = y.left;
    Node t2 = x.right;
    x.right = y;
    y.left = t2;
    updateHeight(y);
    updateHeight(x);
    return x;
  }

  private Node rotateLeft(Node x) {
    Node y = x.right;
    Node t2 = y.left;
    y.left = x;
    x.right = t2;
    updateHeight(x);
    updateHeight(y);
    return y;
  }

  private int height(Node n) {
    return n == null ? 0 : n.height;
  }

  private int getBalance(Node n) {
    return n == null ? 0 : height(n.left) - height(n.right);
  }

  private void updateHeight(Node n) {
    n.height = 1 + Math.max(height(n.left), height(n.right));
  }

  public void printInOrder() {
    printInOrder(root);
    System.out.println();
  }

  private void printInOrder(Node node) {
    if (node != null) {
      printInOrder(node.left);
      System.out.print(node.value + " ");
      printInOrder(node.right);
    }
  }

  public static void main(String[] args) {
    var tree = new AvlTree();
    java.util.List.of(10, 20, 30, 40, 50, 25).forEach(tree::insert);

    System.out.print("AVL In-Order Verification: ");
    tree.printInOrder();
  }
}
