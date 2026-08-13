package com.mastering.concurrency.patterns.guarded_suspension.logistic;


import java.util.Arrays;

public class LoadingBay {
    public static final int EMPTY = -1;

    private final int capacity;
    private final int[] docks;
    private int waitingTrucks = 0;
    private final BayLogger logger;

    public LoadingBay(int capacity, BayLogger logger) {
        this.capacity = capacity;
        this.logger = logger;
        docks = new int[capacity];

        Arrays.fill(docks, EMPTY);

        logger.log("Bay Opened", docks, 0);
    }

    public synchronized int dockTruck(int truckId) throws InterruptedException {
        waitingTrucks++;
        logger.log("Arrived " + truckId, docks, waitingTrucks);

        while (getFreeDockIndex() == EMPTY) {
            wait();
        }

        waitingTrucks--;
        int freeIndex = getFreeDockIndex();
        docks[freeIndex] = truckId;

        logger.log("Docking " + truckId, docks, waitingTrucks);
        return freeIndex;
    }

    public synchronized void undockTruck(int truckId, int dockIndex) {
        docks[dockIndex] = EMPTY;
        logger.log("Leaving " + truckId, docks, waitingTrucks);
        notifyAll();
    }

    private int getFreeDockIndex() {
        for (int i = 0; i < capacity; i++) {
            if (docks[i] == EMPTY) return i;
        }
        return EMPTY;
    }
}