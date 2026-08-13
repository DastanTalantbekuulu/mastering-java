package com.programs.challenges;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;

public class CollectionsDemo {

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("Name", "Pranshu Shrivastava");
		map.put("Age", "26");
		map.put("City", "Mumbai");

		// iterateMap(map);
		// queueImpl();
		findDuplicateChar("AloremA lipsum ; ; gand mara 0 hi @");
	}

	public static void iterateMap(Map<String, String> map) {
		System.out.println("Using iterator---------------");
		Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) itr.next();
			System.out.println(entry.getKey() + " " + entry.getValue());
		}
		System.out.println("Using for loop----------------");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getKey());
		}
	}

	public static void findDuplicateChar(String string) {
		char[] carray = string.toCharArray();
		// System.out.println(Arrays.toString(carray));
		Map<Character, Integer> charmap = new HashMap<>();
		for (char c : carray) {
			if (Character.isLetter(c)) {
				if (charmap.containsKey(c)) {
					charmap.put(c, ((int) charmap.get(c) + 1));
				} else {
					charmap.put(c, 1);
				}
			}
		}	

		// Extract values greater than 1 and print
		for (Map.Entry<Character, Integer> e : CollectionsDemo.mapSort(charmap).entrySet()) {
			char key = e.getKey();
			int val = e.getValue();
			if (val > 1) {
				System.out.println(key + " " + val);
			}
		}
	}

	public static Map<Character, Integer> mapSort(Map<Character, Integer> charmap) {
		// Convert map to list and sort list using comparator
		List<Map.Entry<Character, Integer>> charlist = new LinkedList<>(charmap.entrySet());
		Collections.sort(charlist, new Comparator<Map.Entry<Character, Integer>>() {

			@Override
			public int compare(Entry<Character, Integer> o1, Entry<Character, Integer> o2) {
				return o1.getValue() - o2.getValue();
			}

		});

		// Populate a new map with sorted values
		Map<Character, Integer> sortedMap = new LinkedHashMap<>();
		for (Map.Entry<Character, Integer> entry : charlist) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}

		return sortedMap;
	}

	public static void queueImpl() {
		System.out.println("Queue-------------------");
		Queue<String> queue = new LinkedList<>();
		queue.add("Pranshu");
		queue.add("Mumbai");
		queue.add("Java");
		System.out.println("Peek :" + queue.peek());
		System.out.println("Contains Java :" + queue.contains("Java"));
		System.out.println("Queue :" + queue.toString());
		System.out.println("Poll :" + queue.poll());
		queue.add("Rahul");
		System.out.println("Queue :" + queue.toString());
		Iterator<String> qitr = queue.iterator();
		while (qitr.hasNext()) {
			System.out.println(qitr.next());
		}
	}

}
