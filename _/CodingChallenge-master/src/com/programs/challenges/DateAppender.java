package com.programs.challenges;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class DateAppender {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	public static void main(String[] args) {
		
		System.out.println("Enter Date To Be Incremented By 2 Days :");
		try(Scanner sc = new Scanner(System.in)){
			String datestr = sc.nextLine();
			Date date = sdf.parse(datestr);
			System.out.println("Entered Date "+date);
			System.out.printf("Next working date is %s ",new DateAppender().incrementDate(date, 2));
		} catch (ParseException e) {
			System.err.printf("Error Parsing Date : %s",e);
		}
		
	}
	
	public String incrementDate(Date currentDate, int incr){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_WEEK, incr);
		System.out.println("After increment"+calendar.getTime());
		while(!isWorkingDay(calendar.getTime(),calendar)){
			System.out.println("Incrementing..");
			calendar.add(Calendar.DAY_OF_WEEK, 1);
		}
		return sdf.format(calendar.getTime());
	}
	
	public boolean isWorkingDay(Date date,Calendar calendar){
		boolean flag;
		calendar.setTime(date);
		System.out.println(calendar.getTime());
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		
		if((dayOfWeek == 7) || (dayOfWeek == 1)){
			System.out.println("Weekend "+dayOfWeek);
			flag = false;
		}else{
			flag = true;
		}
		
		return flag;		
	}

}
