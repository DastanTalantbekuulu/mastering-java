package com.programs.challenges.java8.optional;

import java.util.Optional;

public class OptionalDemo {

	public static void main(String[] args) {
		final String value = "1";
		System.out.println("Is the value null or empty :"+optionalNullCheck(value));
		System.out.println("Is the value null or empty :"+optionalNullCheckWithEmptyFilter(value));

	}
	
		
	private static boolean optionalNullCheck(final String value) {
		final Optional<String> val = Optional.ofNullable(value);
		if(val.isPresent()) {
			return false;
		} else {
			return true;
		}
	}
	
	private static boolean optionalNullCheckWithEmptyFilter(final String value) {
		final Optional<String> val = Optional.ofNullable(value)
				.filter(v -> v.trim().length() > 0)
				.map(v -> v.toUpperCase());
		if(val.isPresent()) {
			return false;
		} else {
			return true;
		}	
	}

}
