package com.programs.challenges.collections;

public interface Stack<T> extends Iterable<T>{

	T pop(); // return the top item and removes it from stack

	void push(T item); // adds an item to the stack

	boolean isEmpty(); // returns true if stack is empty, false otherwise

	int size(); // returns the number of items in stack
}
