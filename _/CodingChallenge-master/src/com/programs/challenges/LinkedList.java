package com.programs.challenges;


public class LinkedList {

	private static int counter;
	private Node head;

	public LinkedList() {
	}

	public void add(Object data) {
		if (head == null) {
			head = new Node(data);
		}
		Node temp = new Node(data);
		Node current = head;

		if (current != null) {
			while (current.getNext() != null) {
				current = current.getNext();
			}

			current.setNext(temp);
		}

		incrementCounter();

	}

	/**
	 * Inserts the specified element at specified position
	 * 
	 * @param data
	 * @param index
	 */
	public void add(Object data, int index) {
		Node temp = new Node(data);
		Node current = head;
		if (current != null) {
			// crawl to the requested index or the last element in the list,
			// whichever comes first
			for (int i = 0; i < index && current.getNext() != null; i++) {
				current = current.getNext();
			}
			temp.setNext(current.getNext());
			current.setNext(temp);

			incrementCounter();
		}

	}

	public Object get(int index) {
		// returns the element at the specified position in this list.

		// index must be 1 or higher
		if (index < 0)
			return null;
		Node current = null;
		if (head != null) {
			current = head.getNext();
			for (int i = 0; i < index; i++) {
				if (current.getNext() == null)
					return null;

				current = current.getNext();
			}
			return current.getData();
		}
		return current;

	}

	// removes the element at the specified position in this list.
	public boolean remove(int index) {

		// if the index is out of range, exit
		if (index < 1 || index > size())
			return false;

		Node current = head;
		if (head != null) {
			for (int i = 0; i < index; i++) {
				if (current.getNext() == null)
					return false;

				current = current.getNext();
			}
			current.setNext(current.getNext().getNext());

			// decrement the number of elements variable
			decrementCounter();
			return true;

		}
		return false;
	}

	// returns the number of elements in this list.
	public int size() {
		return getCounter();
	}

	public String toString() {
		String output = "";

		if (head != null) {
			Node current = head.getNext();
			while (current != null) {
				output += "[" + current.getData().toString() + "]";
				current = current.getNext();
			}

		}
		return output;
	}

	private class Node {
		// reference to the next node in the chain, or null if there isn't one.
		Node next;

		// data carried by this node. could be of any type you need.
		Object data;

		// TreeNode constructor
		public Node(Object dataValue) {
			next = null;
			data = dataValue;
		}

		// another TreeNode constructor if we want to specify the node to point to.
		@SuppressWarnings("unused")
		public Node(Object dataValue, Node nextValue) {
			next = nextValue;
			data = dataValue;
		}

		// these methods should be self-explanatory
		public Object getData() {
			return data;
		}

		@SuppressWarnings("unused")
		public void setData(Object dataValue) {
			data = dataValue;
		}

		public Node getNext() {
			return next;
		}

		public void setNext(Node nextValue) {
			next = nextValue;
		}

	}

	private static int getCounter() {
		return counter;
	}

	private static void incrementCounter() {
		counter++;
	}

	private void decrementCounter() {
		counter--;
	}

}
