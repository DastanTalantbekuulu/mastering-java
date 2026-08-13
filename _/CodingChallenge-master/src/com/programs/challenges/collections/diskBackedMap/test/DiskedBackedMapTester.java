package com.programs.challenges.collections.diskBackedMap.test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.programs.challenges.collections.diskBackedMap.DiskBackedMap;

public class DiskedBackedMapTester {

	public static void main(String[] args) {
		DiskedBackedMapTester tester = new DiskedBackedMapTester();
		File root = new File("/usr/local/java/");
		int start_time = LocalDateTime.now().getSecond();
		DiskBackedMap<String, Integer> dbm = new DiskBackedMap<>(root);
		dbm.setMaxCapacity(1000);
		for (int i = 0; i < 1000; i++) {
			dbm.put("key" + i, (int) (Math.random() * 10));
		}
		// Running all tests
		tester.iteratorTest(dbm);
		int end_time = LocalDateTime.now().getSecond();
		System.out.println("Time taken:" + (start_time - end_time));
		
		tester.synchronizationTest(dbm);

		
	}
	
	public void synchronizationTest(DiskBackedMap<String, Integer> dbm){
		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					dbm.put("PF:Key" + i, i);
					if (i == 50) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					System.out.println(dbm.get("PF:Key" + i));
					if (i == 50) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}

			}
		});
		t1.start();
		t2.start();

	}

	public void iteratorTest(DiskBackedMap<String, Integer> dbm) {
		Iterator<Entry<String, Integer>> iterator = dbm.entrySet().iterator();
		while (iterator.hasNext()) {
			Entry<String, Integer> entry = iterator.next();
			if (entry.getKey().equals("key8")) {
				System.out.println("Removing key");
				iterator.remove();
			}
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

	}
}
