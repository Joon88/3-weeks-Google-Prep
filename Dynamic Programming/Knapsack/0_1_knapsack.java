import java.util.*;

public class Solution {
    int[][] memo;
    public static void main(String[] args){
        int[] weights = new int[]{};
        int[] profits = new int[]{};
        int capacity = 5;

        memo = new int[weights.length+1][capacity+1];
        for(int i = 1 ; i < memo.length ; i++) {
            for(int j = 1 ; j < memo[0].length ; j++) {
                memo[i][j] = -1;
            }
        }
        System.out.println(maxWeight(weights.length, capacity, weights, profits));
    }
    // (Exhaustive method) O(2^n) time and O(n) space for the recursion stack, where n is size of weights array
    public static int maxWeight(int n int cap, int[] weights, int[] profits) {
        if(cap == 0 || n == 0)
            return 0;
        if(cap < weights[n-1])
            return maxWeight(n-1, cap, weights, profits);
        return Math.max(maxWeight(n-1, cap, weights, profits), profits[n-1] + maxWeight(n-1, cap-weights[n-1], weights, profits));
    }
    // O(nc) time and O(nc) space, where n is size of weights array and c is the capcity of the knapsack
    public static int maxWeight(int n int cap, int[] weights, int[] profits) {
        if(n == 0 || cap == 0)
            return 0;
        if(memo[n][cap] >= 0)
            return memo[n][cap];
        if(cap < weights[n-1])
            memo[n][cap] = maxWeight(n-1, cap, weights, profits);
        else
            memo[n][cap] = Math.max(maxWeight(n-1, cap, weights, profits), profits[n-1] + maxWeight(n-1, cap - weights[n-1], weights, profits));
        return memo[n][cap];
    }
    // 2-D DP
    // O(nw) Time and space : n & w are number of weights avaiable and capacity of knapsack respectively
    public static int maxWeight(int n, int cap, int[] weights, int[] profits) {
        int[][] dp = new int[n+1][cap+1];
        // by default all array elts will be 0, but mentioned this just to be aware of the base case
        for(int i = 0; i < dp.length ; i++) {
            dp[i][0] = 0;
        }
        Arrays.fill(dp[0], 0);
        for(int i = 1 ; i < dp.length ; i++) {
            for(int wt = 1 ; wt < dp[0].length ; wt++) {
                if(weights[i-1] > wt)
                    dp[i][wt] = dp[i-1][wt];
                else
                    dp[i][wt] = Math.max(dp[i-1][wt], profits[i-1] + dp[i-1][wt-weights[i-1]]);
            }
        }
        return dp[n][cap];
    }

    // 2*1-D DP
    // O(nw) Time and O(2*w) space : n & w are number of weights avaiable and capacity of knapsack respectively
    public static int maxWeight(int n, int cap, int[] weights, int[] profits) {
        int[][] dp = new int[2][cap+1];
        // by default all array elts will be 0, but mentioned this just to be aware of the base case
        for(int i = 0; i < dp.length ; i++) {
            dp[i][0] = 0;
        }
        Arrays.fill(dp[0], 0);
        for(int i = 1 ; i < n+1 ; i++) {
            int row = i%2;
            int prevRow = 1-row;
            for(int wt = 1 ; wt < dp[0].length ; wt++) {
                if(weights[i-1] > wt)
                    dp[row][wt] = dp[prevRow][wt];
                else
                    dp[row][wt] = Math.max(dp[prevRow][wt], profits[i-1] + dp[prevRow][wt-weights[i-1]]);
            }
        }
        return dp[n%2][cap];
    }

    // 1-D DP
    // O(nw) Time and O(w) space : n & w are number of weights avaiable and capacity of knapsack respectively
    public static int maxWeight(int n, int cap, int[] weights, int[] profits) {
        int[] dp = new int[cap+1];
        for(int i = 0 ; i < weights.length ; i++) {
            for(int wt = cap; wt >= 1; wt--) {
                if(weights[i] <= wt) {
                    dp[wt] = Math.max(dp[wt], profits[i] + dp[wt-weights[i]]);
                } else {
                    break;
                }
            }
        }
        return dp[cap];
    }
}
