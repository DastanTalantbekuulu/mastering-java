package com.mastering.concurrency.patterns.divideconquer;

import lombok.RequiredArgsConstructor;

import java.io.Serial;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

/*
 * Pattern: Parallel Divide and Conquer
 *
 * Example: Parallel Sum
 *
 * This is a simple example for educational purpose only, run on your
 * machine and check which one is better!
 */
@RequiredArgsConstructor
public class ParallelSum extends RecursiveTask<BigInteger> {

    @Serial
    private static final long serialVersionUID = 1L;
    private final static int THRESHOLD = 10_000; // Choosing a number to split the computation

    private final List<BigInteger> nums;

    @Override
    protected BigInteger compute() {
        var size = nums.size();
        if (size < THRESHOLD) {
            return sequentialSum(nums);
        } else {
            var x = new ParallelSum(nums.subList(0, size / 2));
            var y = new ParallelSum(nums.subList(size / 2, size));
            x.fork();
            y.fork();
            var xResult = x.join();
            var yResult = y.join();
            return yResult.add(xResult);
        }
    }

    /*
     * Just showing how to use the pattern and some really dummy benchmark. Don't
     * take it seriously.
     */
    static void main() throws InterruptedException {
        List<BigInteger> nums = LongStream.range(0, 10_000_000L)
                .mapToObj(BigInteger::valueOf)
                .toList();

        // Run one then comment and run another
        Runnable parallel = () -> {
            BigInteger result;
            try (ForkJoinPool commonPool = ForkJoinPool.commonPool()) {
                result = commonPool.invoke(new ParallelSum(nums));
            }
            System.out.println("Parallel Result is: " + result);
        };

        Runnable sequential = () -> {
            BigInteger acc = sequentialSum(nums);
            System.out.println("Sequential Result is: " + acc);
        };

        sequential.run();
        parallel.run();

        Thread.sleep(2000);

        System.out.println("#### After some JIT \n\n");

        dummyBenchmark(sequential);
        dummyBenchmark(parallel);

        Thread.sleep(2000);

        System.out.println("#### After more JIT \n\n");

        dummyBenchmark(sequential);
        dummyBenchmark(parallel);
    }

    private static BigInteger sequentialSum(List<BigInteger> nums) {
        BigInteger acc = BigInteger.ZERO;
        for (BigInteger value : nums) {
            acc = acc.add(value);
        }
        return acc;
    }

    static void getHot(Runnable runnable) {
        runnable.run();
    }

    static void dummyBenchmark(Runnable runnable) {
        long before = System.currentTimeMillis();
        runnable.run();
        long after = System.currentTimeMillis();
        System.out.println("Executed in: " + (after - before));
        System.out.println("######\n");
    }

}
