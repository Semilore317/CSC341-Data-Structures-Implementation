import java.util.*;

class Graph {
    private int V;
    private LinkedList<Integer>[] adj;

    // Constructor
    Graph(int v) {
        V = v;
        adj = new LinkedList[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedList<>();
        }
    }

    // Add edge (undirected)
    void addEdge(int u, int v) {
        adj[u].add(v);
        adj[v].add(u);
    }

    // BFS traversal
    void BFS(int start) {
        boolean[] visited = new boolean[V];
        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            System.out.print(node + " ");

            for (int neighbor : adj[node]) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(neighbor);
                }
            }
        }
    }

    // Generate adjacency matrix
    int[][] getAdjacencyMatrix() {
        int[][] matrix = new int[V][V];

        for (int i = 0; i < V; i++) {
            for (int j : adj[i]) {
                matrix[i][j] = 1;
            }
        }

        return matrix;
    }

    // Print matrix
    void printMatrix(int[][] matrix) {
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

    // Main method
    public static void main(String[] args) {
        Graph g = new Graph(19);

        g.addEdge(9, 1);
        g.addEdge(1, 5);
        g.addEdge(5, 4);
        g.addEdge(4, 8);
        g.addEdge(8, 10);
        g.addEdge(2, 10);
        g.addEdge(7, 2);
        g.addEdge(3, 7);
        g.addEdge(3, 9);
        g.addEdge(9, 6);
        g.addEdge(1, 6);
        g.addEdge(6, 5);
        g.addEdge(3, 6);
        g.addEdge(3, 4);
        g.addEdge(6, 7);
        g.addEdge(6, 2);
        g.addEdge(6, 10);
        g.addEdge(4, 10);
        g.addEdge(2, 8);
        

        System.out.println("BFS Traversal starting from node 9:");
        g.BFS(9);

        System.out.println("\n\nAdjacency Matrix:");
        int[][] matrix = g.getAdjacencyMatrix();
        g.printMatrix(matrix);
    }
}