package com.mastering.concurrency.patterns.two_phase_termination;

class BackgroundMonitor implements Runnable {
    @Override
    public void run() {
        try {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println("Monitor checking system...");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Monitor was interrupted during sleep!");
        } finally {
            System.out.println("Cleaning up and stopping.");
        }
    }
}

// Thread t = new Thread(new BackgroundMonitor());
// t.start();
// ...  ...
// t.interrupt();