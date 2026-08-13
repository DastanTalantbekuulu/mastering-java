package com.programs.challenges.hackerrank.arrayrotate;

public class ArrayRotate {
	
	
	static int[] rotLeft(int[]arr,int d){
		int n = arr.length;
		if(d>n){
			System.err.println("Rotation index greater then size of array");
			return arr;
		}
		swap(arr, 0, d-1);
		swap(arr, d, n-1);
		swap(arr, 0, n-1);
		
		return arr;
	}
	
	
	static void swap(int[]arr,int start,int end){
		int temp;
		while(start < end){
			temp = arr[start];
			arr[start] = arr[end];
			arr[end]=temp;
			start++;
			end--;
		}
	}
	
	public static void main(String[] args) {
		int [] arr = {1,2,3,4,5};
		
		for(int i : rotLeft(arr, 2)) {
			System.out.print(i+",");
		}
		
	}
	

}
