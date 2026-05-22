import java.util.*;

public class BPlusTree<K extends Comparable<K>, V> {
  private final int M;
  private Node root;

  public BPlusTree(int order) {
    if (order < 3)
      throw new IllegalArgumentException("Order must be at least 3");
    this.M = order;
    this.root = new LeafNode();
  }

  abstract class Node {
    List<K> keys = new ArrayList<>();
    InternalNode parent;
  }

  class InternalNode extends Node {
    List<Node> children = new ArrayList<>();
  }

  class LeafNode extends Node {
    List<V> values = new ArrayList<>();
    LeafNode next;
  }

  public void insert(K key, V value) {
    LeafNode leaf = findLeaf(root, key);
    insertIntoLeaf(leaf, key, value);

    if (leaf.keys.size() >= M) {
      splitLeaf(leaf);
    }
  }

  private LeafNode findLeaf(Node node, K key) {
    if (node instanceof LeafNode)
      return (LeafNode) node;
    InternalNode in = (InternalNode) node;
    int i = 0;
    while (i < in.keys.size() && key.compareTo(in.keys.get(i)) >= 0)
      i++;
    return findLeaf(in.children.get(i), key);
  }

  private void insertIntoLeaf(LeafNode leaf, K key, V value) {
    int i = 0;
    while (i < leaf.keys.size() && key.compareTo(leaf.keys.get(i)) > 0)
      i++;
    leaf.keys.add(i, key);
    leaf.values.add(i, value);
  }

  private void splitLeaf(LeafNode leaf) {
    int mid = leaf.keys.size() / 2;
    LeafNode newLeaf = new LeafNode();
    newLeaf.keys.addAll(leaf.keys.subList(mid, leaf.keys.size()));
    newLeaf.values.addAll(leaf.values.subList(mid, leaf.values.size()));
    leaf.keys.subList(mid, leaf.keys.size()).clear();
    leaf.values.subList(mid, leaf.values.size()).clear();

    newLeaf.next = leaf.next;
    leaf.next = newLeaf;

    promote(leaf, newLeaf.keys.get(0), newLeaf);
  }

  private void promote(Node left, K key, Node right) {
    if (left.parent == null) {
      InternalNode newRoot = new InternalNode();
      newRoot.keys.add(key);
      newRoot.children.add(left);
      newRoot.children.add(right);
      left.parent = newRoot;
      right.parent = newRoot;
      root = newRoot;
    } else {
      InternalNode parent = left.parent;
      int i = 0;
      while (i < parent.keys.size() && key.compareTo(parent.keys.get(i)) > 0)
        i++;
      parent.keys.add(i, key);
      parent.children.add(i + 1, right);
      right.parent = parent;

      if (parent.keys.size() >= M)
        splitInternal(parent);
    }
  }

  private void splitInternal(InternalNode node) {
    int mid = node.keys.size() / 2;
    K upKey = node.keys.get(mid);
    InternalNode newNode = new InternalNode();

    newNode.keys.addAll(node.keys.subList(mid + 1, node.keys.size()));
    newNode.children.addAll(node.children.subList(mid + 1, node.children.size()));
    for (Node child : newNode.children)
      child.parent = newNode;

    node.keys.subList(mid, node.keys.size()).clear();
    node.children.subList(mid + 1, node.children.size()).clear();

    promote(node, upKey, newNode);
  }

  public void printTreeStructure() {
    Queue<Node> queue = new LinkedList<>();
    queue.add(root);
    while (!queue.isEmpty()) {
      int size = queue.size();
      for (int i = 0; i < size; i++) {
        Node n = queue.poll();
        System.out.print(n.keys + " ");
        if (n instanceof InternalNode)
          queue.addAll(((InternalNode) n).children);
      }
      System.out.println();
    }
  }

  public static void main(String[] args) {
    BPlusTree<Integer, String> tree = new BPlusTree<>(3);
    int[] data = { 10, 20, 5, 15, 30, 7, 17 };

    for (int val : data) {
      System.out.println("Inserting: " + val);
      tree.insert(val, "V-" + val);
      tree.printTreeStructure();
      System.out.println("---");
    }
  }
}
