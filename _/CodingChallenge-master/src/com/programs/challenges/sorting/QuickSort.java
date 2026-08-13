package com.programs.challenges.sorting;

import java.util.Arrays;

public class QuickSort {

	public static void main(String[] args) {
		Integer[] array = new Integer[]{13,14,12,50,15,10,9,11,22};
		
		System.out.println("Original : "+Arrays.toString(array));
		quickSort(array,0,array.length - 1);
		System.out.println("After Sorting : "+Arrays.toString(array));

	}
	
	public static void quickSort(Integer[] array,int low,int high){
		if(array == null || array.length == 0 ||low >= high){
			return ;
		}
		int middle = low + (high - low)/2;
		int pivot = array[middle];
		
		int i = low;int j = high;
		
		while(i <= j){
			while(array[i] < pivot ){
				i++;
			}
			while(array[j] > pivot){
				j--;
			}
			if(i<=j){
				swap(array,i,j);
				i++;
				j--;
			}
			if(low < j){
				quickSort(array, low, j);
			}
			if(high > i){
				quickSort(array, i, high);
			}
		}
		
	}
	
	public static void swap(Integer arr[],int i,int j){
		System.out.println("Swapping :"+arr[i]+" with "+arr[j]+": "+Arrays.toString(arr));
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

}
