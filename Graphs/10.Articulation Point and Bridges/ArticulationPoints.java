/*
In this problem, we are considering undirected graph, so there won't be any cross edges, thus no Stack
or inStack() required.

Time : O(V+E) and space : O(V+E)
 */

import java.util.*;

public class Solution {
    private static int UNVISITED = -1;
    private static int time = 0;
    private static void articulationPointsCount(Map<Integer, List<Integer>> graph) {
        int low[] = new int[graph.size()];
        int disc[] = new int[graph.size()];
        int parent[] = new int[graph.size()]; // need this to avoid the child -> parent edge
        boolean artiPoints[] = new boolean[graph.size()];
        Arrays.fill(low, -1);
        Arrays.fill(disc, -1);
        Arrays.fill(parent, -1);

        for(int u : graph.keySet()) {
            if(disc[u] == UNVISITED) {
                DFS(u, graph, low, disc, parent, artiPoints);
            }
        }
        System.out.println(Arrays.toString(artiPoints));
    }

    private static void DFS(int u, Map<Integer, List<Integer>> graph, int[] low, int[] disc, int[] parent, boolean[] artiPoints) {
        int children = 0;
        disc[u] = low[u] = time++;
        for(int v : graph.get(u)) {
            if(disc[v] == UNVISITED) {
                children++;
                parent[v] = u;
                DFS(v, graph, low, disc, parent, artiPoints);
                low[u] = Math.min(low[u], low[v]); // for tree edge while backtracking

                if(parent[u] == -1 && children > 1) // scenario 1
                    artiPoints[u] = true;
                else if(parent[u] != -1 && low[v] >= disc[u]) {  // there can be another edge between u & v,
                                                                 // other than the one under consideration now,
                                                                 // thats why the condtn is '>='
                    artiPoints[u] = true;
                }
            } else if(v != parent[u]) {
                low[u] = Math.min(low[u], disc[v]); // this is for back edge
            }
        }
    }
}
