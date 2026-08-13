package com.mastering.concurrency.patterns.queue_block;

import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

public class TransferQueueDemo {

    static void main() {
        TransferQueue<String> queue = new LinkedTransferQueue<>();

        new Thread(new Producer(queue), "Producer").start();
        new Thread(new Consumer(queue), "Consumer").start();
    }

    private static void log(String msg) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.println(time + " " + msg);
    }

    @AllArgsConstructor
    static class Producer implements Runnable {
        private final TransferQueue<String> queue;

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                try {
                    String item = "'Item-" + i + "'";
                    log("Producer: Waiting to TRANSFER " + item + "...");

                    // transfer() BLOCKS until a Consumer calls take() for this specific element
                    queue.transfer(item);

                    log("Producer: Transfer of " + item + " COMPLETED.\n");
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    @AllArgsConstructor
    static class Consumer implements Runnable {
        private final TransferQueue<String> queue;

        @Override
        public void run() {
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(2000); // Simulate delay
                    log("Consumer: Ready to consume...");

                    String element = queue.take(); // Unblocks the producer

                    log("Consumer: CONSUMED " + element);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}