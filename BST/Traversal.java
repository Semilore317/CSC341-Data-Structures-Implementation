package fundamentals.oop.traversal_code;

import java.util.*;

public class Traversal {
    // Static inner class for Node
    public static class Node {
        String data;
        Node left;
        Node right;

        public Node(String data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    private Node root;

    // Public wrapper for PreOrder traversal
    public void preOrder() {
        preOrder(root);
    }

    private void preOrder(Node node) {
        if (node == null)
            return;

        System.out.print(node.data + " ");
        preOrder(node.left);
        preOrder(node.right);
    }

    // Public wrapper for InOrder traversal
    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(Node node) {
        if (node == null)
            return;

        inOrder(node.left);
        System.out.print(node.data + " ");
        inOrder(node.right);
    }

    // Public wrapper for PostOrder traversal
    public void postOrder() {
        postOrder(root);
    }

    private void postOrder(Node node) {
        if (node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.data + " ");
    }

    // Public wrapper for LevelOrder traversal
    public void levelOrder() {
        levelOrder(root);
    }

    private void levelOrder(Node node) {
        if (node == null)
            return;

        Queue<Node> queue = new LinkedList<>();
        queue.add(node);
        while (!queue.isEmpty()) {
            Node temp = queue.poll();
            System.out.print(temp.data + " ");
            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        System.out.println();
    }

    // Method to set the root
    public void setRoot(Node root) {
        this.root = root;
    }

    public ArrayList<String> zigZag() {
        // perform zig-zag level-order traversal and return node values
        //Array result = new String[root.length];
        ArrayList<String> result = new ArrayList<String>();
        if (root == null) {
            return result;
        }
        Deque<Node> queue = new ArrayDeque<>();
        boolean left_to_right = true;

        while(!queue.isEmpty()) {
            int level_size = queue.size();
            //int[] current_level = new int[level_size];
            ArrayList<Integer> current_level = new ArrayList<Integer>();

            // process all nodes at the current level
            for (int i = 0; i < level_size; i++) {
                Traversal.Node node = queue.poll();
                current_level.add(Integer.valueOf(node.data));

                if(
                        node.left != null) {
                    queue.add(node.left);
                }else if(node.right != null) {
                    queue.add(node.right);
                }
            }

            // Reverse the current level if going right-to-left
            if(!left_to_right) {
                current_level.reversed();
            }

            result.add(current_level.toString());
            left_to_right = !left_to_right;
        }
        return result;
    }

    // Main method for testing
    public static void main(String[] args) {
        Traversal tree = new Traversal();

        // Create a sample binary tree:
        //       A
        //      / \
        //     B   C
        //    / \   \
        //   D   E   F
        Node root = new Node("A");
        root.left = new Node("B");
        root.right = new Node("C");
        root.left.left = new Node("D");
        root.left.right = new Node("E");
        root.right.right = new Node("F");

        tree.setRoot(root);

        System.out.println("PreOrder Traversal:");
        tree.preOrder(); // Output: 1 2 4 5 3

        System.out.println("\nInOrder Traversal:");
        tree.inOrder(); // Output: 4 2 5 1 3

        System.out.println("\nPostOrder Traversal:");
        tree.postOrder(); // Output: 4 5 2 3 1

        System.out.println("\nLevelOrder Traversal:");
        tree.levelOrder(); // Output: 1 2 3 4 5
    }
}