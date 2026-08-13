package com.programs.challenges.generics;

public abstract class Animal{
	
	public Animal(){
		
	}
	
	public Animal(String info){
		System.out.println(info);
	}
	
	public void eat(){
		System.out.println("This animal is eating");
	}
	
	String eat(String food){
		return "Eating :"+food;
	}
	
	public void eat(String food,String day){
		System.out.println("Eating :"+food+" Every :"+day);
	}
	
	public abstract String animalType();
	
	public boolean hasWings() throws RuntimeException{
		
		throw new RuntimeException();
		
	}
}
