package com.mastering.concurrency.patterns.guarded_suspension.logistic;

public interface BayLogger {
    void log(String event, int[] dockStates, int waitingCount);
}