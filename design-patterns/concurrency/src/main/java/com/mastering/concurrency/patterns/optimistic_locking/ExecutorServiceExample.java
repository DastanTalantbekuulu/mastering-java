package com.mastering.concurrency.patterns.optimistic_locking;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorServiceExample {

     static void main() {
        SequenceGenerator sg = new SequenceGenerator();

         try (ExecutorService executor = Executors.newFixedThreadPool(5)) {

             List<Future<SequenceResult>> futures = new ArrayList<>();

             for (int i = 0; i < 10; i++) {
                 SequenceTask task = new SequenceTask(i, 3, sg);
                 futures.add(executor.submit(task));
             }

             System.out.println("\nAll tasks submitted. Waiting for results...\n");

             for (Future<SequenceResult> future : futures) {
                 try {
                     SequenceResult result = future.get();

                     printResult(result);

                 } catch (InterruptedException e) {
                     Thread.currentThread().interrupt();
                     System.err.println("Main thread interrupted");
                 } catch (ExecutionException e) {
                     System.err.println("Computation error: " + e.getCause());
                 }
             }
             executor.shutdown();
         }
         System.out.println("\n\nThe threads have completed their work");
    }

    private static void printResult(SequenceResult result) {
        StringBuilder sb = new StringBuilder();
        sb.append("Flow sequence ")
                .append(result.workerId())
                .append(" : [");

        List<BigInteger> numbers = result.sequence();
        for (int i = 0; i < numbers.size(); i++) {
            if (i > 0) sb.append(", ");

            String numStr = numbers.get(i).toString();
            StringBuilder nb = new StringBuilder(numStr);

            while (nb.length() < 9) {
                nb.insert(0, " ");
            }
            sb.append(nb);
        }
        sb.append("]");
        System.out.println(sb);
    }
}
