package com.mastering.concurrency.patterns.synchronizers;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {
    private static final int COUNT_CONTROL_PLACES = 5;
    private static final int COUNT_RIDERS = 7;
    private static boolean[] CONTROL_PLACES = null;

    private static Semaphore SEMAPHORE = null;

    @RequiredArgsConstructor
    public static class Rider implements Runnable {
        private final int ruderNum;

        @Override
        public void run() {
            System.out.printf("Всадник %d подошел к зоне контроля\n", ruderNum);
            try {
                SEMAPHORE.acquire();
                System.out.printf("\tвсадник %d проверяет наличие свободного контроллера\n", ruderNum);
                int controlNum = -1;
                synchronized (CONTROL_PLACES) {
                    for (int i = 0; i < COUNT_CONTROL_PLACES; i++)
                        if (CONTROL_PLACES[i]) {
                            CONTROL_PLACES[i] = false;
                            controlNum = i;
                            System.out.printf("\t\tвсадник %d подошел к контроллеру %d.\n", ruderNum, i);
                            break;
                        }
                }
                Thread.sleep((int) (Math.random() * 10 + 1) * 1000);
                synchronized (CONTROL_PLACES) {
                    CONTROL_PLACES[controlNum] = true;
                }
                SEMAPHORE.release();
                System.out.printf("Всадник %d завершил проверку\n", ruderNum);
            } catch (InterruptedException _) {
            }
        }
    }

    static void main() throws InterruptedException {
        CONTROL_PLACES = new boolean[COUNT_CONTROL_PLACES];
        for (int i = 0; i < COUNT_CONTROL_PLACES; i++) {
            CONTROL_PLACES[i] = true;
        }
        SEMAPHORE = new Semaphore(CONTROL_PLACES.length, true);
        for (int i = 1; i <= COUNT_RIDERS; i++) {
            new Thread(new Rider(i)).start();
            Thread.sleep(400);
        }
    }
}
