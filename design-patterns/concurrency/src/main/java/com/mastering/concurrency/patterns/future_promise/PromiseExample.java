package com.mastering.concurrency.patterns.future_promise;

import java.util.concurrent.CompletableFuture;

public class PromiseExample {
    static void main() throws Exception {
        CompletableFuture<String> promise = new CompletableFuture<>();

        new Thread(() -> {
            try {
                System.out.println("Kitchen: Frying a cutlet...");
                Thread.sleep(2000);

                promise.complete("Hot burger");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();

        System.out.println("Client: Waiting for your order...");

        String result = promise.get();
        System.out.println("Client: Received " + result);
    }
}