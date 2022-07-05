/* Partition Equal Subset Sum
Given a non-empty array nums containing only positive integers,
find if the array can be partitioned into two subsets such that
the sum of elements in both subsets is equal.

Soln : The problem simply comes down to finding if there exists
a subset with sum equal to (sum of input array)/2, coz if it
exists, then surely the rest of the elements will sum up to the
remaining (sum of input array)/2. So, this is nothing but a
subsetSum problem.

O(nS) time, O(s) space
n = size of input arr, S = sum of all elements in input array
*/

class Solution {
    public boolean canPartition(int[] nums) {
        int sum = Arrays.stream(nums).sum();
        if(sum % 2 > 0) // if sum is not even, then equal partitioning is not possible
            return false;
        return isSubsetSumPossible(nums.length, sum/2, nums);
    }
    public boolean isSubsetSumPossible(int n, int targetSum, int[] nums) {
        boolean dp[] = new boolean[targetSum+1];
        dp[0] = true;

        for(int i = 0 ; i < nums.length ; i++) {
            for(int sum = targetSum ; sum > 0 ; sum--) {
                if(sum < nums[i])
                    break;
                dp[sum] = dp[sum] || dp[sum - nums[i]];
            }
        }
        return dp[targetSum];
    }
}
