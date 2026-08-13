package com.programs.challenges;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SwapString {

	public static void main(String[] args) {
		String first = "Hello";
		String second = "Pranshu";
		//swap(first, second);
		// reverseString(first+" "+second);
		// reverseStringBuilder(first+" "+second);
		//reverseList();
		System.out.println(doubleX2("axbcxx"));
	}

	public static void swap(String first, String second) {
		System.out.println("First : " + first + "\nSecond : " + second);

		first = first + second;
		second = first.substring(0, first.length() - second.length());
		first = first.substring(second.length());

		System.out.println("After Swap..");
		System.out.println("First : " + first + "\nSecond : " + second);
	}

	public static void reverseString(String value) {
		System.out.println("Reverse using array.." + value);
		char[] arr = value.toCharArray();
		for (int i = arr.length - 1; i >= 0; i--) {
			System.out.print(arr[i]);
		}
	}

	public static void reverseStringBuilder(String value) {
		System.out.println("\nReverse using StringBuilder.." + value);
		System.out.println(new StringBuilder(value).reverse());
	}

	public static void reverseList() {
		List<String> strlist = new ArrayList<>();
		strlist.add("First");
		strlist.add("Second");
		strlist.add("Third");
		for (int i = strlist.size() - 1; i >= 0; i--) {
			System.out.print(strlist.get(i) + " ");
		}
		Collections.reverse(strlist);
		System.out.println("\nUsing Collections" + strlist.toString());
	}

	public static boolean doubleX(String str) {
		int length = str.length() - 1;
		int firstIndexOfX = str.indexOf('x');
		if(length == firstIndexOfX){
			return false;
		}
		if(str.charAt(firstIndexOfX + 1) == 'x'){
			return true;
		}else{
			return false;
		}
		
	}
	
	public static boolean doubleX2(String str){
		int length = str.length();
		for(int i = 0; i < length; i++){
			if(str.charAt(i) == 'x' && i < length -1){
				if(str.charAt(i + 1) == 'x'){
					return true;
				}else{
					return false;
				}
			}
		}
		return false;
	}
	

}
