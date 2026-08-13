package com.mastering.concurrency.patterns.thread_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WorkerDemo {
    static void main() {
        try (ExecutorService executor = Executors.newFixedThreadPool(2)) {

            for (int i = 0; i < 5; i++) {
                final int taskId = i;
                executor.submit(() -> System.out.println("Worker " + Thread.currentThread().getName() + " doing task " + taskId));
            }

            executor.shutdown();
        }
    }
}