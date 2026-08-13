package com.mastering.concurrency.patterns.synchronizers;

import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {
    private static CyclicBarrier FerryBarrier;
    private static final int FerryBoat_size = 3;

    public static class FerryBoat implements Runnable {
        @Override
        public void run() {
            try {
                System.out.println("\nLoading Ferry Boat...");
                Thread.sleep(500);
                System.out.println("The ferry transported the cars\n");
            } catch (InterruptedException _) {
            }
        }
    }

    public static class Car implements Runnable {
        private final int carNumber;

        public Car(int carNumber) {
            this.carNumber = carNumber;
        }

        @Override
        public void run() {
            try {
                System.out.printf("A car drove up to the crossing %d\n", carNumber);
                FerryBarrier.await();
                System.out.printf("Car %d continued moving\n", carNumber);
            } catch (Exception _) {
            }
        }
    }

    static void main() throws InterruptedException {
        FerryBarrier = new CyclicBarrier(FerryBoat_size, new FerryBoat());

        for (int i = 1; i < 10; i++) {
            new Thread(new Car(i)).start();
            Thread.sleep(400);
        }
    }
}
