package com.mastering.concurrency.patterns.optimistic_locking;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadLocalRandom;

public class SequenceTask implements Callable<SequenceResult> {
    private final int id;
    private final int count;
    private final SequenceGenerator sg;

    public SequenceTask(int id, int count, SequenceGenerator sg) {
        this.id = id;
        this.count = count;
        this.sg = sg;
    }

    @Override
    public SequenceResult call() throws Exception {
        List<BigInteger> sequence = new ArrayList<>();
        System.out.println("Task " + id + " started");

        for (int i = 0; i < count; i++) {
            sequence.add(sg.next());
            Thread.sleep(ThreadLocalRandom.current().nextLong(30, 100));
        }

        System.out.println("Task " + id + " completed");
        return new SequenceResult(id, sequence);
    }
}