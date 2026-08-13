package com.mastering.concurrency.patterns.queue_unblock;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ConcurrentLinkedQueueExample {
    private final Queue<String> queue;

    private volatile boolean cycle = true;

    ConcurrentLinkedQueueExample() {
        queue = new ConcurrentLinkedQueue<>();

        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
        producer.start();
        consumer.start();

        while (consumer.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.exit(0);
    }

    class Producer implements Runnable {

        public void run() {
            System.out.println("Producer started");
            try {
                for (int i = 1; i <= 10; i++) {
                    String str = "String" + i;
                    queue.add(str);
                    System.out.println("Producer added : " + str);
                    Thread.sleep(200);
                }
                cycle = false;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            String str;
            System.out.println("Consumer started\n");
            while (cycle || !queue.isEmpty()) {
                if ((str = queue.poll()) != null)
                    System.out.println("  consumer removed : " + str);
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    static void main() {
        new ConcurrentLinkedQueueExample();
    }
}
