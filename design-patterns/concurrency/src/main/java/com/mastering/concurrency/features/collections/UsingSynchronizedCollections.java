package com.mastering.concurrency.features.collections;

import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Synchronized collections synchronizes every public method to encapsulate
 * their state.
 * <p>
 * They're thread-safe, but if you use compound actions like size+add, they'rent
 * anymore, because these operations needs to be atomic.
 * <p>
 * It's required to use client side locking for compound actions.
 * <p>
 * Synchronized collections doesn't support concurrent iteration+modification.
 * They'll throw ConcurrentModificationException
 *
 */
public class UsingSynchronizedCollections {

    /**
     * Use client-side locking to guard compound actions; contains and add are
     * synchronized, but this doesn't help when you need to use both in a compounded
     * manner.
     * <p>
     * It's just an easy example to explain the problem, sets are better for this
     * than a vector/vector.
     */
    public static void insertIfAbsent(Vector<Long> vector, Long value) {
        synchronized (vector) {
            boolean contains = vector.contains(value);
            if (!contains) {
                vector.add(value);
                System.out.println("Value added: " + value);
            }
        }
    }

    /**
     * You can have duplicates. Try to run multiple times and see the diff in
     * results
     */
    public static void insertIfAbsentUnsafe(Vector<Long> list, Long value) {
        var contains = list.contains(value);
        if (!contains) {
            list.add(value);
            System.out.println("Value added: " + value);
        }
    }

    static void main() throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        // Synchronized - Vector
        Vector<Long> vector = new Vector<>();

        Runnable insertIfAbsent = () -> {
            long millis = System.currentTimeMillis() / 1000;
            insertIfAbsent(vector, millis);
        };
        for (int i = 0; i < 10001; i++) {
            executor.execute(insertIfAbsent);
        }
        executor.shutdown();
        boolean _ = executor.awaitTermination(4000, TimeUnit.SECONDS);

        // Using the wrappers for not sync collections
        // List<String> synchronizedList = Collections.synchronizedList(abcList);
        // Collections.synchronizedMap(m)
        // Collections.synchronizedXXX
    }
}
