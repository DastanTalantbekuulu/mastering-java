package com.programs.challenges;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueElements {

	public static void main(String[] args) {
		int[] intArr = { 1, 1, 1, 3, 3, 5, 9, 9, 8, 8 };
		getUniqueElementsUsingSet(intArr);
		getUniqueElementsWithoutSet(intArr);
	}

	public static void getUniqueElementsUsingSet(int[] arr) {
		System.out.println("Unique elements using SET");
		Integer[] array = new Integer[arr.length];
		for (int i = 0; i < arr.length; i++) {
			array[i] = Integer.valueOf(arr[i]);
		}
		Set<Integer> integerSet = new HashSet<>(Arrays.asList(array));
		System.out.println(Arrays.deepToString(integerSet.toArray()));

	}

	public static void getUniqueElementsWithoutSet(int[] array) {
		System.out.println("Unique elements without using SET");
		int temp = 0;
		for (int a : array) {
			if (temp != a) {
				System.out.print(a + " ");
				temp = a;
			}
		}
	}

	public static int[] shuffleArray(int[] array) {
		Random rgen = new Random(); // Random number generator

		for (int i = 0; i < array.length; i++) {
			int randomPosition = rgen.nextInt(array.length);
			int temp = array[i];
			array[i] = array[randomPosition];
			array[randomPosition] = temp;
		}

		return array;
	}

}
