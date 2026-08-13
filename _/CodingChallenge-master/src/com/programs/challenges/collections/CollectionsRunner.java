package com.programs.challenges.collections;

public class CollectionsRunner {

	public static void main(String[] args) {
	
		Stack<String> nameStack = new LinkedStack<String>();
		nameStack.push("Pranshu");
		nameStack.push("Shivansh");
		nameStack.push("Sheetal");
		nameStack.push("Rahul");
		nameStack.push("Sumit");
		
		System.out.println("Size of stack :"+nameStack.size());
		
		for(String s : nameStack){
			System.out.println(s);
		}
		
		System.out.println("Popping :"+nameStack.pop());
		System.out.println("Size of stack :"+nameStack.size());
		
		for(String s : nameStack){
			System.out.println(s);
		}
		
	}
	
	

}
