package com.mastering.concurrency.patterns.queue_block;

import lombok.AllArgsConstructor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueDemo {

    private static final String POISON_PILL = "DONE";

    private static final String[] MESSAGES = {
            "Mother went to cook dinner",
            "Mother called to the table",
            "Children are eating milk porridge",
            "What is father eating?"
    };

    static void main() {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1, true);

        Thread producer = new Thread(new Producer(queue), "Producer");
        Thread consumer = new Thread(new Consumer(queue), "Consumer");

        producer.start();
        consumer.start();
    }

    @AllArgsConstructor
    static class Producer implements Runnable {
        private final BlockingQueue<String> queue;

        @Override
        public void run() {
            try {
                int count = 0;
                for (String msg : MESSAGES) {
                    System.out.println("[Producer] Putting: " + msg);
                    queue.put(msg);

                    // Simulate work for the first few messages
                    if (++count < 3) {
                        Thread.sleep(2000);
                    }
                }
                // Signal end of processing
                queue.put(POISON_PILL);
                System.out.println("[Producer] Sent POISON_PILL");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Producer] Interrupted");
            }
        }
    }

    @AllArgsConstructor
    static class Consumer implements Runnable {
        private final BlockingQueue<String> queue;

        @Override
        public void run() {
            try {
                String msg;
                // Take blocks until an element is available
                while (!(msg = queue.take()).equals(POISON_PILL)) {
                    System.out.println("[Consumer] Received: " + msg);
                }
                System.out.println("[Consumer] Received POISON_PILL. Exiting.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("[Consumer] Interrupted");
            }
        }
    }
}