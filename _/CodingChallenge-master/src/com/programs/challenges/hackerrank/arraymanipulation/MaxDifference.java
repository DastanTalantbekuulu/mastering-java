package com.programs.challenges.hackerrank.arraymanipulation;

/**
 * Given an array arr[] of integers,
 * find out the maximum difference between any two elements such that larger element appears
 * after the smaller number.
 * <pre class="code">
 * Input : arr = {2, 3, 10, 6, 4, 8, 1}
 * Output : 8
 * Explanation : The maximum difference is between 10 and 2.
 *
 * Input : arr = {7, 9, 5, 6, 3, 2}
 * Output : 2
 * Explanation : The maximum difference is between 9 and 7.
 * </pre>
 */
public class MaxDifference {
    public static void main(String[] args) {
        int arr[] = {1, 2, 90, 10, 110};
        System.out.println("Maximum differnce is " +findMaxDifference(arr, 5));
    }

    static int findMaxDifference(int[] arr, int n){

        if(n < 2){
            return 0;
        }

        int maxDiff = Integer.MIN_VALUE;
        int minElement = arr[0];
        for (int i = 0; i < n; i++){
            if(arr[i] - minElement > maxDiff){
                maxDiff = arr[i] - minElement;
            }
            if(arr[i] < minElement){
                minElement = arr[i];
            }
        }

        return maxDiff;
    }


}
