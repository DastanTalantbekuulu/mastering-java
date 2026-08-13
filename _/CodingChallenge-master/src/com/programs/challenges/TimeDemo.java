package com.programs.challenges;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeDemo {

	private DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
	
	/**
	 * Show Current Time
	 */
	public void getLocalTime(){
		LocalTime lt = LocalTime.now();
		System.out.println("Current Time : "+lt.toString());
	}
	
	/**
	 * Show Current Date
	 */
	public void getLocalDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		String date = LocalDateTime.now().toLocalDate().format(formatter);
		System.out.println("Current Date : "+LocalDate.parse(date, formatter));
	}
	
	
	/**
	 * Gets difference between 2 dates using LocalDateTime
	 * @param date1 
	 * @param date2
	 * @see LocalDateTime
	 */
	public void diffDate(LocalDateTime date1,LocalDateTime date2){
		Duration duration = Duration.between(date2, date1);
		System.out.printf("Duration between %s and %s is %d seconds",date1.format(formatter),date2.format(formatter),duration.getSeconds());
	}
	
}
