package com.mastering.concurrency.patterns.synchronizers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Phaser;

public class PhaserExample {
    private static Phaser PHASER;

    private static String WAIT = " ждёт на станции ";
    private static String ENTER = " вошел в вагон";
    private static String EXIT = " вышел из вагона ";
    private static String SPACE = "    ";
    private static String OPEN = "    ... открытие дверей ...";
    private static String CLOSE = "    ... закрытие дверей ...";

    public static class Passenger implements Runnable {
        private final int id;
        private final int departure;
        private final int destination;

        public Passenger(int id, int departure, int destination) {
            this.id = id;
            this.departure = departure;
            this.destination = destination;
            System.out.println(this + WAIT + departure);
        }

        @Override
        public void run() {
            try {
                System.out.println(SPACE + this + ENTER);
                while (PHASER.getPhase() < destination) {
                    PHASER.arriveAndAwaitAdvance();
                }
                Thread.sleep(500);
                System.out.println(SPACE + this + EXIT);
                PHASER.arriveAndDeregister();
            } catch (InterruptedException _) {
            }
        }

        @Override
        public String toString() {
            return "Пассажир " + id + " {" + departure + " -> " + destination + '}';
        }
    }

    static void main() {
        PHASER = new Phaser(1);

        List<Passenger> passengers = new ArrayList<>();
        for (int i = 1; i < 5; i++) {
            if ((int) (Math.random() * 2) > 0) {
                passengers.add(new Passenger(10 + i, i, i + 1));
            }

            if ((int) (Math.random() * 2) > 0) {
                Passenger p = new Passenger(20 + i, i, 5);
                passengers.add(p);
            }
        }

        for (int i = 0; i < 7; i++) {
            switch (i) {
                case 0:
                    System.out.println("Метро вышло из тупика");
                    PHASER.arrive();
                    break;
                case 6:
                    System.out.println("Метро ушло в тупик");
                    PHASER.arriveAndDeregister();
                    break;
                default:
                    int currentStation = PHASER.getPhase();
                    System.out.println("Станция " + currentStation);
                    for (Passenger pass : passengers)
                        if (pass.departure == currentStation) {
                            PHASER.register();
                            new Thread(pass).start();
                        }
                    System.out.println(OPEN);
                    PHASER.arriveAndAwaitAdvance();
                    System.out.println(CLOSE);
            }
        }
    }
}
