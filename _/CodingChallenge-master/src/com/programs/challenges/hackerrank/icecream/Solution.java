/**
 * @author pranshu.shrivastava
 * @date Oct 31, 2019
 */
package com.programs.challenges.hackerrank.icecream;

import java.util.HashMap;
import java.util.Map;

public class Solution {

	public static void main(String[] args) {
		int[] cost = {2,3,4,2};

		whatFlavors(cost, 4);
	}

	static void whatFlavors(int[] cost, int money) {
		Map<Integer, Integer> complements = new HashMap<>();
		for (int i = 0; i < cost.length; i++) {
			int flavors = cost[i];
			if (complements.containsKey(flavors))
				System.out.println(complements.get(flavors)+1 + " " + (i+1));
			else
				complements.put(money - flavors, i);
		}
	}

}
