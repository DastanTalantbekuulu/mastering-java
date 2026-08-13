package com.programs.challenges.collections;

import java.util.Iterator;

public class LinkedStack<T> implements Stack<T> {

	private Node head;
	private int size; // number of items

	// nested class to define node
	private class Node {
		T item;
		Node next;
	}

	// Zero argument constructor
	public LinkedStack() {
		head = null;
		size = 0;
	}

	@Override
	public T pop() {
		T item = head.item;
		head = head.next;
		size--;
		return item;
	}

	@Override
	public void push(T item) {
		Node oldHead = head;
		head = new Node();
		head.item = item;
		head.next = oldHead;
		size++;
	}

	@Override
	public boolean isEmpty() {

		return (size == 0);
	}

	@Override
	public int size() {

		return size;
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedStackIterator();
	}
	
	private class LinkedStackIterator implements Iterator<T>{

		private int i = size;
		private Node first = head;
		
		@Override
		public boolean hasNext() {
			return (i > 0);
		}

		@Override
		public T next() {
			T item = first.item;
			first = first.next;
			i--;
			return item;
		}
		
	}

}
