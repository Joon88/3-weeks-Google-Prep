/*
KNAPSACK WITH NEGATIVE ELEMENTS

This file contains the isSubsetSumPossible and the countSubsets code, with
all their variations.

This way we can handle -ve values in the input array (0 values also supported)
The memo and dp array would be taking more space than normal, also the recursive
equations will also go through slight variations. This is because, due to the
possibility of negative and 0 values, even when weight[n] > sum, we dont simply
skip weight[n], like we do for only positive weights, instead we keep considering
both the skip and consider scenarios until n == 0.
 */

class Solution {
    static int[][] memo;
    public static void main(String args[]) {
        int[] weights = new int[]{-8,1,7,0};
        int capacity = 0;

        int negativeMod = 0, positiveMod = 0;
        for(int x : weights) {
            if(x < 0)
                negativeMod += -x;
            if(x > 0)
                positiveMod += x;
        }

        int total = negativeMod + positiveMod; // total signifies the maximum value/range sum can assume
                                               // both in the positive and in the negative direction
        // This is to handle the worst case scenario where lets say all values are positive, and we
        // are asked to find subsets for sum 0. Then while considering the elt at every step, we'll keep
        // subtracting the current element from the targetSum i.e. 0 , so, the
        // total sum at the end would be -(sum of abs() of all elts) which is -total. The same thing
        // also applies on the positive side
        memo = new int[weights.length+1][2*total + 1];
        /*
        0-(total-1)       -> negative sum values e.g. sum = 0 mean sum = 0 - total
        total             -> sum = 0
        (total+1)-2*total -> positive sum values e.g. sum = 2*total mean sum = 0 + total
         */
        for(int a[] : memo)
            Arrays.fill(a, -1);
        System.out.println(isSubsetPossible(weights.length, capacity, weights));
        for(int a[] : memo)
            Arrays.fill(a, -1);
        System.out.println(subsetCount(weights.length, capacity, weights));
    }

    // O(2^n) time and O(n) space
    public static boolean isSubsetPossible(int n, int sum, int weights[]) {
        if(sum == 0)
            return true;
        if(n == 0)
            return false;
        return isSubsetPossible(n-1, sum, weights) ||
                isSubsetPossible(n-1, sum-weights[n-1], weights);
    }

    // O(2^n) time and O(n) space
    public static int subsetCount(int n, int sum, int weights[]) {
        if(n == 0 && sum == 0)
            return 1;
        if(n == 0)
            return 0;
        return subsetCount(n-1, sum, weights) +
                subsetCount(n-1, sum-weights[n-1], weights);
    }

    // O(nS) time and space
    public static boolean isSubsetPossible(int n, int sum, int weights[], int total) {
        if(sum == 0)
            return true;
        if(n == 0)
            return false;

        if(memo[n][sum+total] > -1)
            return memo[n][sum+total] == 1;

        boolean result = isSubsetPossible(n-1, sum, weights, total) ||
                isSubsetPossible(n-1, sum-weights[n-1], weights, total);
        memo[n][sum+total] = result ? 1 : 0;
        return memo[n][sum+total] == 1;
    }

    // O(nS) time and space
    public static int subsetCount(int n, int sum, int weights[], int total) {
        if(n == 0 && sum == 0)
            return 1;
        if(n == 0)
            return 0;

        if(memo[n][sum+total] > -1)
            return memo[n][sum+total];

        return memo[n][sum+total] = subsetCount(n-1, sum, weights, total) +
                subsetCount(n-1, sum-weights[n-1], weights, total);
    }

