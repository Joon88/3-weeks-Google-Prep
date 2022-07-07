/*
Minimum subset sum difference / Minimum sum partition / Minimum difference subsets
Link : https://practice.geeksforgeeks.org/problems/minimum-sum-partition3317/1

Basically we need to divide the input array to two subsets, such that their sum
difference is the minimum.

Soln - We just need to find the maxWeight(n, sum/2) as in 0/1 knapsack problem.
sum = subsetSum1 + subsetSum2
diff = abs(subsetSum1 - subsetSum2)
     = abs(sum - subsetSum2 - subsetSum2)
     = abs(sum - 2*subsetSum2)
 */
class Solution
{
    public int minDifference(int arr[], int n)
    {
        int sum = Arrays.stream(arr).sum();  // O(n)
        int subsetSum1 = maxSubsetSum(n, sum/2, arr);
        return Math.abs(sum-2*subsetSum1);

    }
    // O(ns) time and O(s) space, where n is arr size and s is sum of all array elements
    public int maxSubsetSum(int n, int targetSum, int arr[]) {
        int dp[] = new int[targetSum+1];
        for(int i = 0 ; i < arr.length ; i++) {
            for(int sum = targetSum ; sum > 0 ; sum--) {
                if(arr[i] > sum)
                    break;
                dp[sum] = Math.max(dp[sum], arr[i] + dp[sum-arr[i]]);
            }
        }
        return dp[targetSum];
    }
}
