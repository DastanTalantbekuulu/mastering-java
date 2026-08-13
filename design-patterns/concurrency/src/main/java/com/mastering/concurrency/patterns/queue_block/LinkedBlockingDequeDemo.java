package com.mastering.concurrency.patterns.queue_block;

import lombok.AllArgsConstructor;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class LinkedBlockingDequeDemo {

    private static final String LOG_EXTRACT = "Extract from map   : %s%n";
    private static final String LOG_INSERT = "Insert into deque  : %s%n";
    private static final String LOG_WAIT = "... waiting to insert: %s%n";
    private static final String LOG_SIZE = "--- deque.size=%d ---%n";
    private static final String LOG_HEAD = "\tRemove HEAD: %s%n";
    private static final String LOG_TAIL = "\tRemove TAIL: %s%n";

    static void main() throws InterruptedException {
        // 1. Prepare data (Month names)
        List<String> months = new ArrayList<>();
        for (Month m : Month.values()) {
            months.add(m.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        }
        System.out.printf("Collection list: %s%n%n", months);

        // 2. Create a bounded deque (capacity 6)
        BlockingDeque<String> deque = new LinkedBlockingDeque<>(6);

        Thread producer = new Thread(new Producer(months, deque), "Producer");
        Thread consumer = new Thread(new Consumer(deque), "Consumer");

        producer.start();
        consumer.start();

        // Wait for producer to finish
        producer.join();

        // Interrupt consumer (daemon-like behavior for this demo)
        consumer.interrupt();
        System.out.println("\nDemo finished.");
    }

    @AllArgsConstructor
    static class Producer implements Runnable {
        private final List<String> data;
        private final BlockingDeque<String> deque;

        @Override
        public void run() {
            Iterator<String> iter = data.iterator();
            String element = null;

            while (iter.hasNext() || element != null) {
                if (element == null) {
                    element = iter.next();
                    System.out.printf(LOG_EXTRACT, element);
                }

                // Try to offer to the FRONT of the deque
                // offerFirst returns false if deque is full
                if (deque.offerFirst(element)) {
                    System.out.printf(LOG_INSERT, element);
                    element = null; // Success, reset element
                } else {
                    System.out.printf(LOG_WAIT, element);
                    sleep(250);
                }
                System.out.printf(LOG_SIZE, deque.size());
            }

            // Allow consumer to catch up
            sleep(3500);
        }

        private void sleep(int ms) {
            try {
                TimeUnit.MILLISECONDS.sleep(ms);
            } catch (InterruptedException ignored) {
            }
        }
    }

    @AllArgsConstructor
    static class Consumer implements Runnable {
        private final BlockingDeque<String> deque;

        @Override
        public void run() {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    // Alternate between polling first and last
                    if (deque.size() % 2 != 0) {
                        String item = deque.pollFirst(1, TimeUnit.SECONDS);
                        if (item != null) System.out.printf(LOG_HEAD, item);
                    } else {
                        String item = deque.pollLast(1, TimeUnit.SECONDS);
                        if (item != null) System.out.printf(LOG_TAIL, item);
                    }
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            } catch (InterruptedException e) {
                // Expected exit
            }
        }
    }
}