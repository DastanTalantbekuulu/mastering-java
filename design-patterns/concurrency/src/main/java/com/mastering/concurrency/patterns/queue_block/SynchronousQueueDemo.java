package com.mastering.concurrency.patterns.queue_block;

import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class SynchronousQueueDemo {

    private static final String POISON_PILL = "DONE";
    private static final String[] MESSAGES = {
            "Mother went to cook dinner",
            "Mother called to the table",
            "Children are eating milk porridge",
            "What is father eating?"
    };

     static void main() {
        // SynchronousQueue has NO capacity.
        // Put blocks until Take, Take blocks until Put.
        BlockingQueue<String> drop = new SynchronousQueue<>();

        new Thread(new Producer(drop), "Producer").start();
        new Thread(new Consumer(drop), "Consumer").start();
    }

    private static void log(String msg) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(time + " " + msg);
    }

    @AllArgsConstructor
    static class Producer implements Runnable {
        private final BlockingQueue<String> drop;

        @Override
        public void run() {
            try {
                for (String msg : MESSAGES) {
                    log("*** Producer: Preparing to put '" + msg + "'");
                    // This will BLOCK until consumer takes it
                    drop.put(msg);
                    log("*** Producer: Successfully put message\n");
                }
                drop.put(POISON_PILL);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Producer Interrupted");
            }
        }
    }

    @AllArgsConstructor
    static class Consumer implements Runnable {
        private final BlockingQueue<String> drop;

        @Override
        public void run() {
            try {
                String msg;
                while (true) {
                    log("--- Consumer: Ready to take (waiting 3s)...");
                    Thread.sleep(3000); // Simulate slow consumer

                    // This will BLOCK until producer puts something
                    msg = drop.take();

                    if (msg.equals(POISON_PILL)) break;

                    System.out.println("--> Consumer RECEIVED: " + msg);
                    log("--- Consumer: Processing finished ---\n");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Consumer Interrupted");
            }
        }
    }
}