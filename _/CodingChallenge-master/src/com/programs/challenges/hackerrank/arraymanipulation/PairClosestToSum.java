package com.programs.challenges.hackerrank.arraymanipulation;

/**
 * Given a sorted array and a number x, find a pair in array whose sum is closest to x.
 * <pre class="code">
 * EXAMPLE:
 * Input: arr[] = {10, 22, 28, 29, 30, 40}, x = 54
 * Output: 22 and 30
 *
 * Input: arr[] = {1, 3, 4, 7, 10}, x = 15
 * Output: 4 and 10
 * </pre>
 */
public class PairClosestToSum {
    public static void main(String[] args) {
        int[] arr = {10, 22, 28, 29, 30, 40};
        int x = 54;
        printPair(arr,x);

    }

    static void printPair(int[] arr,int x) {

        if (arr.length < 2){
            System.out.println("Need at-least 2 elements in array");
            return;
        }

        int minDiff = Integer.MAX_VALUE;

        int low = 0,high = arr.length - 1;

        int j = 0,k = 0;

        while(high > low){

            if(Math.abs(arr[low] + arr[high] - x) < minDiff) {
                minDiff = Math.abs(arr[low] + arr[high] - x);
                j = low;
                k = high;
            }

            if(arr[low] + arr[high] < x){
                low++;
            } else {
                high--;
            }

        }

        System.out.println("Closest pair is :["+arr[j]+","+arr[k]+"]");

    }


}
