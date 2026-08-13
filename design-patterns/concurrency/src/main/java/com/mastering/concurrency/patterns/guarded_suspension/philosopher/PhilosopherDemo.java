package com.mastering.concurrency.patterns.guarded_suspension.philosopher;

import java.util.ArrayList;
import java.util.List;

public class PhilosopherDemo {

    private static final String RESET = "\u001B[0m";

    private static final String BG_GREEN = "\u001B[42m\u001B[30m";
    private static final String RED_BOLD = "\u001B[1;31m";
    private static final String CYAN_DIM = "\u001B[2;36m";

    private static final String FORK_FREE = "\u001B[32m|\u001B[0m";
    private static final String FORK_TAKEN = "\u001B[31mX\u001B[0m";

    static void main() throws InterruptedException {
        int count = 5;
        int cycles = 5;

        printLegend();

        printTableHeader(count);

        Table table = createTable(count);

        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            int rightFork = (i + 1) % count;
            Philosopher philosopherLogic = new Philosopher(i, table, i, rightFork, cycles);
            threads.add(new Thread(philosopherLogic));
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        System.out.println("\n" + "-".repeat(10 + (count * 13)));
        System.out.println("Simulation finished");
    }

    private static Table createTable(int count) {
        TableLogger logger = (action, forks, states) -> {
            StringBuilder sb = new StringBuilder();

            sb.append(String.format("%-10s", action));

            for (int i = 0; i < count; i++) {
                String forkSymbol = forks[i] ? FORK_TAKEN : FORK_FREE;

                String philSymbol;
                switch (states[i]) {
                    case EATING -> philSymbol = BG_GREEN + "!EAT!" + RESET;
                    case HUNGRY -> philSymbol = RED_BOLD + " !!! " + RESET;
                    default -> philSymbol = CYAN_DIM + "  .  " + RESET;
                }

                sb.append(String.format(" %s |  %s  |", forkSymbol, philSymbol));
            }
            System.out.println(sb);
        };

        return new Table(count, logger);
    }

    private static void printTableHeader(int count) {
        System.out.printf("%-10s", "ACTION");
        for (int i = 0; i < count; i++) {
            System.out.printf(" %d |   P-%d   |", i, i);
        }
        System.out.println("\n" + "-".repeat(10 + (count * 14)));
    }

    private static void printLegend() {
        System.out.println("LEGEND PHILOSOPHERS:");
        System.out.println(BG_GREEN + " !EAT! " + RESET + " = Eating (Holding resources)");
        System.out.println(RED_BOLD + "  !!!  " + RESET + " = Waiting (Suspended in wait())");
        System.out.println(CYAN_DIM + "   .   " + RESET + " = Thinking (Idle)");
        System.out.println("LEGEND FORKS:");
        System.out.println(FORK_FREE + " = Free, " + FORK_TAKEN + " = Taken");
        System.out.println();
    }
}