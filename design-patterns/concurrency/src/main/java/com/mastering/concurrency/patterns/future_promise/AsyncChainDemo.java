package com.mastering.concurrency.patterns.future_promise;

import java.util.concurrent.CompletableFuture;

public class AsyncChainDemo {
    static void main() {

        CompletableFuture.supplyAsync(() -> {
                    sleep(1000);
                    System.out.println("Thread: " + Thread.currentThread().getName());
                    return "Java";
                })
                .thenApply(data -> {
                    System.out.println("Add 'Guru'...");
                    return data + " Guru";
                })
                .thenAccept(finalResult -> {
                    System.out.println("ФИНАЛ: " + finalResult);
                });

        System.out.println("Main: I'm not waiting, I'm moving on!");
        sleep(2000);
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException _) {
        }
    }
}