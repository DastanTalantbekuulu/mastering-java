package com.programs.challenges;

import java.util.Scanner; 

public class FindLength {

	public static void main(String[] args) {
		try(Scanner sc = new Scanner(System.in);){
			System.out.println("Please enter the string..");
			String s = sc.nextLine();
			System.out.printf("Length of string \"%s\" is %d",s,getLength(s));
		}catch (Exception e) {
			System.err.println("Error in getting input");
		}

	}
	
	public static int getLength(String string){
		int length=0;
		for(char c : string.toCharArray()){
			length++;
		}
		return length;
	}

}
