import java.util.*;

public class BTree {
  private final int T; // minimum degree
  private Node root;

  public BTree(int t) {
    if (t < 2)
      throw new IllegalArgumentException("Minimum degree T must be >= 2");
    this.T = t;
    this.root = new Node(true);
  }

  class Node {
    int n = 0; // current number of keys
    int[] keys = new int[2 * T - 1];
    Node[] children = new Node[2 * T];
    boolean leaf;

    Node(boolean leaf) {
      this.leaf = leaf;
    }
  }

  public void insert(int k) {
    Node r = root;
    // If root is full, the tree grows in height
    if (r.n == 2 * T - 1) {
      Node s = new Node(false);
      root = s;
      s.children[0] = r;
      splitChild(s, 0, r);
      insertNonFull(s, k);
    } else {
      insertNonFull(r, k);
    }
  }

  // "moving-up" logic
  private void splitChild(Node x, int i, Node y) {
    Node z = new Node(y.leaf);
    z.n = T - 1;

    // copy the last T-1 keys of y to z
    System.arraycopy(y.keys, T, z.keys, 0, T - 1);

    // copy the last T children of y to z
    if (!y.leaf) {
      System.arraycopy(y.children, T, z.children, 0, T);
    }

    y.n = T - 1; // y is now reduced

    // shift children of x to make room for z
    System.arraycopy(x.children, i + 1, x.children, i + 2, x.n - i);
    x.children[i + 1] = z;

    // shift keys of x to make room for the median key from y
    System.arraycopy(x.keys, i, x.keys, i + 1, x.n - i);
    x.keys[i] = y.keys[T - 1]; // The Median moves UP
    x.n++;
  }

  private void insertNonFull(Node x, int k) {
    int i = x.n - 1;
    if (x.leaf) {
      while (i >= 0 && k < x.keys[i]) {
        x.keys[i + 1] = x.keys[i];
        i--;
      }
      x.keys[i + 1] = k;
      x.n++;
    } else {
      while (i >= 0 && k < x.keys[i])
        i--;
      i++;
      if (x.children[i].n == 2 * T - 1) {
        splitChild(x, i, x.children[i]);
        if (k > x.keys[i])
          i++;
      }
      insertNonFull(x.children[i], k);
    }
  }

  public void printTree() {
    if (root == null)
      return;
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int levelSize = queue.size();
      while (levelSize-- > 0) {
        Node node = queue.poll();
        System.out.print("[");
        for (int i = 0; i < node.n; i++) {
          System.out.print(node.keys[i] + (i == node.n - 1 ? "" : ","));
        }
        System.out.print("] ");
        if (!node.leaf) {
          for (int i = 0; i <= node.n; i++) {
            if (node.children[i] != null)
              queue.add(node.children[i]);
          }
        }
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    BTree tree = new BTree(2);

    int[] data = { 10, 20, 5, 6, 12, 30, 7, 17 };
    for (int val : data) {
      System.out.println("Inserting: " + val);
      tree.insert(val);
      tree.printTree();
      System.out.println("---");
    }
  }
}
