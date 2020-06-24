package com.multithreading;

import java.math.BigInteger;

/**
 * Daemon should be set before thread start
 */
public class ThreadDaemon {
    public static void main(String[] args) {
        Thread thread = new Thread(new ThreadDemo());
        thread.setDaemon(true);
        thread.start();
    }
    private static class ThreadDemo implements Runnable{

        @Override
        public void run() {
            BigInteger base = BigInteger.valueOf(200);
            BigInteger power = BigInteger.valueOf(1000);
            BigInteger value = base ;
            for(int i=1 ;i<=power.intValue() ;i++ ){
                if(Thread.currentThread().isInterrupted()){
                    System.out.println(" Exiting value");
                    return;
                }
                value = value.multiply(base);
            }
        }
    }
}
