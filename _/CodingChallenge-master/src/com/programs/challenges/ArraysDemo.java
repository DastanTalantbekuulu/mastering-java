package com.programs.challenges;

import java.util.Arrays;

public class ArraysDemo {

	public static void main(String[] args) {
		int arr[] = new int[20];
		arr[0] = 12;
		
		int capacity = arr.length;
		int n = 1;
		int key = 26;

		System.out.print("\nBefore Insertion: " + Arrays.toString(arr));

		// Inserting key
		insertSorted(arr, n, key, capacity);

		System.out.print("\nAfter Insertion: " + Arrays.toString(arr));

	}

	static void insertSorted(int arr[], int n, int key, int capacity) {
		
		if (n >= capacity)
			return;

		int i;
		for (i = n - 1; (arr[i] > key && i >= 0); i--)
			arr[i + 1] = arr[i];

		arr[i + 1] = key;

	}

}
