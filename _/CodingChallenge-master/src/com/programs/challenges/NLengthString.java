package com.programs.challenges;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class NLengthString {

	public static void main(String[] args) {
		System.out.println("Enter the length");
		try(Scanner sc = new Scanner(System.in)){
			int n = Integer.parseInt(sc.nextLine());
			List<String> strlist = new ArrayList<String>();
			strlist.add("Pranshu");
			strlist.add("Sheetal");
			strlist.add("Sumit");
			strlist.add("Vijayendra");
			strlist.add("Rahul");
			strlist.add("Vishwanathan Shastri");
			strlist.add("Shivansh");
			
			System.out.println( findNlengthString(strlist,n) );			
		}
	}
	
	public static List<String> findNlengthString(List<String> list,int n) throws ArrayIndexOutOfBoundsException {
		if(n < 1){
			return null;
		}
		
		TreeMap<Integer, List<String>> map = new TreeMap<>();
		for(String s : list){
			Integer length = s.length();
			List<String> templist = map.get(length) != null ? map.get(length) : new ArrayList<String>();
			templist.add(s);
			map.put(length, templist);
		}
		
		System.out.println(Arrays.toString(map.descendingKeySet().toArray()));
		/*
		for(Map.Entry<Integer, List<String>> entries : map.entrySet()){
			System.out.println(entries.getKey()+" : "+entries.getValue());
		}
		*/
		return map.get(n);
		
	}

}
