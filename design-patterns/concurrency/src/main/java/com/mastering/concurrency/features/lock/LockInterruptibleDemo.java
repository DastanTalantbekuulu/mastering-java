package com.mastering.concurrency.features.lock;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LockInterruptibleDemo {

    private static final Lock lock = new ReentrantLock();
    private static Thread victimThread;

    static void main() throws InterruptedException {
        Thread thread1 = new Thread(new ActiveWorker(), "Owner-Thread");
        victimThread = new Thread(new InterruptedWorker(), "Victim-Thread");
        Thread thread3 = new Thread(new PatientWorker(), "Patient-Thread");

        thread1.start();
        TimeUnit.MILLISECONDS.sleep(100);

        victimThread.start();
        TimeUnit.MILLISECONDS.sleep(100);

        thread3.start();

        thread1.join();
        victimThread.join();
        thread3.join();

        log("The demonstration is over");
    }

    private static void log(String msg) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        System.out.printf("%s [%-15s] %s%n", time, Thread.currentThread().getName(), msg);
    }

    static class ActiveWorker implements Runnable {
        @Override
        public void run() {
            try {
                lock.lock();
                log("I've taken over the block. I'm working....");
                TimeUnit.SECONDS.sleep(2);

                log("I decided to interrupt the Victim-Thread while I was holding the lock");
                victimThread.interrupt();

                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException _) {
                Thread.currentThread().interrupt();
            } finally {
                log("Releasing the lock");
                lock.unlock();
            }
        }
    }

    static class InterruptedWorker implements Runnable {
        @Override
        public void run() {
            try {
                log("I'm trying to take over the block.(lockInterruptible)...");
                lock.lockInterruptibly();

                try {
                    log("YAY! I got blocked (this shouldn't happen in this example)");
                } finally {
                    lock.unlock();
                }
            } catch (InterruptedException _) {
                log("ERROR: I was interrupted while waiting to be locked! Logging out");
            }
        }
    }

    static class PatientWorker implements Runnable {
        @Override
        public void run() {
            try {
                log("I'm getting in line...");
                lock.lock();
                try {
                    log("Finally got blocked!");
                } finally {
                    lock.unlock();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}