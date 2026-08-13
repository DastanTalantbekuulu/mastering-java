package com.mastering.concurrency.patterns.producer_consumer;

import java.util.LinkedList;
import java.util.Queue;

class MessageQueue {
    private final Queue<String> queue = new LinkedList<>();
    private final int capacity;

    public MessageQueue(int capacity) {
        this.capacity = capacity;
    }

    public synchronized void produce(String msg) throws InterruptedException {
        while (queue.size() == capacity) {
            wait();
        }
        queue.add(msg);
        notifyAll();
    }

    public synchronized String consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        String msg = queue.poll();
        notifyAll();
        return msg;
    }
}
