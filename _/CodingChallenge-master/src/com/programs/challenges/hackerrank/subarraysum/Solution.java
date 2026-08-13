/**
 * @author pranshu.shrivastava
 * @date Mar 5, 2020
 */
package com.programs.challenges.hackerrank.subarraysum;

public class Solution {

	public static void main(String[] args) {
		int[] nums = { 23, 2, 4, 6, -7 };
		System.out.println(checkSubarraySum(nums, 6));

	}

	public static int checkSubarraySum(int[] nums, int k) {
		int max_so_far = 0;
		int max_ending_here = 0;

		for (int i = 0; i < nums.length; i++) {
			max_ending_here = max_ending_here + nums[i];
			if (max_ending_here < 0)
				max_ending_here = 0;
			else if (max_so_far < max_ending_here)
				max_so_far = max_ending_here;
			
		}
		System.out.println("");
		return max_so_far;

	}

}
