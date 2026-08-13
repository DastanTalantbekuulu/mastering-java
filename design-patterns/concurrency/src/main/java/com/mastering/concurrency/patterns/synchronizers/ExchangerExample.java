package com.mastering.concurrency.patterns.synchronizers;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.Exchanger;

public class ExchangerExample {

    private static Exchanger<Letter> EXCHANGER;

    static String msg1 = "Почтальон %s получил письма : %s, %s\n";
    static String msg2 = "Почтальон %s выехал из %s в %s\n";
    static String msg3 = "Почтальон %s приехал в пункт Д\n";
    static String msg4 = "Почтальон %s получил письма для %s\n";
    static String msg5 = "Почтальон %s привез в %s : %s, %s\n";

    @RequiredArgsConstructor
    public static class Postman implements Runnable {
        private final String id;
        private final String departure;
        private final String destination;
        private final Letter[] letters;

        @Override
        public void run() {
            try {
                System.out.printf(msg1, id, letters[0], letters[1]);
                System.out.printf(msg2, id, departure, destination);
                Thread.sleep(((long) (Math.random() * 10) + 4) * 1000);
                System.out.printf(msg3, id);
                letters[1] = EXCHANGER.exchange(letters[1]);
                System.out.printf(msg4, id, destination);
                Thread.sleep(((long) (Math.random() * 10) + 4) * 1000);
                System.out.printf(msg5, id, destination, letters[0], letters[1]);
            } catch (InterruptedException _) {
            }
        }
    }

    @RequiredArgsConstructor
    public static class Letter {
        private final String address;

        public String toString() {
            return address;
        }
    }

    static void main() throws InterruptedException {
        EXCHANGER = new Exchanger<>();
        Letter[] posts1 = new Letter[2];
        Letter[] posts2 = new Letter[2];
        posts1[0] = new Letter("п.В - Петров");
        posts1[1] = new Letter("п.Г - Киса Воробьянинов");
        posts2[0] = new Letter("п.Г - Остап Бендер");
        posts2[1] = new Letter("п.В - Иванов");
        new Thread(new Postman("a", "А", "В", posts1)).start();
        Thread.sleep(100);
        new Thread(new Postman("б", "Б", "Г", posts2)).start();
    }

}
