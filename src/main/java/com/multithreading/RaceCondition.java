package com.multithreading;

import org.apache.log4j.Logger;

/**
 * Race condition
 */
public class RaceCondition {
    static Logger log = Logger.getLogger(RaceCondition.class.getName());
    public static void main(String[] args) throws InterruptedException {
    Inventory inventory = new Inventory();
    IncrementorThread incrementorThread = new IncrementorThread(inventory);
    DecrementorThread decrementorThread = new DecrementorThread(inventory);
    long startTime = System.currentTimeMillis();
    incrementorThread.start();
    decrementorThread.start();
    // join is used to make sure the below lines get printed after the both thread is completed
    incrementorThread.join();
    decrementorThread.join();

   log.debug("Time take to get " +inventory.getItem()+" items is :"+String.valueOf(System.currentTimeMillis() -startTime) );
    }
    private static class IncrementorThread extends  Thread{
        private Inventory inventory;
        public IncrementorThread(Inventory inventory){
            this.inventory =inventory;
        }
        @Override
        public void run() {
            for(int i=0 ; i < 10000 ;i++){
                try {
                    Thread.sleep(1);
                    inventory.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private static class DecrementorThread extends  Thread{
        private Inventory inventory;
        public DecrementorThread(Inventory inventory){
            this.inventory =inventory;
        }
        @Override
        public void run() {
            for(int i=0 ; i < 10000 ;i++){
                try {
                    Thread.sleep(1);
                    inventory.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    private static class Inventory {
        Object lock1 = new Object();
        private int item =0 ;
        public void increment(){
            synchronized (lock1) {
                item++;
            }
        }
        public void decrement(){
            synchronized (lock1) {
                item--;
            }
        }
        public int getItem(){
            synchronized (lock1) {
                return item;
            }
        }
    }
}
