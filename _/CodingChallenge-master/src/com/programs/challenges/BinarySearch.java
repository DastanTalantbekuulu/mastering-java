package com.programs.challenges;

public class BinarySearch {

    public static int binarySearchRecursive(int [] arr, int element, int left, int right){

        if(arr.length == 0){
            return Integer.MIN_VALUE;
        }

        int mid = left + ((right - left)/2);

        if(arr[mid] == element){
            return mid;
        }

        if(element > arr[mid]){
            return binarySearchRecursive(arr,element,mid + 1,right);
        } else {
            return binarySearchRecursive(arr, element, left, mid - 1);
        }

    }

    public static int binarySearchIterative(int[] arr, int element, int left, int right){

        if(arr.length == 0)
            return Integer.MIN_VALUE;

        while(left <= right){
            int mid = left + ((right - left)/2);

            if(arr[mid] == element)
                return mid;

            if(element > arr[mid])
                left = mid + 1;
            else
                right = mid - 1;
        }

        return Integer.MIN_VALUE;

    }

    public static void main(String[] args) {
        int [] arr = {1,2,5,7,20,21,33,45,55,81,121,155};

        int index = binarySearchRecursive(arr,21,0,arr.length - 1);
        //int index = binarySearchIterative(arr,21,0,arr.length - 1);

        System.out.println("Found element at index : "+index);

    }


}
