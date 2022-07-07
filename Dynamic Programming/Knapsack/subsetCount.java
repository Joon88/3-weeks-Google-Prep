/*
Count subsets (subsequences, to be precise) with given sum
OR
Number of way to achieve the sum

Note : Input array is expected to have only +ve integers (>0). For input array with
       0, follow leetcode_494.java

Soln : Same as subsetSum, only difference is, here we are tracking number of possible
subsets instead of possibility of subset.
 */

public class Solution {
    static int[][] memo;
    public static void main(String args[]) {
        int[] nums = new int[]{3,1,2,3};
        int targetSum = 6;
        memo = new int[nums.length+1][targetSum+1];
        for(int[] a : memo) {
            Arrays.fill(a, -1);
            a[0] = 1;
        }
        for(int i = 1 ; i < memo[0].length ; i++)
            memo[0][i] = 0;
        System.out.println(countSubsets(nums.length, targetSum, nums));
    }
    // O(2^n) time and O(n) space
    public static int countSubsets(int n, int targetSum, int[] nums) {
        if(targetSum == 0)
            return 1;
        if(n == 0)
            return 0;
        if(nums[n-1] > targetSum)
            return countSubsets(n-1, targetSum, nums);
        return countSubsets(n-1, targetSum, nums) + countSubsets(n-1, targetSum-nums[n-1], nums);
    }
    // O(nS) time and space
    public static int countSubsets(int n, int targetSum, int[] nums) {
        if(memo[n][targetSum] >= 0)
            return memo[n][targetSum];
        if(nums[n-1] > targetSum)
            memo[n][targetSum] = countSubsets(n-1, targetSum, nums);
        else
            memo[n][targetSum] = countSubsets(n-1, targetSum, nums) + countSubsets(n-1, targetSum-nums[n-1], nums);
        return memo[n][targetSum];
    }
    // O(nS) time and space
    public static int countSubsets(int n, int targetSum, int[] nums) {
        int[][] dp = new int[nums.length+1][targetSum+1];
        for(int[] a : dp)
            a[0] = 1;

        for(int i = 1 ; i < dp.length ; i++) {
            for(int sum = 1 ; sum < dp[0].length ; sum++) {
                if(nums[i-1] > sum)
                    dp[i][sum] = dp[i-1][sum];
                else
                    dp[i][sum] = dp[i-1][sum] + dp[i-1][sum-nums[i-1]];
            }
        }
        return dp[n][targetSum];
    }
    // O(nS) time and O(s) space
    public static int countSubsets(int n, int targetSum, int[] nums) {
        int dp[] = new int[targetSum+1];
        dp[0] = 1;

        for(int i = 0 ; i < nums.length ; i++) {
            for(int sum = targetSum ; sum > 0 ; sum--) {
                if(nums[i] > sum)
                    break;
                dp[sum] = dp[sum] + dp[sum-nums[i]];
            }
        }
        return dp[targetSum];
    }
}
