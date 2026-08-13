package com.programs.challenges.hackerrank.arraymanipulation;


/**
 * Given an array of distinct integers, find length of the longest subarray
 * which contains numbers that can be arranged in a continuous sequence.
 * <pre class"code">
 * EXAMPLE:
 * Input:  arr[] = {10, 12, 11};
 * Output: Length of the longest contiguous subarray is 3
 *
 * Input:  arr[] = {14, 12, 11, 20};
 * Output: Length of the longest contiguous subarray is 2
 *
 * Input:  arr[] = {1, 56, 58, 57, 90, 92, 94, 93, 91, 45};
 * Output: Length of the longest contiguous subarray is 5
 *
 * </pre>
 */
public class SubArrayWithContinousElements {

    public static void main(String[] args) {
        int arr[] = {1, 56, 58, 57, 90, 92, 94, 93, 91, 45};
        System.out.println("Length of the longest contiguous subarray is :"+findLengthOfSubarray(arr,arr.length));

    }

    static int findLengthOfSubarray(int[] arr, int n){

        if(n < 2)
            return n;

        int maxLen = 1;

        for(int i = 0; i < n - 1;i++){

            int max = arr[i]; int min = arr[i];
            for(int j = i+1; j < n; j++){
                max = getMax(max, arr[j]);
                min = getMin(min,arr[j]);

                if((max - min) == j - i){
                    maxLen = getMax(maxLen,max - min + 1);
                }
            }
        }

        return maxLen;

    }
    static int getMax(int x, int y){
        return (x > y) ? x : y;
    }

    static int getMin(int x, int y){
        return (x < y) ? x : y;
    }





}
