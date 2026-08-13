package com.programs.challenges.algorithms;

import java.util.Arrays;
import java.util.Scanner;

public class AlgoExecuter {

	public static void main(String[] args) {
		//findFirstAndLastOccurence();
		//findElementInMatrix();
		//countNegativeElements();
		//hasArrayTwoCandidates(5);
		//getMaxCharSequence();
		//isUniqueChar("PRANSHUU");
		//pythagoreanTriplet();
		//rotateMatrix();
		//findPairsInArray();
	}
	
	public static void findPairsInArray(){
		int[] array = {1,2,3,5,7,4,9};
		AlgoHelper.findPairs(array, 10);
		AlgoHelper.findPairsUsingSort(array, 10);
	}
	
	
	public static void rotateMatrix(){
		int[][] matrix = { { 10, 20, 30, 40 }, 
				{ 15, 25, 35, 45 }, 
				{ 27, 29, 37, 48 }, 
				{ 32, 33, 39, 50 }};
		
		AlgoHelper.rotateMatrix(matrix);
	}
	
	public static void pythagoreanTriplet(){
		int[]arr = {1,3,2,4,6,5};
		System.out.println(AlgoHelper.containsPythagoreanTriplet(arr, arr.length));
	}
	
	public static void isUniqueChar(String seq){
		if(seq.length() > 128){
			System.out.println("Not all characters are unique");
			return;
		}
		char[] char_set = new char[seq.length()] ;
		for(int i=0;i < seq.length();i++){
			char val = seq.charAt(i);
			if(contains(char_set,val)){
				System.out.println("Not all characters are unique");
				return;
			}
			
			char_set[i] = val;
		}
		System.out.println("All characters are unique in sequence");
	}
	
	public static boolean contains(char[]char_set,char val){
		for(char c : char_set){
			if(c == val){
				return true;
			}
		}
		return false;
	}
	
	public static void getMaxCharSequence(){
		String seq = "AABCDDDEEFGG";
		AlgoHelper.maxSequence(seq);
	}
	
	public static void hasArrayTwoCandidates(int sum){
		int[]arr = {8,2,19,3,4,21,1,7,5,}; 
		int l=0,r=arr.length-1;
		System.out.println("Before sorting : "+Arrays.toString(arr));
		AlgoHelper.quickSort(arr, 0, r);
		System.out.println("After sorting : "+Arrays.toString(arr));
		while(l < r){
			if(arr[l] + arr[r] == sum){
				System.out.println(l+" "+r);
				return;
			}
			if(arr[l] + arr[r] > sum){
				r--;
			}else{
				l++;
			}
		}
		System.out.println("-1");
	}
	
	
	public static void countNegativeElements(){
		int[][] matrix = {{-3,-2,-1,1},{-2,-1,0,2},{-1,2,3,6},{4,9,11,12}};
		AlgoHelper.countNegativeValuesInMatrix(matrix, 4,0);
	}

	public static void findElementInMatrix() {
		int[][] matrix = { { 10, 20, 30, 40 }, 
						{ 15, 25, 35, 45 }, 
						{ 27, 29, 37, 48 }, 
						{ 32, 33, 39, 50 }, };

		AlgoHelper.searchRowColumnSortedMatrix(matrix, 4, 29);
		
	}

	public static void findFirstAndLastOccurence() {

		try (Scanner in = new Scanner(System.in);) {
			int t = in.nextInt();
			while (t > 0) {
				t--;
				int n = in.nextInt();
				int[] arr = new int[n];
				for (int i = 0; i < n; i++) {
					arr[i] = in.nextInt();
				}
				System.out.println("Array is :" + Arrays.toString(arr));
				int x = in.nextInt();
				System.out.println("Element to find :" + x);
				AlgoHelper.binarySearch(arr, x, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
