package com.mastering.concurrency.features.lock;

import lombok.AllArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TryLockDemo {

    private static final Lock lock = new ReentrantLock();
    private static String sharedResource = "Empty";

    static void main() throws InterruptedException {
        Thread owner = new Thread(new ResourceOwner(), "Owner");
        Thread patient = new Thread(new ResourceRequester(7), "Patient");
        Thread impatient = new Thread(new ResourceRequester(2), "Impatient");

        owner.start();
        TimeUnit.MILLISECONDS.sleep(500);

        patient.start();
        impatient.start();

        owner.join();
        patient.join();
        impatient.join();

        System.out.println("\nFinal resource value: " + sharedResource);
    }

    private static void log(String msg) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.printf("%s [%-10s] %s%n", time, Thread.currentThread().getName(), msg);
    }

    static class ResourceOwner implements Runnable {
        @Override
        public void run() {
            lock.lock();
            try {
                log("Captured the resource for 5 seconds");
                sharedResource = "Owner was here";
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException _) {
                Thread.currentThread().interrupt();
            } finally {
                log("Freed up the resource");
                lock.unlock();
            }
        }
    }

    @AllArgsConstructor
    static class ResourceRequester implements Runnable {
        private int waitTimeSeconds;

        @Override
        public void run() {
            log("Trying to capture a resource (waiting max " + waitTimeSeconds + " seconds)...");
            try {
                if (lock.tryLock(waitTimeSeconds, TimeUnit.SECONDS)) {
                    try {
                        log("SUCCESS! Gained access to the resource");
                        log("I see the meaning: " + sharedResource);
                        sharedResource = Thread.currentThread().getName() + " updated this";
                        TimeUnit.SECONDS.sleep(1);
                    } finally {
                        lock.unlock();
                        log("Completed the job and released the resource");
                    }
                } else {
                    log("FAILURE! Timeout expired. I'm leaving");
                }
            } catch (InterruptedException _) {
                log("I was interrupted");
            }
        }
    }
}