public class Solution {
    private static int[][] memo;
    public static void main(String args[]) {
        int[] arr = new int[]{3, 34, 4, 12, 5, 2};
        int targetSum = 9;
        memo = new int[arr.length+1][targetSum+1];
        for(int[] a : memo) {
            Arrays.fill(a, -1);
            a[0] = 1; // base case covered here
        }
        for(int i = 1 ; i < memo[0].length ; i++) {
            memo[0][i] = 0;
        }
        System.out.println(isSubsetSumPossible(arr.length, targetSum, arr));
    }

    // Recursive brute-force approach : O(2^n) time and O(n) space, where n = number of elements in the array
    public static boolean isSubsetSumPossible(int n, int targetSum, int[] arr) {
        if(targetSum == 0)
            return true;
        if(n == 0 && targetSum != 0) // the && targetSum != 0 is not actually required
            return false;
        if(arr[n-1] > targetSum)
            return isSubsetSumPossible(n-1, targetSum, arr);
        return isSubsetSumPossible(n-1, targetSum, arr) || isSubsetSumPossible(n-1, targetSum-arr[n-1], arr);
    }

    // Memoization : O(nS) time (number of unique subproblems) and O(nS) space where n is number of elts in arr, and S is the targetSum
    public static boolean isSubsetSumPossible(int n, int targetSum, int[] arr) {
        if(targetSum == 0)
            return true;
        if(n == 0)
            return false;
        
        if(memo[n][targetSum] > -1)
            return memo[n][targetSum] == 1;
        if (targetSum < arr[n - 1]) {
            memo[n][targetSum] = isSubsetSumPossible(n - 1, targetSum, arr) ? 1 : 0;
        } else if (n > 0 && targetSum > 0) {
            memo[n][targetSum] = (isSubsetSumPossible(n - 1, targetSum, arr) || isSubsetSumPossible(n - 1, targetSum - arr[n - 1], arr)) ? 1 : 0;
        }
        return memo[n][targetSum] == 1;
    }

    // Bottom-up 2-D DP : O(nS) time and O(nS) space where n is number of elts in arr, and S is the targetSum
    public static boolean isSubsetSumPossible(int n, int targetSum, int[] arr) {
        boolean dp[][] = new boolean[n+1][targetSum+1];
        for(boolean[] a : dp)
            a[0] = true;
        for(int i = 1 ; i < dp.length ; i++) {
            for(int sum = 1 ; sum < dp[0].length ; sum++) {
                if(arr[i-1] > sum)
                    dp[i][sum] = dp[i-1][sum];
                else
                    dp[i][sum] = dp[i-1][sum] || dp[i-1][sum-arr[i-1]];
            }
        }
        return dp[n][targetSum];
    }

    // Bottom-up 1-D DP : O(nS) time O(s) space (similar concept as the 01-knapsack problem)
    public static boolean isSubsetSumPossible(int n, int targetSum, int[] arr) {
        boolean dp[] = new boolean[targetSum+1];
        dp[0] = true; // base case
        for(int i = 0 ; i < arr.length ; i++) {
            for(int sum = targetSum ; sum > 0 ; sum--) {
                if(arr[i] > sum)
                    break;
                dp[sum] = dp[sum] || dp[sum - arr[i]];
            }
        }
        return dp[targetSum];
    }
}
