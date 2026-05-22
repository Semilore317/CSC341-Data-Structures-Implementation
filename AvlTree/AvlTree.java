package fundamentals.oop.traversal_code;

import java.util.ArrayList;
import java.util.Arrays;

public class AvlTree {

    private class Node {
        int value, height;
        Node left, right;

        Node(int d) {
            this.value = d;
            this.height = 1;
        }
    }

    private Node root;

    private int height(Node node) {
        return (node == null) ? 0 : node.height;
    }

    private int getBalance(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private Node rotateRight(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        return x;
    }

    private Node rotateLeft(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        return y;
    }

    public void insert(int value) {
        root = insertRecursive(root, value);
    }

    private Node insertRecursive(Node node, int value) {
        if (node == null) return new Node(value);

        if (value < node.value) {
            node.left = insertRecursive(node.left, value);
        } else if (value > node.value) {
            node.right = insertRecursive(node.right, value);
        } else {
            return node; // duplicate values are ignored
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        return rebalance(node, value);
    }

    private Node rebalance(Node node, int value) {
        int balance = getBalance(node);

        // Left Heavy
        if (balance > 1) {
            if (value < node.left.value) // left-left Case
                return rotateRight(node);
            if (value > node.left.value) { // left-right Case
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }

        // Right Heavy
        if (balance < -1) {
            if (value > node.right.value) // right-right Case
                return rotateLeft(node);
            if (value < node.right.value) { // right-left Case
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    public void buildFromList(ArrayList<Integer> list) {
        for (Integer val : list) {
            insert(val);
        }
    }

    // verify sorted property with in-order traversal
    public void printInOrder() {
        printInOrderRecursive(root);
        System.out.println();
    }

    private void printInOrderRecursive(Node node) {
        if (node != null) {
            printInOrderRecursive(node.left);
            System.out.print(node.value + " ");
            printInOrderRecursive(node.right);
        }
    }

    public static void main(String[] args) {
        AvlTree tree = new AvlTree();
        ArrayList<Integer> inputData = new ArrayList<>(Arrays.asList(10, 20, 30, 40, 50, 25));

        tree.buildFromList(inputData);

        System.out.print("Tree verification (In-Order): ");
        tree.printInOrder();
        // should output: 10 20 25 30 40 50
    }
}