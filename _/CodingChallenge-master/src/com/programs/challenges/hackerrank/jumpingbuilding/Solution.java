/**
 * Given a zero-indexed array H of height of buildings, number of bricks b and number of ropes r. You start your journey from building 0 and move to adjacent building either using rope or bricks. You have limited number of bricks and ropes.
 * While moving from ith building to (i+1)th building,
 * if next building's height is less than or equal to the current building's height, you do not need rope or bricks.
 * if next building's height is greater than current building's height, you can either use one rope or (h[i+1] - h[i]) bricks.
 * So, question is How far can you reach from 0th building if you use bricks and ropes optimally? return index of building till which you can move.
 * Example 1:
 * Input : H = [4,2,7,6,9,11,14,12,8], b = 5, r = 2
 * Output: 8
 * Explanation: use rope to move from index 1 to index 2.
 * use 3 bricks to move from index 3 to index 4.
 * use 2 bricks to move from index 4 to index 5.
 * use rope to move from index 5 to index 6.
 * so we can reach at the end of the array using 2 ropes and 5 bricks.
 *
 */
package com.programs.challenges.hackerrank.jumpingbuilding;

public class Solution {

	private static int jumpBuilding(int [] arr, int b, int r) {
		int n = arr.length - 1;
		int t = r;
		int currentIndex = 0;
		
		for(int i = 0; i < arr.length - 1; i++) {
			if(i + 1 > n) {
				break;
			}
			
			if(arr[i] < arr[i+1]) {
				int brickNeeded = Math.abs(arr[i + 1] - arr[i]);
				if(brickNeeded > t && r != 0) {
					currentIndex = i + 1;
					r--;
				} else if(b >= brickNeeded){
					b -= brickNeeded;
					currentIndex = i + 1;
				} else {
					break;
				}
			} else {
				currentIndex = i + 1;
			}
			
		}
		
		return currentIndex;
	}
		
	public static void main(String[] args) {
		int [] arr = {4,2,7,6,9,11,14,12,8};
		int b = 5;
		int r = 2;
		System.out.println(jumpBuilding(arr, b, r));
	}

}
