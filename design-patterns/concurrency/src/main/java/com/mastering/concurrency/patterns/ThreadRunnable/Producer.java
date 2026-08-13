package com.mastering.concurrency.patterns.ThreadRunnable;

public class Producer implements Runnable
{
    Store store;
    
    Producer(Store store) {
       this.store=store; 
    }
    @Override
    public void run() {
    	for (int i = 1; i < 6; i++) {
    		store.put();
        }
    }
}
