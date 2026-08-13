package com.mastering.concurrency.patterns.read_write_lock;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedCache {
    private String data = "Default";
    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public String readData() {
        rwLock.readLock().lock();
        try {
            return data;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public void writeData(String newData) {
        rwLock.writeLock().lock();
        try {
            data = newData;
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}