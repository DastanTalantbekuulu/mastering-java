package com.mastering.concurrency.patterns.guarded_suspension.logistic;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class Truck implements Runnable {
    private final int id;
    private final LoadingBay loadingBay;

    @Override
    public void run() {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(500, 2000));
            int dockIndex = loadingBay.dockTruck(id);
            unloadCargo();
            loadingBay.undockTruck(id, dockIndex);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void unloadCargo() throws InterruptedException {
        int duration = ThreadLocalRandom.current().nextInt(1000, 3000);
        Thread.sleep(duration);
    }
}