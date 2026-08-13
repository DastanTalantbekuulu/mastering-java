package com.programs.challenges;

public class StackDemo {

	public static void main(String[] args) {
		Integer i1 = Integer.valueOf(1);
		Integer i2 = Integer.valueOf(2);
		Integer i3 = Integer.valueOf(3);
		
		GenericStack<Integer> stack = new GenericStackImpl<>(Integer.class, 3);
		stack.push(i1);
		stack.push(i2);
		System.out.println(stack.peek());
		stack.pop();
		System.out.println(stack.peek());
		stack.pop();
		stack.pop();
		stack.pop();
	}

}
