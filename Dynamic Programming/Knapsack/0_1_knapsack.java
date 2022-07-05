import java.util.*;

public class Solution {
    public static void main(String[] args){
    }
    // 2-D DP
    // O(nw) Time and space : n & w are number of weights avaiable and capacity of knapsack respectively
    public static int maxWeight(int[] weights, int[] profits, int cap) {
        int[][] dp = new int[weights.length+1][cap+1];
        // by default all array elts will be 0, but mentioned this just to be aware of the base case
        for(int i = 0; i < dp.length ; i++) {
            dp[i][0] = 0;
        }
        Arrays.fill(dp[0], 0);
        for(int i = 1 ; i < dp.length ; i++) {
            for(int  j = 1 ; j < dp[0].length ; j++) {
                if(weights[i-1] > j) {
                    dp[i][j] = dp[i-1][j];
                    continue;
                }
                dp[i][j] = Math.max(dp[i-1][j], profits[i-1] + dp[i-1][j-weights[i-1]]);
            }
        }
        return dp[weights.length][cap];
    }

    // 2*1-D DP
    // O(nw) Time and O(2*w) space : n & w are number of weights avaiable and capacity of knapsack respectively
    public static int maxWeight(int[] weights, int[] profits, int cap) {
        int[][] dp = new int[2][cap+1];
        // by default all array elts will be 0, but mentioned this just to be aware of the base case
        for(int i = 0; i < dp.length ; i++) {
            dp[i][0] = 0;
        }
        Arrays.fill(dp[0], 0);
        for(int i = 1 ; i < weights.length+1 ; i++) {
            int row = i%2;
            int prevRow = 1-row;
            for(int  j = 1 ; j < dp[0].length ; j++) {
                if(weights[i-1] > j) {
                    dp[row][j] = dp[prevRow][j];
                    continue;
                }
                dp[row][j] = Math.max(dp[prevRow][j], profits[i-1] + dp[prevRow][j-weights[i-1]]);
            }
        }
        return dp[weights.length%2][cap];
    }

    // 1-D DP
    // O(nw) Time and O(w) space : n & w are number of weights avaiable and capacity of knapsack respectively
    public static int maxWeight(int[] weights, int[] profits, int cap) {
        int[] dp = new int[cap+1];
        for(int i = 0 ; i < weights.length ; i++) {
            for(int j = cap; j >= 1; j--) {
                if(weights[i] <= j) {
                    dp[j] = Math.max(dp[j], profits[i] + dp[j-weights[i]]);
                }
            }
        }
        return dp[cap];
    }
}
