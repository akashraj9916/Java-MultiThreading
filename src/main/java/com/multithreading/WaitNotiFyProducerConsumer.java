package com.multithreading;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Scalable Producer Consumer using wait And notify
 */
public class WaitNotiFyProducerConsumer {
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
                while (true) {
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
                while (true) {
                    inbox.get();
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
    private static class Inbox {
        private AtomicInteger i = new AtomicInteger(0);
        private Queue<Integer> queue = new ArrayDeque<>();
        public void put() throws InterruptedException {
            synchronized (queue) {
                if (queue.isEmpty()) {
                    // Thread.sleep(1000);
                    queue.offer(i.incrementAndGet());
                    System.out.println(" Producing: " + Thread.currentThread().getName() + " item : " + i);
                    queue.notifyAll();
                }
                else{
                    queue.wait();
                }
            }
        }
        public void get() throws InterruptedException {
            synchronized (queue) {
                if (!queue.isEmpty()) {
                    System.out.println(" Consuming: " + Thread.currentThread().getName() + " item : " + queue.poll());
                    queue.notify();
                }
                else{
                    queue.wait();
                }
            }
        }
    }
}
