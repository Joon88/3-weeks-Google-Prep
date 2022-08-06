/*
Count all the bridges or cut edges in a graph.

In this problem, we are considering undirected graph, so there won't be any cross edges, thus no Stack
or inStack() required.

Time : O(V+E) and space : O(V+E)

Implementation :
LC-1192 (https://leetcode.com/problems/critical-connections-in-a-network/)
 */
class Solution {
    private static int UNVISITED = -1;
    private static int time = 0;
    private static void bridgeCount(Map<Integer, List<Integer>> graph) {
        int low[] = new int[graph.size()];
        int disc[] = new int[graph.size()];
        int parent[] = new int[graph.size()]; // need this to avoid the child -> parent edge
        List<int[]> bridges = new ArrayList<>();
        Arrays.fill(low, -1);
        Arrays.fill(disc, -1);
        Arrays.fill(parent, -1);
        // In this problem, we are considering undirected graph, so there won't be any cross edges
        for(int u : graph.keySet()) {
            if(disc[u] == UNVISITED) {
                DFS(u, graph, low, disc, parent, bridges);
            }
        }
        for(int[] ans: bridges)
            System.out.println(Arrays.toString(ans));
    }

    private static void DFS(int u, Map<Integer, List<Integer>> graph, int[] low, int[] disc, int[] parent, List<int[]> bridges) {
        disc[u] = low[u] = time++;
        for(int v : graph.get(u)) {
            if(disc[v] == UNVISITED) {
                parent[v] = u;
                DFS(v, graph, low, disc, parent, bridges);
                low[u] = Math.min(low[u], low[v]); // for tree edge while backtracking

                if(low[v] > disc[u]) {  // only one scenario here, and there can be no other edge between u & v,
                                        // except the one under consideration now, thats why the condtn is '>'
                    bridges.add(new int[]{u, v});
                }
            } else if(v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]); // this is for back edge
            }
        }
    }
}
