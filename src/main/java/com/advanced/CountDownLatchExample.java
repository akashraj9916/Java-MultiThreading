package com.advanced;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatchExample {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService executorServices = Executors.newFixedThreadPool(10);
        IntStream.range(0,4).forEach(i -> executorServices.execute(new SimpleTask(countDownLatch)));
        countDownLatch.await(3, TimeUnit.SECONDS);
        long end = System.currentTimeMillis() - start;
        System.out.println(" Finished all work in time ..."+ end);
        executorServices.shutdown();
    }

    private static class SimpleTask implements Runnable {
        private CountDownLatch countDownLatch;

        public SimpleTask(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public void run() {
            System.out.println(" Thread started with name " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
                countDownLatch.countDown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
