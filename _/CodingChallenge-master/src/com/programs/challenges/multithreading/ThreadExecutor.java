package com.programs.challenges.multithreading;

import java.lang.Thread.State;

public class ThreadExecutor {

	public static void main(String[] args) {
		Thread frostbyte = new Thread(new FrostByte());
		Ignite ignite = new Ignite();
		frostbyte.setName("FrostByte");
		ignite.setName("Ignite");
		
		frostbyte.start();
		ignite.start();
	
		/*
		try {
			frostbyte.join();
			ignite.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		*/
	}

}
