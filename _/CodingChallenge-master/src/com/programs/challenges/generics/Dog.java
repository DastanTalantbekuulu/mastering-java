package com.programs.challenges.generics;

public class Dog extends Animal{

	public Dog(){
	}
	
	@Override
	public void eat() {
		System.out.println("This dog is eating");
	}
	
	@Override
	public boolean hasWings() {
		return false;
	}

	@Override
	public String animalType() {
		
		return "Dog is a Mammal";
	}
}
