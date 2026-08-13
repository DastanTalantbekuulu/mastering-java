package com.mastering.concurrency.patterns.thread_specific_storage;

public class ThreadLocalDemo {
    private static final ThreadLocal<String> transactionId = ThreadLocal.withInitial(() -> "UNKNOWN");

    static void main() {
        Runnable task = () -> {
            transactionId.set("TX-" + Thread.currentThread().threadId());
            processRequest();
        };

        new Thread(task).start();
        new Thread(task).start();
    }

    static void processRequest() {
        System.out.println("Processing with ID: " + transactionId.get());
    }
}