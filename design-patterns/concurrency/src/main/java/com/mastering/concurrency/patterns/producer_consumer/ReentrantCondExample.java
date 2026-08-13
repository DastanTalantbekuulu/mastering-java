package com.mastering.concurrency.patterns.producer_consumer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantCondExample {
    private static final String[] GOODS = {"Milk", "Kefir", "Ryazhenka", "Coffee", "Tea"};
    private final List<String> goods = new ArrayList<>();

    private final Store store;
    private final SimpleDateFormat sdf;


    ReentrantCondExample() {
        store = new Store();
        sdf = new SimpleDateFormat("HH:mm:ss  ");
        Thread producer = new Thread(new Producer());
        Thread consumer = new Thread(new Consumer());
        producer.start();
        consumer.start();

        while (producer.isAlive() || consumer.isAlive()) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("\nDone");
        System.exit(0);

    }

    void printMessage(final String msg) {
        if (msg != null) {
            String text = sdf.format(new Date()) + msg;
            System.out.println(text);
        } else
            System.out.println("\tItems in stock: " + goods.size());
    }

    class Store {
        private final ReentrantLock lock;
        private final Condition cond;

        Store() {
            lock = new ReentrantLock();
            cond = lock.newCondition();
        }

        public void get() {
            lock.lock();
            try {
                while (goods.isEmpty())
                    cond.await();

                printMessage("Implementation : " + goods.getFirst());
                goods.removeFirst();
                printMessage(null);
                cond.signalAll();
            } catch (InterruptedException _) {
            } finally {
                lock.unlock();
            }
        }

        public void put(final String good) {
            lock.lock();
            try {
                while (goods.size() >= 3)
                    cond.await();
                goods.add(good);

                printMessage("Delivery : " + good);
                printMessage(null);
                cond.signalAll();
            } catch (InterruptedException _) {
            } finally {
                lock.unlock();
            }
        }
    }

    class Producer implements Runnable {
        public void run() {
            for (String good : GOODS) {
                store.put(good);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException _) {
                }
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            for (int i = 0; i < GOODS.length; i++) {
                try {
                    Thread.sleep(8000);
                } catch (InterruptedException _) {
                }
                store.get();
            }
        }
    }

    static void main() {
        new ReentrantCondExample();
    }
}
