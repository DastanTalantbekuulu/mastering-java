package com.mastering.concurrency.patterns.balking;

class AutoSaver {
    private boolean isSaving = false;

    public void save() {
        synchronized (this) {
            if (isSaving) {
                System.out.println("Already saving! I give up (Balking).");
                return;
            }
            isSaving = true;
        }

        try {
            System.out.println("Saving to disk...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            synchronized (this) {
                isSaving = false;
            }
        }
    }
}