package com.mastering.concurrency.patterns.guarded_suspension.philosopher;

import java.util.Arrays;

import static com.mastering.concurrency.patterns.guarded_suspension.philosopher.PhilosophyState.EATING;
import static com.mastering.concurrency.patterns.guarded_suspension.philosopher.PhilosophyState.HUNGRY;
import static com.mastering.concurrency.patterns.guarded_suspension.philosopher.PhilosophyState.THINKING;

public class Table {
    private final int count;
    private final boolean[] forks;
    private final PhilosophyState[] states;
    private final TableLogger logger;

    public Table(int count, TableLogger logger) {
        this.count = count;
        this.logger = logger;
        forks = new boolean[count];
        states = new PhilosophyState[count];
        Arrays.fill(states, THINKING);
    }

    public synchronized void grabForks(int id, int leftFork, int rightFork) throws InterruptedException {
        states[id] = HUNGRY;
        notifyLogger("Wait " + id);

        while (forks[leftFork] || forks[rightFork]) {
            wait();
        }

        forks[leftFork] = true;
        forks[rightFork] = true;
        states[id] = EATING;

        notifyLogger("Take " + id);
    }

    public synchronized void releaseForks(int id, int leftFork, int rightFork) {
        forks[leftFork] = false;
        forks[rightFork] = false;
        states[id] = THINKING;

        notifyLogger("Free " + id);

        notifyAll();
    }

    private void notifyLogger(String action) {
        logger.log(action, forks, states);
    }
}