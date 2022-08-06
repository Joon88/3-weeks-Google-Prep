/*
LC-300 (https://leetcode.com/problems/longest-increasing-subsequence/)
 */

import java.util.*;

class Solution {
    int[] parent;
    int LISend = -1;
    int maxLIS = 0;
    // the above 3 are for getting the LIS string while using the memoization approach
    // After the code runs, we need to iterate the parent array from LISend idx to 0,
    // where at every step we jump to the parent idx, until the parent idx is -1 (i.e.
    // its the start of the LIS string)
    int memo[][];
    // Memoization approach : O(n^2) time and space
    public int lengthOfLIS(int[] nums) {
        parent = new int[nums.length]; // for getting the LIS string
        memo = new int[nums.length+1][nums.length+1];
        for(int a[] : memo)
            Arrays.fill(a, -1);
        Arrays.fill(parent, -1); // for getting the LIS string
        return recur(nums.length, Integer.MAX_VALUE, 0, nums);
    }
    private int recur(int n, int max, int maxIdx, int[] nums) {
        if(n == 0)
            return 0;
        if(maxIdx > 0 && memo[n][maxIdx] > -1)
            return memo[n][maxIdx];

        int elt = nums[n-1];
        if(elt < max) {
            int one = recur(n-1, max, maxIdx, nums);
            int two = 1 + recur(n-1, elt, n, nums);
            int rslt = Math.max(one, two);
            if(maxIdx > 0) {
                memo[n][maxIdx] = rslt;
                if (rslt == two) { // for getting the LIS string
                    parent[maxIdx - 1] = n - 1;
                    LISend = rslt > maxLIS ? maxIdx-1 : LISend;
                    maxLIS = Math.max(maxLIS, rslt);
                }
            }
            else
                return rslt;
        }
        else
            memo[n][maxIdx] = recur(n-1, max, maxIdx, nums);
        return memo[n][maxIdx];
    }

    // 1D bottom up DP/iterative : O(n^2) time and O(n) space
    public int lengthOfLIS(int[] nums) {
        int tempLISlen[] = new int[nums.length];
        int parent[] = new int[nums.length]; // not reqd for this problem, but can be used to find the actual LIS
        Arrays.fill(tempLISlen, 1);
        Arrays.fill(parent, -1);

        for(int i = 1 ; i < nums.length ; i++) {
            int j = 0;
            while(j < i) {
                if(nums[j] < nums[i]) {
                    int prevLen = tempLISlen[i];
                    int newLen = 1 + tempLISlen[j];
                    tempLISlen[i] = Math.max(prevLen, newLen);
                    parent[i] = newLen > prevLen ? j : parent[i];
                }
                j++;
            }
        }
        int rslt = Arrays.stream(tempLISlen).max().getAsInt();
        printLIS(nums, tempLISlen, parent, rslt); // prints the lis
        countLIS(tempLISlen, rslt); // total number of LIS
        return rslt;
    }

    public void printLIS(int[] nums, int[] tempLISlen, int[] parent, int rslt) {
        int i;
        for(i = 0 ; i < tempLISlen.length ; i++)
            if(tempLISlen[i] == rslt)
                break;

        // To find the LIS
        int k = i;
        Stack<Integer> stack = new Stack<>();
        while(parent[k] != -1) {
            stack.push(nums[k]);
            k = parent[k];
        }
        stack.push(nums[k]);
        System.out.println();
        while(!stack.isEmpty())
            System.out.print(stack.pop() + "-"); // this is the LIS
    }

    public void countLIS(int[] tempLISlen, int value) {
        int ctr = 0;
        for(int x : tempLISlen) {
            if(x == value)
                ctr++;
        }
        System.out.println(ctr);
    }
    // O(nlogn) approach with O(n) space
    // This approach only gives the size of the LIS
    public int lengthOfLIS(int[] nums) {
        int temp[] = new int[nums.length];
        Arrays.fill(temp, Integer.MAX_VALUE);
        temp[0] = nums[0];
        int j = 0;
        for(int i = 1 ; i < nums.length ; i++) {
            if(nums[i] > temp[j]) {
                temp[++j] = nums[i];
            } else {
                int pos = findSuitablePos(temp, nums[i]);
                temp[pos] = nums[i];
            }
        }
        return j+1;
    }
    private int findSuitablePos(int[] a, int x) {
        int idx = Arrays.binarySearch(a, x); // returns i if its present or -(i+1) if its not present
        return idx < 0 ? Math.abs(idx)-1 : idx;
    }
}
