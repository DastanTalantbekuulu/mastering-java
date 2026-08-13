package com.programs.challenges.exceptionhandling;

import java.util.Scanner;

public class ExceptionDemo {

	public static void main(String[] args) {
		
		String input;
		try(Scanner sc = new Scanner(System.in)){
			doProcess();
			input = sc.nextLine();
			if(input.equals("")){
				throw new MyException("Blank input");
			}
		}catch(MyException e){
			System.err.println(e);
		}
		
		System.out.println("Rest of code");
	}
	
	public static boolean doProcess() throws MyException{
		try{
			int i = 100/0;
			return true;
		}catch(Exception e){
			//throw new MyException().divideByZero();
			throw new MyException("Cannot divide by zero");
		}finally{
			System.out.println("Cleaning resources ");
			
		}
	}

}
