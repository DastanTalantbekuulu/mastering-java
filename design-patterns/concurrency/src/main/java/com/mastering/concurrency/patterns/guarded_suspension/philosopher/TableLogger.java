package com.mastering.concurrency.patterns.guarded_suspension.philosopher;

public interface TableLogger {
    /**
     * Called whenever the table state changes
     * @param action Action name (e.g. "Wait 0")
     * @param forks Current snapshot of the forks
     * @param philosophStates A current snapshot of the state of philosophers
     */
    void log(String action, boolean[] forks, PhilosophyState[] states);
}