/*
LC-210 - Course Schedule - II (https://leetcode.com/problems/course-schedule-ii/)
 */

// O(V+E) time and space
class Solution {
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        if(numCourses == 1)
            return new int[]{0};
        Map<Integer, List<Integer>> graph = new HashMap<>(); // O(V+E) space + O(V+E) time

        for(int i = 0; i < numCourses ; i++)
            graph.put(i, new LinkedList<>());

        for(int[] a : prerequisites) {
            int src = a[1];
            int dst = a[0];

            List<Integer> l = graph.get(src);
            l.add(dst);
            graph.put(src, l);
        }

        // cycle check : O(V+E) time and O(V) space as its just DFS
        int color[] = new int[numCourses];
        for(int i = 0 ; i < numCourses ; i++)
            if(color[i] == 0 && isCyclic(i, graph, color))
                return new int[]{};

        Stack<Integer> stack = new Stack<>();
        int visited[] = new int[numCourses];

        // O(V+E) time and O(V) space
        for(int i = 0 ; i < numCourses ; i++)
            if(visited[i] == 0)
                topologicalSort(i, graph, visited, stack);

        int idx = 0;
        Arrays.fill(visited, 0);
        while(!stack.isEmpty()) {
            visited[idx++] = stack.pop();
        }
        return visited;
    }

    private void topologicalSort(int src, Map<Integer, List<Integer>> graph, int[] visited, Stack<Integer> stack) {
        if(visited[src] == 1)
            return;

        visited[src] = 1;
        for(int v : graph.get(src)) {
            topologicalSort(v, graph, visited, stack);
        }
        stack.push(src);
    }

    private boolean isCyclic(int src, Map<Integer, List<Integer>> graph, int[] color) {
        if(color[src] == 1)
            return true;
        if(color[src] == 2)
            return false;

        color[src] = 1;
        for(int v : graph.get(src)) {
            if(isCyclic(v, graph, color))
                return true;
        }
        color[src] = 2;
        return false;
    }
}
