/**
 * @author pranshu.shrivastava
 * @date Feb 18, 2020
 */
package com.programs.challenges;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;

public class UniversalDateConverter {

	public static void main(String[] args) {
		System.out.println(parseAndConvert("1990-02-07"));

	}

	private static String parseAndConvert(final String input) {
		String convertedDate=null;
		if (null == input) {
			throw new IllegalArgumentException("Passed null argument where a date-time string is expected.");
		} else if (input.length() <= 10) {
			LocalDateTime ld = null;
			for (DateTimeFormatter f : getFormatters()) {
				try {
					ld = LocalDateTime.parse(input, f);
					convertedDate = getFormattedDate(ld);
					System.out.println(ld);
				} catch (Exception e) {
					System.err.println("Moving to next formatter");
				}
			}
		// If over 10 in length AND ends in a Z.	
		} else if ((input.length() > 10) && input.substring(input.length() - 1).equalsIgnoreCase("Z")) {
			try {
				Instant ld = Instant.parse(input); 
				// Uses `DateTimeFormatter.ISO_INSTANT` formatter.
			} catch (Exception e) {
				throw new IllegalArgumentException("Unable to parse date-time string argument.");
			}
		} else if (input.length() > 10) {
			// TODO: Define another list of formatters to try here.
		} else if (input.length() == 0) {
			throw new IllegalArgumentException("Passed empty string where a date-time string is expected.");
		} else // Impossible-to-reach, for defensive programming.
		{
			throw new RuntimeException(
					"ERROR - Unexpectedly reached IF-ELSE when checking input argument.");
		}
		System.out.println("Done running.");
		return convertedDate;
	}
	
	/**
	 * Converts date string to dd/MM/YYYY.
	 *
	 * @param strDate            to be converted in dd/MM/YYYY format
	 * @return String of formatted date
	 * @throws ParseException             if date passed in request is not in yyyy-mm-dd format
	 */
	private static String getFormattedDate(final LocalDateTime date) throws ParseException {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/uuuu");

		return (date == null) ? "" : formatter.format(date);
	}
	
	private static List<DateTimeFormatter> getFormatters(){
		List<DateTimeFormatter> dateFormatters = new ArrayList<>(2);
		
		dateFormatters.add(DateTimeFormatter.ofPattern("uuuu-MM-dd [HH:mm:ss]")); 
		dateFormatters.add(DateTimeFormatter.ofPattern("dd-MM-uuuu [HH:mm:ss]"));
		
		return dateFormatters;
		
	}
}
