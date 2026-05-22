import java.util.*;

public class BinarySearchTree {
  private static final class Node {
    int data;
    Node left, right;

    Node(int data) {
      this.data = data;
    }
  }

  private Node root;

  public void insert(int data) {
    root = insert(root, data);
  }

  private Node insert(Node node, int data) {
    if (node == null)
      return new Node(data);
    if (data < node.data)
      node.left = insert(node.left, data);
    else if (data > node.data)
      node.right = insert(node.right, data);
    return node;
  }

  public void inOrder() {
    inOrder(root);
    System.out.println();
  }

  private void inOrder(Node node) {
    if (node == null)
      return;
    inOrder(node.left);
    System.out.print(node.data + " ");
    inOrder(node.right);
  }

  public List<List<Integer>> zigZag() {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null)
      return result;

    Deque<Node> queue = new ArrayDeque<>();
    queue.add(root);
    boolean leftToRight = true;

    while (!queue.isEmpty()) {
      int size = queue.size();
      LinkedList<Integer> level = new LinkedList<>();

      for (int i = 0; i < size; i++) {
        Node current = queue.poll();
        if (leftToRight)
          level.addLast(current.data);
        else
          level.addFirst(current.data);

        if (current.left != null)
          queue.add(current.left);
        if (current.right != null)
          queue.add(current.right);
      }
      result.add(level);
      leftToRight = !leftToRight;
    }
    return result;
  }

  public static void main(String[] args) {
    BinarySearchTree bst = new BinarySearchTree();
    List.of(20, 10, 30, 5, 15, 25, 35).forEach(bst::insert);

    System.out.print("In-Order (Should be sorted): ");
    bst.inOrder();

    System.out.println("Zig-Zag Levels: " + bst.zigZag());
  }
}
