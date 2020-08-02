package com.multithreading;

import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

/**
 * An example for Reentrant lock
 */
public class ReentrantLockThread {
    static Logger log = Logger.getLogger(ReentrantLockThread.class.getName());

    public static void main(String[] args) throws InterruptedException {
        PriceContainer priceContainer = new PriceContainer();
        Thread thread1 = new Thread(new PriceDisplayer(priceContainer));
        Thread thread2 = new Thread(new PriceDisplayer(priceContainer));
        Thread thread3 = new Thread(new PriceDisplayer(priceContainer));
        Thread thread4 = new Thread(new PriceDisplayer(priceContainer));
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread1.join();
        thread2.join();
        thread3.join();
        thread4.join();
        log.debug(" Pricing Done");
    }

    private static class PriceDisplayer implements Runnable {
        private Random random = new Random();
        private PriceContainer priceContainer;

        public PriceDisplayer(PriceContainer priceContainer) {
            this.priceContainer = priceContainer;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // If thread 0 is executing ..other thread will go to else loop

                if (priceContainer.getReentrantLock().tryLock()) {
                    updatePrice();
                    log.debug("Price for apple share :" + priceContainer.getAppleShare() + "   " +Thread.currentThread().getName());
                    log.debug("Price for MS share :" + priceContainer.getMicrosoftShare());
                    log.debug("Price for google share :" + priceContainer.getGoogleShare());
                } else {
                    log.debug("Didn't get lock :  "+ Thread.currentThread().getName());
                }
            }
        }

        private void updatePrice() {
            try {
                priceContainer.getReentrantLock().lock();
                priceContainer.setAppleShare(random.nextInt(100));
                priceContainer.setGoogleShare(random.nextInt(200));
                priceContainer.setMicrosoftShare(random.nextInt(500));
            } finally {
                priceContainer.getReentrantLock().unlock();
            }
        }
    }

    private static class PriceContainer {
        private ReentrantLock reentrantLock = new ReentrantLock(true);
        private double appleShare;
        private double microsoftShare;
        private double googleShare;

        public ReentrantLock getReentrantLock() {
            return reentrantLock;
        }

        public void setReentrantLock(ReentrantLock reentrantLock) {
            this.reentrantLock = reentrantLock;
        }


        public double getAppleShare() {
            return appleShare;
        }

        public void setAppleShare(double appleShare) {
            this.appleShare = appleShare;
        }

        public double getMicrosoftShare() {
            return microsoftShare;
        }

        public void setMicrosoftShare(double microsoftShare) {
            this.microsoftShare = microsoftShare;
        }

        public double getGoogleShare() {
            return googleShare;
        }

        public void setGoogleShare(double googleShare) {
            this.googleShare = googleShare;
        }


    }
}

