package com.programs.challenges.hackerrank.arraymanipulation;

/**
 * Given an array of integers and a number x, find the smallest subarray with sum greater than the given value.
 * <pre class="code">
 * Examples:
 * arr[] = {1, 4, 45, 6, 0, 19}
 *    x  =  51
 * Output: 3
 * Minimum length subarray is {4, 45, 6}
 *
 * arr[] = {1, 10, 5, 2, 7}
 *    x  = 9
 * Output: 1
 * Minimum length subarray is {10}
 * arr[] = {1, 2, 4}
 *     x = 8
 * Output : Not Possible
 * </pre>
 */
public class MinLenSubArray {

    public static void main(String[] args) {
        int[]arr = {1, 11, 100, 1, 0, 200, 3, 2, 1, 250};
        int x  =  251;

        int minLength = getMinSubArrayLength(arr,x);
        System.out.println(minLength);
    }


    static int getMinSubArrayLength(int[] arr, int x){
        int n = arr.length;
        int currentSum = 0;
        int minLen = n+1;

        int start=0;int end=0;

        while(end < n){

           while(currentSum <= x && end < n){
                currentSum += arr[end];
                end++;
           }

           while(currentSum > x && start < n){
               if(end - start < minLen){
                    minLen = end-start;
               }
               currentSum -= arr[start];
               start++;

           }

        }
        if(minLen > n){
            System.out.println("Not Possible");
        }
        return minLen;

    }
}
