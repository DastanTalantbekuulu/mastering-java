package com.programs.challenges;

import java.util.Scanner;

public class EvenOdd {
	
	public static void main(String[] args) {
		System.out.println("Find even odd numbers using bitwise operator...");
		findEvenOdd();
	}
	
	private static void findEvenOdd(){		
		try(Scanner sc = new Scanner(System.in)){
			while(true){
				System.out.println("Please enter the number........Enter -0 to exit");
				int number = Integer.parseInt(sc.nextLine());
				if(number == -0){
					System.out.println("Exit");
					System.exit(0);
				}
				System.out.println("Evaluating :"+number);
				evaluate(number);
			}
		}catch(Exception e){
			System.err.println("Invalid entry");
			System.exit(-1);
		}
	}
	
	private static void evaluate(int number){
		if((number&1) == 0){
			System.out.println(number + " is even");
		}else{
			System.out.println(number + " is odd");
		}
	}

}
