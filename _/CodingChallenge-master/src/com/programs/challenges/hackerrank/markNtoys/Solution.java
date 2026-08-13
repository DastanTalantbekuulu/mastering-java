package com.programs.challenges.hackerrank.markNtoys;

import java.util.Arrays;

public class Solution {
	
	// Complete the maximumToys function below.
    static int maximumToys(int[] prices, int k) {
    	int max = 0;
    	
    	Arrays.sort(prices);
    	
    	for(int i = 0 ; i < prices.length; i++) {
    		if(prices[i] <= k) {
    			max++;
    			k -= prices[i];
    		}
    	}    	
    	return max;
    }

	public static void main(String[] args) {
		int [] prices = {1,2,3,4};
		System.out.println(maximumToys(prices, 7));
	}

}
