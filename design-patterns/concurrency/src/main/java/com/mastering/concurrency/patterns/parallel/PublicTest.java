package com.mastering.concurrency.patterns.parallel;

import org.junit.jupiter.api.Test;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PublicTest {

    private final int threadCount = 10;
    private final ParallelMaximizer maximizer = new ParallelMaximizer(threadCount);

    @Test
    public void compareMax() {
        int size = 10000;
        Deque<Integer> list = new LinkedList<>();
        Random rand = new Random();
        int serialMax = Integer.MIN_VALUE;
        int parallelMax;
        for (int i = 0; i < size; i++) {
            int next = rand.nextInt();
            list.add(next);
            serialMax = Math.max(serialMax, next);
        }

        try {
            parallelMax = maximizer.max(list);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(serialMax, parallelMax);
    }
}
