package com.mastering.concurrency.patterns.parallel;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@Slf4j
public class ParallelMaximizer {

    private final int numThreads;
    private final List<ParallelMaximizerWorker> workers;

    public ParallelMaximizer(int numThreads) {
        this.numThreads = numThreads;
        workers = new ArrayList<>(numThreads);
    }


    static void main() {
        int numThreads = 2000;
        int numElements = 10000000;

        ParallelMaximizer maximizer = new ParallelMaximizer(numThreads);
        Deque<Integer> list = new LinkedList<>();

        // TODO: change this implementation to test accordingly
        for (int i = 0; i < numElements; i++)
            list.add(i);

        try {
            System.out.println(maximizer.max(list));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    public int max(Deque<Integer> list) throws InterruptedException {
        int max = Integer.MIN_VALUE;

        System.out.println(workers.size());
        for (int i = 0; i < numThreads; i++) {
            workers.add(i, new ParallelMaximizerWorker(list));
            workers.get(i).start();
        }
        for (ParallelMaximizerWorker worker : workers) {
            worker.join();
        }
        // TODO: IMPLEMENT CODE HERE

        for (ParallelMaximizerWorker worker : workers) {
            int workerMax = worker.getPartialMax();
            if (workerMax >= max) {
                max = workerMax;
            }
        }

        return max;
    }

}
