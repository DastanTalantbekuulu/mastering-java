package com.programs.challenges.hackerrank.arrayrotate;

import java.util.Arrays;

public class ZigZagArray {

    public static void main(String[] args) {

        int[] arr1 = {4, 3, 7, 8, 6, 2, 1};
        int[] arr2 = {4, 3, 7, 8, 6, 2, 1};

        System.out.println("ZigZag using sorting");
        convertToZigZagUsingSorting(arr1,arr1.length);
        printArray(arr1);

        System.out.println("ZigZag using flag");
        convertToZigZagUsingFlag(arr2,arr2.length);
        printArray(arr2);


    }

    static void printArray(int[]arr){
        for(int i : arr){
            System.out.print(i+" ");
        }
        System.out.println();
    }

    static void convertToZigZagUsingFlag(int[] arr, int n){
        boolean flag = true;// true for < false for >

        for(int i = 0; i <= n-2; i++) {
            if (flag) {

                if (arr[i] > arr[i+1]) {
                    swap(arr,i,i+1);
                }

            }else{
                if(arr[i] < arr[i+1]){
                    swap(arr,i,i+1);
                }
            }
            flag = !flag;
        }

    }




    static void convertToZigZagUsingSorting(int[]arr, int n){
        Arrays.sort(arr);
        int i = 1;
        while(i < n){
            swap(arr,i,i+1);
            i+=2;
        }


    }


    static void swap(int[] arr,int a,int b){
        int len = arr.length;
        if(b < len){
            int temp = arr[b];
            arr[b] = arr[a];
            arr[a] = temp;
        }
    }

}
