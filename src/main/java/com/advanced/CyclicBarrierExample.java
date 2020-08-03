package com.advanced;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class CyclicBarrierExample {
    public static void main(String[] args) throws InterruptedException, BrokenBarrierException, TimeoutException {
        long start = System.currentTimeMillis();
        ExecutorService executorServices = Executors.newFixedThreadPool(10);
        Runnable action = new Runnable() {
            @Override
            public void run() {
                System.out.println(" All task finished ..");
            }
        };
        CyclicBarrier cyclicBarrier = new CyclicBarrier(5,action);
        try {
            doWork(executorServices,cyclicBarrier);
            long end = System.currentTimeMillis() - start;
            // resetting should not be used ..it automatically resets
            //cyclicBarrier.reset();

            doWork(executorServices,cyclicBarrier);
            System.out.println(" Finished all work in time ..." + end);
        }
        finally {
            executorServices.shutdown();
        }
    }

    private static void doWork(ExecutorService executorServices,CyclicBarrier cyclicBarrier) throws InterruptedException, BrokenBarrierException, TimeoutException {

        IntStream.range(0,4).forEach(i -> executorServices.execute(new SimpleTask(cyclicBarrier)));

    }

    private static class SimpleTask implements Runnable {
        private  CyclicBarrier cyclicBarrier;

        public SimpleTask( CyclicBarrier cyclicBarrier) {
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            System.out.println(" Thread started with name " + Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
                try {
                    cyclicBarrier.await(3, TimeUnit.SECONDS);
                    System.out.println(" Proceeding after blocked by barrier "+ Thread.currentThread().getName());
                } catch (BrokenBarrierException | TimeoutException e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
