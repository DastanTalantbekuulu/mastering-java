package com.mastering.concurrency.projects.coffee_shop;

import org.junit.jupiter.api.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * Simulation is the main class used to run the simulation.  You may
 * add any fields (static or instance) or any methods you wish.
 */
public class Simulation {
    // List to track simulation events during simulation
    private static final List<SimulationEvent> events = Collections.synchronizedList(new ArrayList<>());


    /**
     * Used by other classes in the simulation to log events
     *
     * @param event
     */
    public static void logEvent(SimulationEvent event) {
        events.add(event);
        System.out.println(event);
    }

    /**
     * Function responsible for performing the simulation. Returns a List of
     * SimulationEvent objects, constructed any way you see fit. This List will
     * be validated by a call to Validate.validateSimulation. This method is
     * called from Simulation.main(). We should be able to test your code by
     * only calling runSimulation.
     * <p>
     * Parameters:
     *
     * @param numCustomers    the number of customers wanting to enter the coffee shop
     * @param numCooks        the number of cooks in the simulation
     * @param numTables       the number of tables in the coffe shop (i.e. coffee shop capacity)
     * @param machineCapacity the capacity of all machines in the coffee shop
     * @param randomOrders    a flag say whether or not to give each customer a random order
     */
    public static List<SimulationEvent> runSimulation(
            int numCustomers, int numCooks,
            int numTables,
            int machineCapacity,
            boolean randomOrders
    ) {

        logEvent(SimulationEvent.startSimulation(numCustomers,
                numCooks,
                numTables,
                machineCapacity));


        // Set things up you might need
        Semaphore freeTable = new Semaphore(numTables);
        LinkedBlockingQueue<Order> orderQueue = new LinkedBlockingQueue<>();

        // Start up machines
        Machine grill = new Machine("Grill", FoodType.burger, machineCapacity);
        Machine fryer = new Machine("Fryer", FoodType.fries, machineCapacity);
        Machine coffeeMaker2000 = new Machine("CoffeeMaker2000", FoodType.coffee, machineCapacity);

        // Let cooks in
        Thread[] cooks = new Thread[numCooks];
        for (int i = 0; i < numCooks; i++) {
            cooks[i] = new Thread(new Cook("cook" + i, fryer, grill, coffeeMaker2000));
            cooks[i].start();
        }

        // Build the customers.
        Thread[] customers = new Thread[numCustomers];
        LinkedList<Food> order;
        if (!randomOrders) {
            order = new LinkedList<Food>();
            order.add(FoodType.burger);
            order.add(FoodType.fries);
            order.add(FoodType.fries);
            order.add(FoodType.coffee);
            for (int i = 0; i < customers.length; i++) {
                customers[i] = new Thread(
                        new Customer("Customer " + (i + 1), order)
                );
            }
        } else {
            for (int i = 0; i < customers.length; i++) {
                Random rnd = new Random(27);
                int burgerCount = rnd.nextInt(3);
                int friesCount = rnd.nextInt(3);
                int coffeeCount = rnd.nextInt(3);
                order = new LinkedList<Food>();
                for (int b = 0; b < burgerCount; b++) {
                    order.add(FoodType.burger);
                }
                for (int f = 0; f < friesCount; f++) {
                    order.add(FoodType.fries);
                }
                for (int c = 0; c < coffeeCount; c++) {
                    order.add(FoodType.coffee);
                }
                customers[i] = new Thread(
                        new Customer("Customer " + (i + 1), order)
                );
            }
        }

        for (Thread customer : customers) {
            customer.start();

        }


        try {

            for (Thread cook : cooks) {
                cook.interrupt();
            }

            logEvent(SimulationEvent.machineEnding(grill));
            logEvent(SimulationEvent.machineEnding(fryer));
            logEvent(SimulationEvent.machineEnding(coffeeMaker2000));

            for (Thread cook : cooks) {
                cook.join();
            }

        } catch (InterruptedException e) {
            System.out.println("Simulation thread interrupted.");
        }


        logEvent(SimulationEvent.endSimulation());

        return events;
    }

    static void main() {
        int numCustomers = 100;
        int numCooks = 50;
        int numTables = 5;
        int machineCapacity = 4;
        boolean random = false;

        System.out.println("Did it work? " +
                Validate.validateSimulation(
                        runSimulation(numCustomers, numCooks, numTables, machineCapacity, random)
                )
        );
    }

}



