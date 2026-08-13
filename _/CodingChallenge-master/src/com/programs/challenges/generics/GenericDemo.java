package com.programs.challenges.generics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericDemo {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("101", "Shrivastsava");
		GenericClass<String> genc = new GenericClass<String>("Pranshu",map);
		System.out.println(genc.getT());
		System.out.println(genc.getMap().get("101"));
		
		//doProcess();
		doProcess(new Peacock());
		doProcess(new Cat());
		doProcess(new Dog());
	}
	
	public static void doProcess(){
		List<Animal> animalList = new ArrayList<>();
		animalList.add(new Dog());
		//animalList.add(new Animal());
		animalList.add(new Cat());
		for(Animal a : animalList){
			a.eat();
			a.eat("Meat");
			a.eat("Chicken", "Saturday");
		}
	}
	
	public static void doProcess(Object obj){
		Animal animal = null;
		if(obj instanceof Cat){
			animal = (Cat)obj;

			System.out.println(animal.animalType());
			System.out.println("Cat has wings :"+animal.hasWings());
			animal.eat();
		}
		if(obj instanceof Dog){
			animal = (Dog)obj;

			System.out.println(animal.animalType());
			System.out.println("Dog has wings :"+animal.hasWings());
			animal.eat();
		}
		if(obj instanceof Peacock){
			animal = (Peacock)obj;

			System.out.println(animal.animalType());
			System.out.println("Peacock has wings :"+animal.hasWings());
			animal.eat();
		}
		
		
	}
	
	

}
