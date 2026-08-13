package com.programs.challenges.algorithms;

import java.util.Arrays;

public class AlgoHelper {
	
	public static void findPairs(int[]arr,int x){
		long start_time = System.currentTimeMillis();
		int pairsFound = 0;
		for(int i=0;i < arr.length;i++){
			for(int j=i+1;j<arr.length;j++){
				if(arr[i]+arr[j] == x){
					pairsFound += 2; 
					System.out.println("Pairs found at index:"+i+" "+j);
				}
			}
		}
		System.out.println("Pairs found:"+pairsFound);
		System.out.println("Completed in :"+(System.currentTimeMillis() - start_time)+"ms");
	}
	
	public static void findPairsUsingSort(int[]arr,int x){
		long start_time = System.currentTimeMillis();
		System.out.println("Sorting array");
		quickSort(arr, 0, arr.length - 1);
		int pairCounter = 0;
		int l = 0,r = arr.length - 1;
		while(l < r){
			if(arr[l] + arr[r] == x){
				pairCounter += 2;
				l++;
			}else if(arr[l] + arr[r] > x){
				r--;
			}else {
				l++;
			}
		}
		System.out.println("Pairs found:"+pairCounter);
		System.out.println("Completed in :"+(System.currentTimeMillis() - start_time)+"ms");
	}
	
	public static void rotateMatrix(int[][]mat){
		int N = mat[0].length;
		displayMatrix(mat, N);
		for(int i=0;i<N/2;i++){
			for(int j=i;j<N-i-1;j++){
				int temp = mat[i][j];
				mat[i][j] = mat[j][N-i-1];
				mat[j][N-i-1] = mat[N-i-1][N-j-1];
				mat[N-i-1][N-j-1] = mat[N-j-1][i];
				mat[N-j-1][i] = temp;
			}
		}
		displayMatrix(mat, N);
	}
	
	public static void  displayMatrix(int[][]mat,int N){
		for(int i = 0;i<N;i++){
			for(int j = 0;j<N;j++){
				System.out.print(mat[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	public static boolean containsPythagoreanTriplet(int[]arr,int n){
		for(int a=0;a<n;a++){
			arr[a] = arr[a]*arr[a];
		}
		
		Arrays.sort(arr);
		System.out.println("After sorting in increasing order"+Arrays.toString(arr));
		for(int i = n-1;i>=2;i--){
			int l = 0;
			int r = i-1;
			while(l < r){
				if(arr[l] + arr[r] == arr[i]){
					return true;
				}
				if(arr[l]+arr[r] > arr[i]){
					r--;
				}else{
					l++;
				}
			}
		}
			
		return false;
	}
	
	public static void maxSequence(String seq){
		char[] seqarr = seq.toCharArray();
		int max_count = 0;
		char max_char='\0';
		char prev_char='\0';
		int count = 0;
		for(char currentchar : seqarr){
			if(prev_char == currentchar){
				count++;
			}else{
				count = 1;
			}
			if(count > max_count){
				max_count = count;
				max_char = currentchar;
			}
			
			prev_char = currentchar;
			
		}
		System.out.println(max_char +" "+max_count);
	}
	
	
	
		
	public static void quickSort(int[] arr,int low,int high){
		int i = low,j = high;
		int pivot = arr[low + (high - low)/2];
		while(i <= j){
			while(arr[i] < pivot ){
				i++;
			}
			while(arr[j] > pivot){
				j--;
			}
			if(i <= j){
				exchange(arr,i,j);
				i++;
				j--;
			}
			
		}
		if(low < j){
			quickSort(arr, low, j);
		}
		if(i < high){
			quickSort(arr, i, high);
		}
	}
	
	public static void exchange(int[]arr,int low,int high){
		int temp = arr[low];
		arr[low] = arr[high];
		arr[high] = temp;
	}
	
	/**
	 * Count negative elements in matrix
	 * @param matrix
	 * @param n
	 * @param m
	 */
	public static void countNegativeValuesInMatrix(int[][]matrix,int n,int m){
		int i = m,j = n-1,count = 0;
		while(i < n && j >= 0){
			if(matrix[i][j] >= 0){
				j--;
			}else{
				count += (j+1);
				i++;
			}
		}
		System.out.println(count);
	}
	
	
	
	/**
	 * Finds element from RowNColumn wise sorted matrix
	 * @param matrix
	 * @param n no. of columns
	 * @param x element to find
	 */
	public static void searchRowColumnSortedMatrix(int[][]matrix,int n,int x ){
		
		int i = 0,j = n-1;
		while(i < n && j > 0){
			if(matrix[i][j] == x){
				System.out.println("Element found at :"+i+" "+j);
				return;
			}
			if(matrix[i][j] > x){
				j--;
			}else{
				i++;
			}
		}
		System.out.println("-1");
	}
	
	
	/**
	 * finds first & last occurence of given element
	 * @param arr array
	 * @param x element to find
	 * @param n length of array
	 */
	public static void binarySearch(int [] arr,int x,int n){
		int beginindex = 0;int length = n-1;
		while(beginindex <= length){
			int mid = beginindex + (length - beginindex)/2;
			if(arr[mid] == x){
				while(beginindex <= mid && arr[beginindex] != x){
					beginindex++;
				}
				while(length >= mid && arr[length] != x){
					length--;
				}
				System.out.println(beginindex+" "+length);
				return;
			}else if(arr[mid] > x){
				length = mid - 1;
			}else{
				beginindex = mid + 1;
			}
		}
		System.out.println("-1");
	}
	
	/**
	 * Finds element in sorted array using iteration
	 * @param arr
	 * @param elementToFind
	 * @return
	 */
	public static int binarySearch(int [] arr,int elementToFind){
		
		int beginIndex = 0;int length = arr.length -1;
		while(beginIndex <= length){
			int mid = beginIndex + (length - beginIndex)/2;
			if(arr[mid] == elementToFind){
				return mid;
			}
			
			if(arr[mid] > elementToFind){
				length = length - 1; 
			}else{
				beginIndex = beginIndex + 1;
			}
			
			
		}
		
		
		return -1;
	}
	
	
	/**
	 * Finds element in sorted array using recursion
	 * @param arr
	 * @param beginIndex
	 * @param length
	 * @param elementToFind
	 * @return
	 */
	public static int binarySearch(int [] arr,int beginIndex,int length,int elementToFind){
		
		if(length >= beginIndex){
			int mid = beginIndex + (length - beginIndex)/2;
			if(arr[mid] == elementToFind){
				return mid;
			}
			
			if(arr[mid] > elementToFind){
				return binarySearch(arr, 0, mid - 1, elementToFind);
			}else{
				return binarySearch(arr, mid + 1, length, elementToFind);
			}
			
		}	
		
		return -1;
		
	}
}
