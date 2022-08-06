/*
Target sum problem (https://leetcode.com/problems/target-sum/)

V.V.Important :
This is a classic example where the input array may contain 0 as element.
And if in any subsetCount problem, the input array contains 0, then in the
recursice equation would have to be updated slightly as follows :

if(n > 0 && nums[n-1] == 0 && targetSum == 0)
    return countSubsets(n-1, targetSum, nums) + countSubsets(n-1, targetSum-nums[n-1], nums);
    OR
    return 2 * countSubsets(n-1, targetSum, nums);
if(targetSum == 0)
    return 1;
if(n == 0)
    return 0;
if(nums[n-1] > targetSum)
    return countSubsets(n-1, targetSum, nums);
return countSubsets(n-1, targetSum, nums) + countSubsets(n-1, targetSum-nums[n-1], nums);

TO BE MORE PRECISE :

if(n == 0 && targetSum == 0)
    return 1;
if(n == 0)
    return 0;
if(nums[n-1] > targetSum)
    return countSubsets(n-1, targetSum, nums);
return countSubsets(n-1, targetSum, nums) + countSubsets(n-1, targetSum-nums[n-1], nums);

This is because, if nums array has an element as 0, then there would me more than one
way of obtaining a 0 targetSum i.e. {} and {0}. And for x number of 0s in the nums
array, there would be 2^x ways of getting a targetSum of zero from them.

This thing also needs to be taken care of when intialising the bottom up 2D dp matrix,
and also for the bottom up 1D dp matrix (line 47 of below code).
In 2D dp matrix, in the second for loop, process all columns from index 0
In memoization, intialise on those (i, 0) to 1, where nums[i-1] > 0
 */

class Solution {
    public int findTargetSumWays(int[] nums, int target) {
        int sum = Arrays.stream(nums).sum();
        if((sum + target) % 2 != 0)
            return 0;
        if(target > sum || target < -sum)
            return 0;
        int targetSum = (sum + target)/2;
        return subsetCount(nums.length, targetSum, nums);
    }

    public int subsetCount(int n, int targetSum, int nums[]) {
        int dp[] = new int[targetSum+1];
        dp[0] = 1;

        for(int i = 0 ; i < n ; i++) {
            for(int sum = targetSum ; sum >= 0 ; sum--) { // here sum >=0 instead of sum > 0, to handle 0s in nums array
                if(nums[i] > sum)
                    break;
                dp[sum] = dp[sum] + dp[sum-nums[i]];
            }
        }
        return dp[targetSum];
    }
}
