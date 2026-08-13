package com.mastering.concurrency.features.parallel_stream;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * Warning
 * <p>
 * Parallel Streams uses the ForkJoin Pool, so be aware of using it in Java
 * EE/Jakarta EE environments!
 * <p>
 * The Stream API allows, with methods like parallel() or parallelStream(), the
 * execution of stream's operations in parallel.
 * <p>
 * It enables each element to be processed in parallel, having a thread for each
 * one of them, depending on the number of cores available. Like the Fork/Join
 * Framework, it has an overhead, so the speed of execution can get much better
 * in some cases, getting worse in some others.
 * <p>
 * Streams are composed of a Source, several intermediate operations and a
 * terminal operation. Streams are executed only when a terminal operation is
 * executed, so they're lazy too. The intermediate operations respect the order
 * that you used, they're sequential. The work of each intermediate operation is
 * parallel.
 * <p>
 * CPU intensive tasks benefits from this feature.
 *
 *
 */
public class UsingParallelStreams {

    static void main() {
        // Creating Parallel Streams from existing collection
        try (Stream<Object> _ = new ArrayList<>().parallelStream()) {
        }

        // Making Stream Parallel
        List<Boolean> _ = IntStream.rangeClosed(0, 30_000) // source
                .parallel().mapToObj(BigInteger::valueOf).map(UsingParallelStreams::isPrime) // Intermediate operations
                .toList();// Terminal Operations

        // Each operation run in parallel, out of order
        IntStream.rangeClosed(0, 20) // source
                .parallel().mapToObj(Integer::toString) // Intermediate operation
                .forEach(System.out::print); // Terminal operation

        System.out.println("\n");

        // Runs sequentially, in order.
        IntStream.rangeClosed(0, 20)
                .mapToObj(Integer::toString)
                .forEach(System.out::print);

        System.out.println("\n");

        dummyPerformanceCheck();
    }

    private static void dummyPerformanceCheck() {
        // Sequential Stream
        long start1 = System.currentTimeMillis();
        List<Boolean> _ = IntStream.rangeClosed(0, 50_000)
                .mapToObj(BigInteger::valueOf)
                .map(UsingParallelStreams::isPrime)
                .toList();
        long end1 = System.currentTimeMillis();
        long time1 = (end1 - start1) / 1000;
        System.out.println("Sequential: " + time1);

        // Parallel Stream
        long start2 = System.currentTimeMillis();
        List<Boolean> _ = IntStream.rangeClosed(0, 50_000)
                .parallel()
                .mapToObj(BigInteger::valueOf)
                .map(UsingParallelStreams::isPrime)
                .toList();
        long end2 = System.currentTimeMillis();
        long time2 = (end2 - start2) / 1000;
        System.out.println("Parallel: " + time2);
    }

    // thanks to lines on
    // https://stackoverflow.com/questions/15862271/java-compute-intensive-task
    public static boolean isPrime(BigInteger n) {
        var counter = BigInteger.ONE.add(BigInteger.ONE);
        var isPrime = true;
        while (counter.compareTo(n) < 0) {
            if (n.remainder(counter).compareTo(BigInteger.ZERO) == 0) {
                isPrime = false;
                break;
            }
            counter = counter.add(BigInteger.ONE);
        }
        return isPrime;
    }
}
