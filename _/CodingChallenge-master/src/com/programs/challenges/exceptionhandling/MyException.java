package com.programs.challenges.exceptionhandling;

public class MyException extends Exception {

	public MyException(){
		super("MyException");
		
	}
	
	public MyException(String message){
		super(message);
	}
	
	public ArithmeticException divideByZero(){
		return new ArithmeticException("Cannot divide by zero");
	}
	
}
