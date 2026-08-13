package com.mastering.concurrency.patterns.thread_safe.shared_state;

import com.mastering.concurrency.patterns.GuardedBy;
import com.mastering.concurrency.patterns.ThreadSafe;

import java.util.concurrent.Executors;

/**
 * Pattern: Protected Shared State
 * <p>
 * Example: A simple Counter example.
 */
@ThreadSafe
public class VisitCounter {

    @GuardedBy("this")
    private int value;

    public synchronized int actualValue() {
        return value;
    }

    public synchronized void increase() {
        value++;
    }

    public synchronized void decrease() {
        value--;
    }

    static void main() {
        var counter = new VisitCounter();
        var threadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 50; i++) {
            System.out.println("value " + counter.actualValue() + " i " + i);
            threadPool.execute(counter::increase);
        }
        threadPool.shutdown();
        System.out.println(counter.actualValue());
    }
}
