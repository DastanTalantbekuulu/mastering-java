package com.programs.challenges.multithreading;

public class Ignite extends Thread {

	@Override
	public void run() {
		
		super.run();
		System.out.println("Running "+Thread.currentThread().getName());
		for(int i=0;i<10;i++){
			doProcess(i);
		}
	}
	
	public void doProcess(int i){
		System.out.println("Executing "+i+":"+Thread.currentThread().getName());
		if(i == 5){
			try {
				synchronized (Ignite.this) {
					Ignite.this.wait(1000);
				} 
				
			} catch (InterruptedException e) {
				System.err.println("Error occured"+e.getLocalizedMessage());
			}
		}
	}
}
