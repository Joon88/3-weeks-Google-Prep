/*
LC-1319 (https://leetcode.com/problems/number-of-operations-to-make-network-connected/)
Soln - To find number of components in a graph. Great implementation of Disjoint Set Union Find(DSUF)

Time : O(E + V) amortized and O(ElogV + v) generally with O(V) space
 */

class Solution {
    class DSUF {
        int[] parent;
        int[] rank;

        public DSUF(int n) {
            parent = new int[n];
            rank = new int[n];
        }

        public void makeSet(int i) {
            parent[i] = i;
            rank[i] = 0;
        }

        public int find(int i) {
            if(parent[i] == i)
                return i;
            return parent[i] = find(parent[i]);
        }

        public boolean union(int i, int j) {
            int ap_i = find(i);
            int ap_j = find(j);

            if(ap_i == ap_j)
                return false;
            if(rank[ap_i] > rank[ap_j]) {
                parent[ap_j] = ap_i;
            } else if(rank[ap_i] < rank[ap_j]) {
                parent[ap_i] = ap_j;
            } else {
                parent[ap_i] = ap_j;
                rank[ap_j]++;
            }
            return true;
        }
    }
    public int makeConnected(int n, int[][] connections) {
        if(connections.length < n-1)
            return -1;
        DSUF ds = new DSUF(n);
        for(int i = 0 ; i < n ; i++) {
            ds.makeSet(i);
        }

        for(int a[] : connections) {
            ds.union(a[0], a[1]);
        }

        int ctr = 0;
        for(int i = 0 ; i < ds.parent.length ; i++) {
            if(ds.parent[i] == i)
                ctr++;
        }
        return ctr-1;
    }
}
