package com.programs.challenges.hackerrank.hourglass;

public class HourglassSum {
	// Complete the hourglassSum function below.
	static int hourglassSum(int[][] arr) {
		int row = arr.length;
		int col = arr[0].length;
		
		if (row < 3 || col < 3) 
	        return -1;
		
		int maxSum = Integer.MIN_VALUE;
				
		
		for (int i = 0; i < row - 2; i++) {
			for (int j = 0; j < col - 2; j++) {
				
				int sum = (arr[i][j] + arr[i][j + 1] + arr[i][j + 2]) + 
						  (arr[i + 1][j + 1]) + 
						  (arr[i + 2][j] + arr[i + 2][j + 1] + arr[i + 2][j + 2]);

				
				maxSum = Math.max(maxSum, sum);
			}
		}

		return maxSum;

	}
	

	public static void main(String[] args) {
		int[][] arr = { { -1,-1,0,-9,-2,-2 }, { -2,-1,-6,-8,-2,-5 }, { -1,-1,-1,-2,-3,-4 }, { -1,-9,-2,-4,-4,-5 },
				{ -7,-3,-3,-2,-9,-9 }, { -1,-3,-1,-2,-4,-5 } };

		int result = hourglassSum(arr);

		System.out.println(result);
	}

}
