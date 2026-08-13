package com.mastering.concurrency.patterns.parallel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Deque;

@Getter
@RequiredArgsConstructor
public class ParallelMaximizerWorker extends Thread {

    private final Deque<Integer> deque;
    private int partialMax = Integer.MIN_VALUE;

    public void run() {
        while (true) {
            int number;
            synchronized (deque) {
                if (deque.isEmpty())
                    return;
                number = deque.remove();
            }

            // TODO: IMPLEMENT CODE HERE
            if (number >= partialMax) {
                partialMax = number;
            }
        }
    }

}
