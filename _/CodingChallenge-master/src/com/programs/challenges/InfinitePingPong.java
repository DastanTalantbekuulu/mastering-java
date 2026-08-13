package com.programs.challenges;

public class InfinitePingPong extends Thread {

    private static final Object lock = new Object();

    private String toPrintOut;

    public InfinitePingPong(String s) {
        this.toPrintOut = s;
    }


    public void run() {
        while (true) {
            synchronized (lock) {
                System.out.println(this.toPrintOut + " -->" + this.getId());
                lock.notifyAll();

                try {
                    lock.wait();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        InfinitePingPong a = new InfinitePingPong("ping");
        InfinitePingPong b = new InfinitePingPong("pong");


        a.start();
        b.start();

        b.wait();

        try {
            a.join();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }
}
