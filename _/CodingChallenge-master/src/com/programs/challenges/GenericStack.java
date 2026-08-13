package com.programs.challenges;

public interface GenericStack<E> {

	void push(E element);
	public E pop();
	public E peek();
	public boolean isFull();
}
