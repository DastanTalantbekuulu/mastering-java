/**
 * @author pranshu.shrivastava
 * @date Dec 5, 2019
 */
package com.programs.challenges;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SubArraySum {

	public static void main(String[] args) {
		int[] arr = { 3, 4, -7, 1, 3, 3, 1, -4 };
		int sum = 7;
		printAllSubarrays(arr, sum);
	}

	private static void insert(Map<Integer, List<Integer>> dataMap, int key, int value) {
		if (!dataMap.containsKey(key)) {
			dataMap.put(key, new ArrayList<>());
		}
		dataMap.get(key).add(value);
	}

	private static void printAllSubarrays(int[] arr, int sum) {

		final Map<Integer, List<Integer>> dataMap = new HashMap<>();

		int sum_so_far = 0;
		
		// To handle the case when the subarray with given sum starts
		// from 0th index
		insert(dataMap, 0, -1);

		for (int i = 0; i < arr.length; i++) {

			sum_so_far += arr[i];

			if (dataMap.containsKey(sum_so_far - sum)) {
				List<Integer> subList = dataMap.get(sum_so_far - sum);
				for (Integer a : subList) {
					printSubArray(arr, a + 1, i);
				}
			}
			insert(dataMap, sum_so_far, i);
		}

	}

	private static void printSubArray(int[] arr, int begin, int end) {
		System.out.println(IntStream.range(begin, end + 1).mapToObj(k -> arr[k]).collect(Collectors.toList()));

	}

}
