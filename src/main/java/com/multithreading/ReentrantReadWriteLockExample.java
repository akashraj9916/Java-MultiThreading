package com.multithreading;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {
    static Logger log = Logger.getLogger(ReentrantReadWriteLockExample.class.getName());

    public static void main(String[] args) throws InterruptedException {
        PriceCalculator priceCalculator = new PriceCalculator();
        Thread writeThread = new Thread(() -> {
            while (true) {
               for(int i=0 ;i<1000;i++) {
                   priceCalculator.addValue("key" + i, 100 + i);
                   priceCalculator.removeValue("key" + 1);
               }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        List<Thread> readList = new ArrayList<>();
        for(int j =0 ;j<7;j++) {
            Thread readThread = new Thread(() -> {
                for (int i = 0; i < 100000; i++) {
                    priceCalculator.getAverage();
                }
            });
            readThread.setDaemon(true);
            readList.add(readThread);
        }
        long startTime = System.currentTimeMillis();
        writeThread.setDaemon(true);
        writeThread.start();
        readList.forEach(Thread::start);
        readList.forEach(thread -> {

            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        });

        long endTime = System.currentTimeMillis() - startTime;
        log.debug("Total time taken :" + endTime + " and average :" + priceCalculator.getAverage());
    }

    private static class PriceCalculator {
        private TreeMap<String, Integer> treeMap = new TreeMap<>();
        private ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        private ReentrantLock reentrantLock = new ReentrantLock();
        private Lock readLock = reentrantReadWriteLock.readLock();
        private Lock writeLock = reentrantReadWriteLock.writeLock();
        public Long getAverage() {
            long average = 1L;
            readLock.lock();
            try {


                for (Integer val : treeMap.values()) {
                    average = average + val;
                }
            } finally {
                readLock.unlock();
            }
            return average;
        }

        public void addValue(String key, Integer val) {
            writeLock.lock();
            try {


               // log.debug(" Adding ..." + key);
                treeMap.put(key, val);
            } finally {
                writeLock.unlock();
            }
        }

        public void removeValue(String key) {
            writeLock.lock();
            try {


              //  log.debug(" Removing ..." + key);
                if (treeMap.containsKey(key)) {
                    treeMap.remove(key);
                }
            } finally {
                writeLock.unlock();
            }
        }
    }
}
