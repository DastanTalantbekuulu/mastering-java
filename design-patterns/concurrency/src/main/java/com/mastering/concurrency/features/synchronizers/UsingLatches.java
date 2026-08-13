package com.mastering.concurrency.features.synchronizers;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Latches are used to delay the progress of threads until it reach a terminal
 * state
 * <p>
 * Most common implementation is CountDownLatch.
 * <p>
 * In CountDownLatch, each event adds 1. When ready, countDown() is called,
 * decrementing by counter by 1. await() method releases when counter is 0.
 * <p>
 * Single use synchronizer.
 */
public class UsingLatches {

    static void main() {
        var executor = Executors.newCachedThreadPool();
        var latch = new CountDownLatch(3);
        Runnable r = () -> {
            try {
                Thread.sleep(1000);
                System.out.println("Service in " + Thread.currentThread().getName() + " initialized.");
                latch.countDown();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };
        executor.execute(r);
        executor.execute(r);
        executor.execute(r);
        try {
            boolean _ = latch.await(2, TimeUnit.SECONDS);
            System.out.println("All services up and running!");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        executor.shutdown();
    }

}
