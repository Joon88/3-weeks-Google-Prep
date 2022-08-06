import java.util.*;

public class Solution {
    public static void main(String args[]) {
        Graph g = new Graph(false);
    //        g.addNode(0);
    //        g.addNode(1);
    //        g.addNode(2);
    //        g.addNode(3);
    //        g.addNode(4);
    //        g.addNode(5);
    //        g.addNode(6);
    //        g.addNode(7);
    //
    //        g.addEdge(0,1);
    //        g.addEdge(2,1);
    //        g.addEdge(2,3);
    //        g.addEdge(3,4);
    //        g.addEdge(4,0);
    //        g.addEdge(4,2);
    //        g.addEdge(5,6);
    //        g.addEdge(6,7);
    //        g.addEdge(7,5);

            g.addNode(0);
            g.addNode(1);
            g.addNode(2);
            g.addNode(3);

            g.addEdge(0,1);
            g.addEdge(0,2);
            g.addEdge(0,3);
            g.addEdge(2,3);

            System.out.println(g);
            System.out.println(isUnDirectedCyclicDFS(g.graph));

        //System.out.println(g);

        //System.out.println(isDirectedCyclicDFS(g.graph));

    }

    static class Graph{
        Map<Integer, List<Integer>> graph;
        boolean isDirected;

        public Graph() {
            graph = new HashMap<>();
            isDirected = false;
        }

        public Graph(boolean isDirected) {
            graph = new HashMap<>();
            this.isDirected = isDirected;
        }

        public void addNode(int i) {
            graph.put(i, new LinkedList<>());
        }

        public void addEdge(int src, int dest) {
            graph.get(src).add(dest);
            if(!isDirected)
                graph.get(dest).add(src);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(Map.Entry<Integer, List<Integer>> elt : graph.entrySet()) {
                sb.append(elt.getKey() + " ---> " + elt.getValue() + "\n");
            }
            return sb.toString();
        }
    }

    // Using DFS --- O(V+E) time and O(V) space
    public static boolean isDirectedCyclicDFS(Map<Integer, List<Integer>> graph) {
        int visited[] = new int[graph.size()];
        // visited[i] = 0 means white node i.e. unvisited
        // visited[i] = 1 means grey node i.e. in recursion stack
        // visited[i] = 2 means black node i.e. processed (visited + out of recursion stack)
        for(int v : graph.keySet()) {
            if(visited[v] == 0 && isDirectedCyclicDFSRecur(graph, v, visited))
                return true;
        }
        return false;
    }

    private static boolean isDirectedCyclicDFSRecur(Map<Integer, List<Integer>> graph, int u, int[] visited) {
        if(visited[u] == 1) // vertex in recursion stack
            return true;
        if(visited[u] == 2) // vertex processed
            return false;

        visited[u] = 1; // vertex is in recursion stack now
        for(int v : graph.get(u)) {
            if(isDirectedCyclicDFSRecur(graph, v, visited))
                return true;
        }
        visited[u] = 2; // vertex completely processed and out of recursion stack
        return false;
    }

    // O(V+E) time and O(V) space
    public static boolean isUnDirectedCyclicDFS(Map<Integer, List<Integer>> graph) {
        int visited[] = new int[graph.size()];
        // In case of undirected graph, we need not track black nodes, as a node will turn black
        // only when all the node in the graph/subgraph is visited, without any cycle.
        // visited[i] = 0 means white node i.e. unvisited
        // visited[i] = 1 means grey node i.e. in recursion stack or visited

        int parent[] = new int[graph.size()]; // to keep track of immediate parent
        Arrays.fill(parent, -1);

        for(int v : graph.keySet()) {
            if(visited[v] == 0 && isUnDirectedCyclicDFSRecur(graph, v, parent[v], visited, parent))
                return true;
        }
        return false;
    }

    private static boolean isUnDirectedCyclicDFSRecur(Map<Integer, List<Integer>> graph, int u, int p, int[] visited, int[] parent) {
        if(visited[u] == 1) // vertex in recursion stack or vertex visited
            return true;

        visited[u] = 1; // vertex is in recursion stack now
        for(int v : graph.get(u)) {
            if(parent[u] == v) // this is the only change from the directed graph DFS logic
                continue;
            parent[v] = u;
            if(isUnDirectedCyclicDFSRecur(graph, v, parent[v], visited, parent))
                return true;
        }
        return false;
    }

    // Using BFS --- O(V+E) time and O(V) space
    public static boolean isUnDirectedCyclicBFS(Map<Integer, List<Integer>> graph) {
        int visited[] = new int[graph.size()];
        // In case of undirected graph, we need not track black nodes, as a node will turn black
        // only when all the node in the graph/subgraph is visited, without any cycle.
        // visited[i] = 0 means white node i.e. unvisited
        // visited[i] = 1 means grey node i.e. in recursion stack or visited

        int parent[] = new int[graph.size()]; // to keep track of immediate parent
        Arrays.fill(parent, -1);

        Queue<Integer> q = new LinkedList<>();
        for(int v : graph.keySet()) {
            if(visited[v] == 0 && isUnDirectedCyclicBFSRecur(graph, q, v, visited, parent))
                return true;
        }
        return false;
    }

    private static boolean isUnDirectedCyclicBFSRecur(Map<Integer, List<Integer>> graph, Queue<Integer> q, int src, int[] visited, int[] parent) {
        visited[src] = 1;
        q.add(src);
        while(!q.isEmpty()) {
            int u = q.poll();
            for(int v : graph.get(u)) {
                if (parent[u] == v) // this means the current edge is same as the one we just traversed
                    continue;
                if (visited[v] == 1)
                    return true;
                visited[v] = 1;
                parent[v] = u;
                q.add(v);
            }
        }
        return false;
    }

    // Kahn's Algo (Using BFS) : O(V+E) time and O(V) space
    public static boolean isDirectedCyclicBFS(Map<Integer, List<Integer>> graph) {
        int[] indegree = new int[graph.size()]; // to store the indegree of all vertices in the graph

        for(List<Integer> edges : graph.values()) { // O(V+E) time
            for(int v : edges) {
                indegree[v] += 1;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        for(int v = 0 ; v < indegree.length ; v++) { // Pushing the 0 indegree vertices to queue
            if(indegree[v] == 0)
                q.add(v);
        }
        int verticesVisited = 0;
        while(!q.isEmpty()) {      // O(E)
            int u = q.poll(); // The order in which we get this u for a DAG, is the topological sorted order of the graph
            verticesVisited++;
            for(int v : graph.get(u)) {
                indegree[v] -= 1;
                if(indegree[v] == 0)
                    q.add(v);
            }
        }
        if(verticesVisited < graph.size()) // this means the graph is not a DAG, so its cyclic
            return true;
        else
            return false;
    }
}
