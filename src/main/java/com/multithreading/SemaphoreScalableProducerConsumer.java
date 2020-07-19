package com.multithreading;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Scalable Producer and Customer problem (Multiple Producer and Consumer)
 */
public class SemaphoreScalableProducerConsumer {
    public static void main(String[] args) {
        Inbox inbox = new Inbox();
        Producer producer = new Producer(inbox);
        Consumer consumer = new Consumer(inbox);
        Thread thread = new Thread(producer);
        Thread thread1 = new Thread(consumer);
        Thread thread2 = new Thread(new Producer(inbox));
        Thread thread3 = new Thread(new Producer(inbox));
        Thread thread4 = new Thread(new Consumer(inbox));
        Thread thread5 = new Thread(new Consumer(inbox));

        thread1.start();
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();
    }
    private static class Producer implements Runnable {
        Inbox inbox;
        public Producer(Inbox inbox) {
            this.inbox = inbox;
        }
        @Override
        public void run() {
            try {
               while(true){
                    inbox.put();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    private static class Consumer implements Runnable {
        Inbox inbox;
        public Consumer(Inbox inbox) {
            this.inbox = inbox;
        }
        @Override
        public void run() {
            try {
                while(true){
                    inbox.get();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }
    private static class Inbox {
        private Semaphore full = new Semaphore(1);
        private Semaphore empty = new Semaphore(0);
        private ReentrantLock reentrantLock = new ReentrantLock();
        private AtomicInteger i = new AtomicInteger(0);
        private Queue<Integer> queue = new ArrayDeque<>();
        public void put() throws InterruptedException {
            try {
                full.acquire();
                reentrantLock.lock();
                // Thread.sleep(1000);
                queue.offer(i.incrementAndGet());
                System.out.println(" Producing: " +Thread.currentThread().getName()+ " item : "+ i);
            } finally {
                empty.release();
                reentrantLock.unlock();
            }
        }

        public void get() throws InterruptedException {
            try {
                empty.acquire();
                reentrantLock.lock();
                // Thread.sleep(1000);
                System.out.println(" Consuming: " +Thread.currentThread().getName()+ " item : "+  queue.poll());
            } finally {
                reentrantLock.unlock();
                full.release();
            }
        }
    }
}

