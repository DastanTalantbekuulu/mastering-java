package com.programs.challenges;

import java.lang.reflect.Array;

public class GenericStackImpl<E> implements GenericStack<E> {
	private E elements[];
	private int top;
	private int size;
	
	public GenericStackImpl(Class<E> element,int size) {
		this.top = -1;
		this.size = size;
		elements = (E[])Array.newInstance(element, size);
	}
	
	

	@Override
	public void push(E e){
		if(top < size-1){
			elements[++top] = e;
		}else{
			throw new IllegalStateException("Stack is full");
		}
		
	}

	@Override
	public E pop(){
		if(!isFull()){
			return elements[top--];
		}else{
			throw new IllegalStateException("Stack is empty");
		}

	}

	@Override
	public E peek(){
		if(!isFull()){
			return elements[top];
		}else{
			throw new IllegalStateException("Stack is full");
		}
	}
	
	@Override
	public boolean isFull() {
		return (top == size-1);
	}

}
