package com.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class BlockingQueueProducerConsumerExample {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        AtomicInteger atomicInteger = new AtomicInteger(0);
        CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
        List<Thread> threads = new ArrayList<>();
        IntStream.range(0,3).forEach(i -> {
            Thread t = new Thread(new Producer(queue,atomicInteger,list));
            threads.add(t);
            t.start();
        });
        IntStream.range(0,3).forEach(i -> {
            Thread t =  new Thread(new Consumer(queue,list));
            threads.add(t);
            t.start();
        });
        Thread.sleep(1000);
       // list.forEach(l -> System.out.println(l));


    }
    private static class Producer implements Runnable {
        private BlockingQueue<Integer> queue;
        private AtomicInteger atomicInteger;
        private CopyOnWriteArrayList<String> list;
        public Producer(BlockingQueue<Integer> queue,AtomicInteger atomicInteger,CopyOnWriteArrayList<String> list ){
           this.queue = queue;
           this.atomicInteger = atomicInteger;
           this.list = list;
        }
        @Override
        public void run() {
            for(int i=0 ;i <2;i++){
                    try {
                            int k = atomicInteger.incrementAndGet();
                            list.add("Producer thread" + Thread.currentThread().getName());
                            queue.put(k);
                            System.out.println(" Producing : " + k + " From Thread: " + Thread.currentThread().getName() + " Current size: - " + queue.size());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

            }
        }
    }
    private static class Consumer implements Runnable {
        private BlockingQueue<Integer> queue;
        private CopyOnWriteArrayList<String> list;
        public Consumer(BlockingQueue<Integer> queue,CopyOnWriteArrayList<String> list){
            this.queue = queue;
            this.list =list;
        }
        @Override
        public void run() {
            for(int i=0 ;i <2;i++){
                try {

                        list.add("Consumer thread" + Thread.currentThread().getName());
                        System.out.println(" Consuming : " + queue.take() + " From Thread: " + Thread.currentThread().getName() + " Current size: - " + queue.size());
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
