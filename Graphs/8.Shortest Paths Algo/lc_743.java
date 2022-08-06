/*
LC-743 (https://leetcode.com/problems/network-delay-time/)

Implementation of Dijkstra's Algo
 */

class Solution {
    static class Node implements Comparable<Node> {
        int id;
        int key;

        public Node(int id) {
            this.id = id;
            this.key = Integer.MAX_VALUE;
        }
        public boolean equals(Object o) {
            if(this == o) return true;
            if(o == null || getClass() != o.getClass()) return false;
            Node n = (Node) o;
            return this.id == n.id;
        }
        public int hashCode(){
            return Objects.hash(id);
        }
        public int compareTo(Node n) {
            return this.key-n.key;
        }
    }
    static class Edge {
        Node dest;
        int weight;

        public Edge(Node dest, int weight){
            this.dest = dest;
            this.weight = weight;
        }
    }
    public int networkDelayTime(int[][] times, int n, int k) {
        Map<Node, List<Edge>> graph = new HashMap<>();
        Node[] nodes = new Node[n]; // used to store all the node object created
        Node start = null;
        for(int i = 1 ; i<= n ; i++) {
            nodes[i-1] = new Node(i);
            if(i == k)
                start = nodes[i-1];
            graph.putIfAbsent(nodes[i-1], new LinkedList<>());
        }

        for(int a[] : times) {
            Node src = nodes[a[0]-1];
            Node dest = nodes[a[1]-1];
            int weight = a[2];

            List<Edge> l = graph.get(src);
            l.add(new Edge(dest, weight));
            graph.put(src, l);
        }

        return dijkstras(graph, start);
    }
    private int dijkstras(Map<Node, List<Edge>> graph, Node src) {
        boolean[] visited = new boolean[graph.size()];

        src.key = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(src);

        while(!pq.isEmpty()) {
            Node u = pq.poll();
            visited[u.id-1] = true;
            for(Edge e : graph.get(u)) {
                if(!visited[e.dest.id-1] && e.dest.key > u.key + e.weight) {
                    e.dest.key = u.key + e.weight;
                    pq.add(e.dest);
                }
            }
        }

        int max = Integer.MIN_VALUE;
        for(Node n : graph.keySet()){
            max = Math.max(max, n.key);
        }
        return max < Integer.MAX_VALUE ? max : -1;
    }

}
