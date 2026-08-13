package com.programs.challenges.hackerrank.fraudactivity;

import java.util.Arrays;

public class Solution {

	static int activityNotifications(int[] expenditure, int d) {
		int[] counts = new int[201];
		for (int i = 0; i < d; i++) {
			counts[expenditure[i]]++;
		}

		int result = 0;
		for (int i = d; i < expenditure.length; i++) {
			int lower = 0;
			int leftNum = 0;
			while ((leftNum + counts[lower]) * 2 <= d) {
				leftNum += counts[lower];
				lower++;
			}

			int upper = counts.length - 1;
			int rightNum = 0;
			while ((rightNum + counts[upper]) * 2 <= d) {
				rightNum += counts[upper];
				upper--;
			}

			if (expenditure[i] >= lower + upper) {
				result++;
			}

			counts[expenditure[i - d]]--;
			counts[expenditure[i]]++;
		}
		return result;

	}

	static int getMedian(int[] sortedExp, int i, int j) {
		int [] arr = Arrays.copyOfRange(sortedExp, i, j);
		int median = 0;
		int len = arr.length;
		if (len % 2 != 0) {
			median = arr[len / 2];
		} else {
			median = (arr[(len - 1) / 2] + arr[len / 2]) / 2;
		}
		return median;
	}

	public static void main(String[] args) {

		int[] expenditure = {1,2,3,4,4,4,50};
		System.out.println(activityNotifications(expenditure, 4));
	}

}
