package com.advanced;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ExecutorServicesExample {
    public static void main(String[] args) {

        ThreadFactory customThreadfactory = new CustomThreadFactoryBuilder()
                .setNamePrefix("DemoPool-Thread").setDaemon(false)
                .setPriority(Thread.MAX_PRIORITY).build();

        ExecutorService fixedExecutorService = Executors.newFixedThreadPool(3,
                customThreadfactory);
        ExecutorService cachedExecutorService = Executors.newCachedThreadPool(customThreadfactory);
        // Create three simple tasks with 1000 ms sleep time
        SimpleTask simpleTask1 = new SimpleTask(1000);
        SimpleTask simpleTask2 = new SimpleTask(1000);
        SimpleTask simpleTask3 = new SimpleTask(1000);
        SimpleTask simpleTask4 = new SimpleTask(1000);
        SimpleTask simpleTask5 = new SimpleTask(1000);

        // Execute three simple tasks with 1000 ms sleep time
        //fixedExecutorService.submit(simpleTask1);
       /* List<SimpleTask> simpleTasks = new ArrayList<>();
        simpleTasks.add(simpleTask1);
        simpleTasks.add(simpleTask4);
        try {
            final List<Future<Object>> futures = fixedExecutorService.invokeAll(simpleTasks);
            futures.forEach(future -> {
                try {
                    System.out.println("Current thread " + Thread.currentThread().getName());
                    Object o = future.get();
                    System.out.println(o);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                    System.out.println(" Exception Caught here");
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally{
            fixedExecutorService.shutdown();
        }*/
        try {
          //  fixedExecutorService.execute(simpleTask2);
          //  fixedExecutorService.execute(simpleTask3);
            fixedExecutorService.submit(simpleTask2);
            fixedExecutorService.submit(simpleTask3);
        }
        catch( Exception e){
            System.out.println(" Exception for execute :");
        }
        finally{
            fixedExecutorService.shutdown();
        }
       // cachedExecutorService.execute(simpleTask4);
       // cachedExecutorService.execute(simpleTask5);

    }
    private static class CustomThreadFactoryBuilder {
        private String namePrefix = null;
        private boolean daemon = false;
        private int priority = Thread.NORM_PRIORITY;

        public CustomThreadFactoryBuilder setNamePrefix(String namePrefix) {
            if (namePrefix == null) {
                throw new NullPointerException();
            }
            this.namePrefix = namePrefix;
            return this;
        }

        public CustomThreadFactoryBuilder setDaemon(boolean daemon) {
            this.daemon = daemon;
            return this;
        }

        public CustomThreadFactoryBuilder setPriority(int priority) {
            if (priority > Thread.MAX_PRIORITY) {
                throw new IllegalArgumentException(String.format(
                        "Thread priority (%s) must be <= %s", priority,
                        Thread.MAX_PRIORITY));
            }
            this.priority = priority;
            return this;
        }

        public ThreadFactory build() {
            return build(this);
        }

        private static ThreadFactory build(CustomThreadFactoryBuilder builder) {
            final String namePrefix = builder.namePrefix;
            final Boolean daemon = builder.daemon;
            final Integer priority = builder.priority;

            final AtomicLong count = new AtomicLong(0);

            return new ThreadFactory() {
                @Override
                public Thread newThread(Runnable runnable) {
                    Thread thread = new Thread(runnable);
                    if (namePrefix != null) {
                        thread.setName(namePrefix + "-" + count.getAndIncrement());
                    }
                    if (daemon != null) {
                        thread.setDaemon(daemon);
                    }
                    if (priority != null) {
                        thread.setPriority(priority);
                    }
                    return thread;
                }
            };
        }

    }
    private static class SimpleTask implements Runnable {

        private long sleepTime;
        private AtomicInteger atomicInteger = new AtomicInteger(0);

        public SimpleTask(long sleepTime) {
            super();
            this.sleepTime = sleepTime;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    System.out.println("Simple task is running on " + Thread.currentThread().getName() + " with priority " + Thread.currentThread().getPriority());
                    Thread.sleep(sleepTime);
                    atomicInteger.getAndIncrement();
                    if( atomicInteger.get() == 2){
                        throw new ArithmeticException(" Hello Exception");
                    }
                    System.out.println(" Crosses exception block with value :"+ atomicInteger.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
