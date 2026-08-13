package com.programs.challenges;

import java.util.Scanner;

public class Fibonacci {

	static int i = 0, y = 1, j = 0;

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in);) {
			System.out.println("Please enter the limit of fibonacci series..");
			int limit = Integer.parseInt(sc.nextLine());
			System.out.println("Fibonacci Series Till - " + limit);
			showFibonacciSeries(limit);
		}

	}

	public static void showFibonacciSeries(int limit) {
		if (limit > 0) {
			j = i + y;
			i = y;
			y = j;
			System.out.print(j + " ");
			showFibonacciSeries(limit - 1);
		}
	}

}
