package com.programs.challenges.sorting;

import java.util.Arrays;

public class SortingArray {

	public static void main(String[] args) {
		int [] ids = {101,102,112,100,99,152,129};
		System.out.println("Original Array : "+Arrays.toString(ids));
		reverseArray(ids);
		//sortAscending(ids);
		//sortDecending(ids);
	}
	
	public static void sortAscending(int [] ids){
		System.out.println("Ascending order : ");
		Arrays.sort(ids);
		System.out.println(Arrays.toString(ids));
	}

	public static void sortDecending(int [] ids){
		System.out.println("Descending order : ");
		
	}
	
	public static void reverseArray(int [] ids){
		System.out.println();
		int length = ids.length;
		for(int i=0;i < length/2;i++){
			int temp = ids[i];
			ids[i] = ids[length - 1 - i];
			ids[length -1 -i] = temp;
		}
		System.out.println("Reverse original order : "+Arrays.toString(ids));
	}
	
	
}

