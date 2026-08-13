package com.programs.challenges.lru;

public class TestLRU {

	public static void main(String[] args) {
		LRUCache lru = new LRUCache(5);
		System.out.println(lru.get(0));
		lru.set(0, 8);
		System.out.println(lru.get(0));
		lru.set(1, 9);
		lru.set(0, 1);
		
		System.out.println(lru.get(1));
		System.out.println(lru.get(0));
	}

}
