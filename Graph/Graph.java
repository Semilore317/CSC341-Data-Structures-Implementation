import java.util.*;

public class Graph<T> {
  private final Map<T, Set<T>> adjacencyList;

  public Graph() {
    this.adjacencyList = new HashMap<>();
  }

  public void addVertex(T vertex) {
    adjacencyList.putIfAbsent(vertex, new LinkedHashSet<>());
  }

  public void addEdge(T source, T destination) {
    addVertex(source);
    addVertex(destination);
    adjacencyList.get(source).add(destination);
    adjacencyList.get(destination).add(source); // Undirected
  }

  public void bfs(T startNode) {
    if (!adjacencyList.containsKey(startNode))
      return;

    Set<T> visited = new HashSet<>();
    Queue<T> queue = new LinkedList<>();

    visited.add(startNode);
    queue.add(startNode);

    while (!queue.isEmpty()) {
      T current = queue.poll();
      System.out.print(current + " ");

      for (T neighbor : adjacencyList.getOrDefault(current, Collections.emptySet())) {
        if (visited.add(neighbor)) { // .add returns false if already present
          queue.add(neighbor);
        }
      }
    }
    System.out.println();
  }

  public int[][] toAdjacencyMatrix(List<T> nodes) {
    int size = nodes.size();
    int[][] matrix = new int[size][size];
    Map<T, Integer> nodeToIndex = new HashMap<>();

    for (int i = 0; i < size; i++) {
      nodeToIndex.put(nodes.get(i), i);
    }

    for (int i = 0; i < size; i++) {
      T u = nodes.get(i);
      for (T v : adjacencyList.getOrDefault(u, Collections.emptySet())) {
        if (nodeToIndex.containsKey(v)) {
          matrix[i][nodeToIndex.get(v)] = 1;
        }
      }
    }
    return matrix;
  }

  public static void main(String[] args) {
    Graph<Integer> g = new Graph<>();

    List.of(
        List.of(9, 1), List.of(1, 5), List.of(5, 4), List.of(4, 8),
        List.of(8, 10), List.of(2, 10), List.of(7, 2), List.of(3, 7),
        List.of(3, 9), List.of(9, 6), List.of(1, 6), List.of(6, 5),
        List.of(3, 6), List.of(3, 4), List.of(6, 7), List.of(6, 2),
        List.of(6, 10), List.of(4, 10), List.of(2, 8)).forEach(edge -> g.addEdge(edge.get(0), edge.get(1)));

    System.out.print("BFS from 9: ");
    g.bfs(9);

    // Sort keys for a deterministic matrix output
    List<Integer> allNodes = new ArrayList<>(g.adjacencyList.keySet());
    Collections.sort(allNodes);

    System.out.println("\nAdjacency Matrix:");
    int[][] matrix = g.toAdjacencyMatrix(allNodes);
    for (int[] row : matrix) {
      System.out.println(Arrays.toString(row));
    }
  }
}
