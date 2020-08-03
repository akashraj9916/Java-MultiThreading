package com.advanced;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
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
      //  SimpleTask simpleTask2 = new SimpleTask(1000);
       // SimpleTask simpleTask3 = new SimpleTask(1000);
        SimpleTask simpleTask4 = new SimpleTask(1000);
        SimpleTask simpleTask5 = new SimpleTask(1000);

        // Execute three simple tasks with 1000 ms sleep time
        fixedExecutorService.execute(simpleTask1);
      //  fixedExecutorService.execute(simpleTask2);
      //  fixedExecutorService.execute(simpleTask3);
        cachedExecutorService.execute(simpleTask4);
        cachedExecutorService.execute(simpleTask5);

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
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