    // O(nS) time and space
    public static boolean isSubsetPossible(int n, int sum, int[] weights) {
        int negativeMod = 0, positiveMod = 0;
        for(int x : weights) {
            if(x < 0)
                negativeMod += -x;
            if(x > 0)
                positiveMod += x;
        }
        int total = negativeMod + positiveMod;
        boolean[][] dp = new boolean[n+1][2*total+1];
        for(int i = 0 ; i < dp[0].length ; i++)
            dp[0][i] = false;
        for(int i = 0 ; i < dp.length ; i++)
            dp[i][total] = true;
        for(int i = 1; i <= n ; i++) {
            /*
            // this two-direction thing is not actually reqd, as we are always reading the values
            // from the row above for calculating the values of the current row.
            if(weights[i-1] < 0)
                for(int s = toal-negativeMod ; s <= total+positiveMod ; s++) {
                    if(dp[i][s])
                        continue;
                    dp[i][s] = dp[i-1][s] || dp[i-1][s - weights[n-1]];
                }
            else
                for(int s = total+positiveMod ; s >= total-negativeMod  ; s--) {
                    if(dp[i][s])
                        continue;
                    dp[i][s] = dp[i-1][s] || dp[i-1][s - weights[n-1]];
                }
            */
            for(int s = total-negativeMod ; s <= total+positiveMod ; s++) {
                dp[i][s] = dp[i-1][s] || dp[i-1][s - weights[n-1]];
            }
        }
        return dp[n][sum+total];
    }

    // O(nS) time and space
    public static int subsetCount(int n, int sum, int weights[]) {
        int negativeMod = 0, positiveMod = 0;
        for(int x : weights) {
            if(x < 0)
                negativeMod += -x;
            if(x > 0)
                positiveMod += x;
        }
        int total = negativeMod + positiveMod;
        int[][] dp = new int[n+1][2*total+1];
        for(int i = 0 ; i < dp[0].length ; i++)
            dp[0][i] = 0; // by default it'll be 0, added this just for reminder
        dp[0][total] = 1; // for the rest of dp[i][total], the value can be more than 1 too, so need calculation
        for(int i = 1; i <= n ; i++) {
            /*
            // this two-direction thing is not actually reqd, as we are always reading the values
            // from the row above for calculating the values of the current row.
            if(weights[i-1] < 0)
                for(int s = total-negativeMod ; s <= total+positiveMod ; s++) {
                    dp[i][s] = dp[i-1][s] + dp[i-1][s - weights[i-1]];
                }
            else
                for(int s = total+positiveMod ; s >= total-negativeMod  ; s--) {
                    dp[i][s] = dp[i-1][s] + dp[i-1][s - weights[i-1]];
                }
            */
            for(int s = total-negativeMod ; s <= total+positiveMod ; s++) {
                dp[i][s] = dp[i-1][s] + dp[i-1][s - weights[i-1]];
            }
        }
        return dp[n][sum+total];

    }

    // O(ns) time and O(s) space
    public static boolean isSubsetPossible(int n, int sum, int[] weights) {
        int negativeMod = 0, positiveMod = 0;
        for (int x : weights) {
            if (x < 0)
                negativeMod += -x;
            if (x > 0)
                positiveMod += x;
        }
        int total = negativeMod + positiveMod;
        boolean[] dp = new boolean[2 * total + 1];
        dp[total] = true;
        for(int i = 0 ; i < n ; i++) {
            if(weights[i] < 0)
                for(int s = total-negativeMod ; s <= total+positiveMod ; s++)
                    dp[s] = dp[s] || dp[s - weights[i]];
            else
                for(int s = total+positiveMod ; i >= total-negativeMod ; i--)
                    dp[s] = dp[s] || dp[s - weights[i]];

        }
        return dp[sum+total];
    }

    // O(ns) time and O(s) space
    public static int subsetCount(int n, int sum, int weights[]) {
        int negativeMod = 0, positiveMod = 0;
        for(int x : weights) {
            if(x < 0)
                negativeMod += -x;
            if(x > 0)
                positiveMod += x;
        }
        int total = negativeMod + positiveMod;
        int[] dp = new int[2*total+1];
        dp[total] = 1;
        for(int i = 0 ; i < n ; i++) {
            if(weights[i] < 0)
                for(int s = total-negativeMod ; s <= total+positiveMod ; s++)
                    dp[s] += dp[s - weights[i]];
            else
                for(int s = total+positiveMod ; s >= total-negativeMod ; s--)
                    dp[s] += dp[s - weights[i]];
        }
        return dp[sum+total];
    }
}
