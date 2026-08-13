package com.programs.challenges;

import java.util.Arrays;

public class TestSort {

	public static void main(String[] args) {
		//int[] arr = {1, 2, 3, 4, 5, 6, 7};
		int[] arr = {1, 2, 5, 3,  8, 7, 6, 4};
		int[] newArr = new int[arr.length];
		
		//copying one array to another
		newArr=Arrays.copyOf(arr,arr.length);
		Arrays.sort(newArr);	
		//rotateArray(arr,1);
		System.out.println(Arrays.toString(newArr));
		countSwaps(arr);

	}
	
	
	public static void rotateArray(int[]arr,int d){
		int n = arr.length;
		if(d>n){
			System.err.println("Rotation index greater then size of array");
			return;
		}
		swap(arr, 0, d-1);
		swap(arr, d, n-1);
		swap(arr, 0, n-1);
	}
	
	
	public static void swap(int[]arr,int start,int end){
		int temp;
		while(start < end){
			temp = arr[start];
			arr[start] = arr[end];
			arr[end]=temp;
			start++;
			end--;
		}
	}
	
	public static void countSwaps(int[]arr) {
		int[] newArr = new int[arr.length];
		boolean iterate = true;
		while(iterate)
		{
			
		}
	}
	
	
	

}
