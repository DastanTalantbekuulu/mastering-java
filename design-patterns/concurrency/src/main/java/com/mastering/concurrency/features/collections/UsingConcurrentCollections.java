package com.mastering.concurrency.features.collections;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Concurrent collections are an alternative to the Synchronized Collections.
 * <p>
 * Supports concurrent access from n threads and performs better than
 * Synchronized collections.
 *
 */
public class UsingConcurrentCollections {

    /**
     * Map for Concurrent access. Sacrifices some aspects of synchronization to
     * achieve better performance.
     * <p>
     * Locks it's values with Lock Striping.
     * <p>
     * Lock Striping divides the protected region through several locks.
     * <p>
     * - Don't throw ConcurrentModificationException
     * <p>
     * - size() and isEmpty() can be incorrect. Don't rely on them.
     * <p>
     * - Supports atomic operations, don't need client side locking.
     * <p>
     * - Readers can access concurrently, and iterator have weak consistency.
     * <p>
     * - Just a few Writers can modify it.
     */
    public static void usingConcurrentHashMap() {
        System.out.println("=== ConcurrentHashMap ===");
        ExecutorService executor = Executors.newCachedThreadPool();
        Random random = new Random();
        ConcurrentHashMap<UUID, Integer> valuesPerUuid = new ConcurrentHashMap<>();
        // atomic operations
        valuesPerUuid.putIfAbsent(UUID.randomUUID(), random.nextInt(100));

        // simulating concurrent access - no duplicates should be inserted
        for (int i = 0; i < 100; i++) {
            if (i % 6 == 0) {
                // write
                executor.execute(() -> {
                    UUID uuid = UUID.randomUUID();
                    Integer value = random.nextInt(10);
                    System.out.println("Added " + uuid + " - " + value);
                    valuesPerUuid.putIfAbsent(uuid, value);
                });
            } else {
                // read
                executor.execute(() -> System.out.println("Printed " + valuesPerUuid.values()));
            }
        }

        // Finishing
        executor.shutdown();
        try {
            boolean _ = executor.awaitTermination(2000, TimeUnit.SECONDS);
            // space for other examples
            Thread.sleep(2000);
            System.out.println("\n\n\n\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Replacement for synchronized list. Based on the immutable object concept.
     * <p>
     * Use when reading is far more common than writing.
     * <p>
     * Creates a new copy every time that the list is modified, only synchronizing
     * briefly to ensure array content visibility.
     * <p>
     * iterator returns a snapshot of the current state of the collection.
     * <p>
     * Supports atomic operations.
     *
     *
     *
     *
     */
    public static void usingCopyOnWriteArrayList() {
        System.out.println("=== CopyOnWriteArrayList ===");
        ExecutorService executor = Executors.newCachedThreadPool();
        Random random = new Random();
        // No ConcurrentModificationException
        CopyOnWriteArrayList<Integer> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

        for (int i = 0; i < 100; i++) {
            if (i % 8 == 0) {
                // write
                executor.execute(() -> {
                    var value = random.nextInt(10);
                    System.err.println("Added " + value);
                    copyOnWriteArrayList.add(value);
                });
            } else {
                // read
                executor.execute(() -> {
                    var builder = new StringBuilder();
                    for (var value : copyOnWriteArrayList) {
                        builder.append(value).append(" ");
                    }
                    System.out.println("Reading " + builder);
                });
            }
        }

        // Finishing
        executor.shutdown();
        try {
            boolean _ = executor.awaitTermination(2000, TimeUnit.SECONDS);
            // space for other examples
            Thread.sleep(2000);
            System.out.println("\n\n\n\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Concurrent Queue interface.
     * <p>
     * Implementations: LinkedBlockingQueue, ArrayBlockingQueue,
     * PriorityBlockingQueue, SynchronizedQueue.
     * <p>
     * Used for the Producer-Consumer pattern.
     * <p>
     * Blocking methods: put/take; Timed blocking methods: offer, poll;
     * <p>
     * Can be bounded or unbounded.
     *
     */
    public static void usingBlockingQueue() {
        System.out.println("=== BlockingQueue ===");

        // Bounded UUID queue
        LinkedBlockingQueue<UUID> uuidQueue = new LinkedBlockingQueue<>(10);

        System.out.println("Queue will execute for 10s");

        // Multiple consumers
        Runnable runConsumer = () -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    var uuid = uuidQueue.take();
                    System.out.println("Consumed: " + uuid + " by " + Thread.currentThread().getName());

                } catch (InterruptedException e) {
                    // interrupted pattern
                    // InterruptedException makes isInterrupted returns false
                    Thread.currentThread().interrupt();
                    System.err.println("Consumer Finished");
                }
            }
        };
        Thread consumer1 = new Thread(runConsumer);
        consumer1.start();
        Thread consumer2 = new Thread(runConsumer);
        consumer2.start();

        // Producer Thread
        Runnable runProducer = () -> {
            try {
                while (!Thread.currentThread().isInterrupted()) {
                    Random r = new Random();
                    // Delay producer
                    Thread.sleep(r.nextInt(1000));
                    UUID randomUUID = UUID.randomUUID();
                    System.out.println("Produced: " + randomUUID + " by " + Thread.currentThread().getName());
                    uuidQueue.put(randomUUID);
                }
            } catch (InterruptedException e) {
                // interrupted pattern
                System.err.println("Producer Finished");
            }
        };

        // Multiple producers - Examples using simple threads this time.
        Thread producer1 = new Thread(runProducer);
        producer1.start();
        Thread producer2 = new Thread(runProducer);
        producer2.start();
        Thread producer3 = new Thread(runProducer);
        producer3.start();

        try {
            // Queue will run for 10secs
            Thread.sleep(10000);
            producer1.interrupt();
            producer2.interrupt();
            producer3.interrupt();
            consumer1.interrupt();
            consumer2.interrupt();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void main() {
        usingConcurrentHashMap();
        usingCopyOnWriteArrayList();
        usingBlockingQueue();
    }

}
