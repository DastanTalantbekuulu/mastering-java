package com.programs.challenges;

public class MyPrimeNumCheck {

	public static void main(String[] args) {
		MyPrimeNumCheck mpc = new MyPrimeNumCheck();
		System.out.println("Is 17 prime number? " + mpc.isPrimeNumber(17));
		System.out.println("Is 19 prime number? " + mpc.isPrimeNumber(19));
		System.out.println("Is 15 prime number? " + mpc.isPrimeNumber(15));
	}
	
	private boolean isPrimeNumber(int number){
		for(int i=2;i<=number/2;i++){
			if(number % i == 0){
				return false;
			}
		}
		return true;
	}

}
