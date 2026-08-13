package com.mastering.concurrency.patterns.guarded_suspension.philosopher;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ThreadLocalRandom;

@RequiredArgsConstructor
public class Philosopher implements Runnable {
    private final int id;
    private final Table table;
    private final int leftFork;
    private final int rightFork;
    private final int cycles;

    @Override
    public void run() {
        try {
            for (int i = 0; i < cycles; i++) {
                think();
                eat();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void think() throws InterruptedException {
        sleepRandom();
    }

    private void eat() throws InterruptedException {
        table.grabForks(id, leftFork, rightFork);
        sleepRandom();
        table.releaseForks(id, leftFork, rightFork);
    }

    private void sleepRandom() throws InterruptedException {
        int duration = ThreadLocalRandom.current().nextInt(200, 800);
        Thread.sleep(duration);
    }
}