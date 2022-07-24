/*
This uses BFS and is an alternative to Dijkstra's.

One similar example : (LC-787) https://leetcode.com/problems/cheapest-flights-within-k-stops/
 */
class Solution {
    class Edge {
        int dest;
        int weight;

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }
    public int[] shortestPath(Map<Integer, List<Edge>> graph, int src) {
        int[] weight = new int[graph.size()];
        Arrays.fill(weight, Integer.MAX_VALUE);
        weight[src] = 0;

        int[] srcNode = new int[]{src, 0}; // creating an auxiliary node {id, key}
        Queue<int[]> q = new LinkedList<>();
        q.add(srcNode);

        while(!q.isEmpty()) {
            int[] node = q.poll();
            int u = node[0];
            int w = node[1];
            if(w > weight[u]) // if we have already got a lower weight path, then ignore the current aux node
                continue;

            for(Edge e : graph.get(u)) {
                int v = e.dest;
                int[] n = new int[]{v, w + e.weight};
                if(weight[v] > w + e.weight) {
                    weight[v] = w + e.weight;
                    q.add(n);
                }
            }
        }

        return weight;
    }
}
