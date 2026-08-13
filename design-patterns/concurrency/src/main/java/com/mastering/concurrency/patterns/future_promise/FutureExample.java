package com.mastering.concurrency.patterns.future_promise;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureExample {
    static void main() throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        System.out.println("1. Send task...");

        Future<String> future = executor.submit(() -> {
            Thread.sleep(2000);
            return "Hello from Future!";
        });

        System.out.println("2. Do something else while the task is being completed...");

        String result = future.get();

        System.out.println("3. The result has been received: " + result);

        executor.shutdown();
    }
}