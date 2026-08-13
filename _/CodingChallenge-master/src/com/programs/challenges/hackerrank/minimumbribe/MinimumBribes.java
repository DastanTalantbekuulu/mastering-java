package com.programs.challenges.hackerrank.minimumbribe;

public class MinimumBribes {
	// Complete the minimumBribes function below.
	static void minimumBribes(int[] q) {
		if (q.length == 0) {
			return;
		}

		boolean isChaotic = false;
		int totalBribes = 0;

		int maxBribe = 0;
		int i = 0;
		int j = 1;
		
		int front = 0;
		int end = q.length - 1;
		
		while (j < q.length) {

			if (q[i] > q[i + 1] && q[i] > q[i + 2] && q[i] > q[i + 3] && i + 1 != i + 2 && i + 1 != i + 3
					&& i + 3 != i + 2) {
				isChaotic = true;
				break;

			}

			j++;
			
		}

		if (isChaotic)
			System.out.println("Too chaotic");
		else
			System.out.println(totalBribes);

	}

	static void swap(int[] arr, int start, int end) {
		int temp = arr[start];
		arr[start] = arr[end];
		arr[end] = temp;
	}

	public static void main(String[] args) {
		int[] arr = { 1, 2, 5, 7, 8, 6, 4, 3 };
		minimumBribes(arr);
		for (int i : arr) {
			System.out.print(i + ",");
		}
	}

}
