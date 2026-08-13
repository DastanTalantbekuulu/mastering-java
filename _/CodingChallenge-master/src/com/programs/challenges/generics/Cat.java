package com.programs.challenges.generics;

public class Cat extends Animal{

	@Override
	public void eat() {
		
		System.out.println("This cat is eating");
	}
	
	@Override
	public boolean hasWings() throws RuntimeException {
		return false;
	}

	@Override
	public String animalType() {
		return "Cat is a Mammal";
	}
}
