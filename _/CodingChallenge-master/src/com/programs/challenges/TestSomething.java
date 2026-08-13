package com.programs.challenges;

import java.math.BigInteger;
import java.text.DateFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.lang3.SerializationUtils;


public class TestSomething {

	public static void main(String[] args) throws ParseException {

		// String names = "John Doe; Peter Benjamin Parker; Mary Jane Watson-Parker;
		// John Elvis Doe; John Evan Doe; Jane Doe; Peter Brian Parker";

		// System.out.println(solution(names,"Example"));
				
		//extraLongFactorials(25);
		
		print();
	
	}
	
	static void print() {
		System.out.println('j'+'a'+'v'+'a');
	}
	
	
	static void extraLongFactorials(int n) {
		BigInteger factorial = BigInteger.ONE;
        for(int i = n; i > 0; i--){
        	factorial = factorial.multiply(BigInteger.valueOf(i));
        }
        System.out.println(factorial);
    }



	
	
	
	
	
	
	
	public static void checkRegexPattern() {
		try(Scanner in = new Scanner(System.in)){
			int testCases = Integer.parseInt(in.nextLine());
			while(testCases>0){
				String pattern = in.nextLine();
	          	boolean isValid;
	            try {
	                Pattern  regex = Pattern.compile(pattern);
	                isValid = true;
	            }catch(PatternSyntaxException ex) {
	                isValid = false;
	            }
	            System.out.println(isValid ? "Valid" : "Invalid");
	            
			}
		}
		
	}
	
	public static String getSmallestAndLargest(String s, int k) {
        String smallest = "";
        String largest = "";
        int len = s.length();
        List<String> sub = new ArrayList<>();
        for(int i = 0 ; i <= len - k; i++) {
        	sub.add(s.substring(i, k + i));
        }
        Collections.sort(sub);
        smallest = sub.get(0);
        largest = sub.get(sub.size() - 1);
        return smallest + "\n" + largest;
    }
	
	
	public static String capitalize(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        return new StringBuilder(strLen)
            .append(Character.toTitleCase(str.charAt(0)))
            .append(str.substring(1))
            .toString();
    }

	public static String findDay(int month, int day, int year) {
		

		Calendar calendar = Calendar.getInstance();
		calendar.set(year, month - 1, day);
		int dayName = calendar.get(Calendar.DAY_OF_WEEK);
		DateFormatSymbols dfs = new DateFormatSymbols(Locale.getDefault());
		String weekdays[] = dfs.getWeekdays();
		return weekdays[dayName].toUpperCase();
	}

	public static void read() {
		int lineNo = 1;
		try (Scanner sc = new Scanner(System.in)) {

			while (sc.hasNext()) {
				System.out.printf("%d %s%n", lineNo, sc.nextLine());
				lineNo++;
			}
		}
	}

	public static String solution(String S, String C) {

		List<String> names = Arrays.asList(S.split("; "));

		return generateEmail(names, C);

	}

	private static String generateEmail(List<String> names, String C) {
		Map<String, Integer> nameCount = new HashMap<String, Integer>();

		String commaSeperatedEmails = "";

		for (int i = 0; i < names.size(); i++) {

			String name = names.get(i);

			String email = "";

			String[] splitedName = name.toLowerCase().split(" ");

			String fullName = "";

			String lastName = "";

			String firstName = splitedName[0] == null ? "" : splitedName[0];

			if (splitedName != null && splitedName.length > 2) {
				fullName = splitedName[0] + "." + splitedName[2];

				String temp = splitedName[2].replace("-", "");

				if (temp.length() > 8) {
					lastName = temp.substring(0, 8);
				} else {
					lastName = temp;
				}

			} else if (splitedName != null) {
				fullName = splitedName[0] + "." + splitedName[1];
				String temp = splitedName[1].replace("-", "");

				if (temp.length() > 8) {
					lastName = temp.substring(0, 8);
				} else {
					lastName = temp;
				}
			}

			if (nameCount.containsKey(fullName)) {
				nameCount.put(fullName, nameCount.get(fullName) + 1);
				int count = nameCount.get(fullName);
				email = name + " " + "<" + firstName + "." + lastName + count + "@" + C.toLowerCase() + ".com" + ">";
			} else {
				nameCount.put(fullName, 1);
				email = name + " " + "<" + firstName + "." + lastName + "@" + C.toLowerCase() + ".com" + ">";
			}

			if (i != names.size() - 1) {
				commaSeperatedEmails += email + "; ";
			} else {
				commaSeperatedEmails += email;
			}

		}

		return commaSeperatedEmails;
	}

}
