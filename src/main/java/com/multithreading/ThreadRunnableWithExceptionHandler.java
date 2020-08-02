package com.multithreading;


import org.apache.log4j.Logger;

public class ThreadRunnableWithExceptionHandler {
    static Logger log = Logger.getLogger(ThreadRunnableWithExceptionHandler.class.getName());
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            log.debug("In new Thread with Thread name :" + Thread.currentThread().getName());
            log.debug("Priority of Thread is :" + Thread.currentThread().getPriority());
            throw new RuntimeException(" Runtime Exception Intentional");
        }
        );
        Thread thread1 = new Thread(() -> {
            log.debug("TTTTT name :" + Thread.currentThread().getName());
            log.debug("Priority of Thread is :" + Thread.currentThread().getPriority());
           // throw new RuntimeException(" Runtime Exception Intentional");
        }
        );
        thread.setName("New Thread 2");
        thread.setPriority(Thread.MAX_PRIORITY);
        // need to set this before thread starts
        thread.setUncaughtExceptionHandler((t, e) -> log.debug(" Exception Caught here : " + "for Thread " + t.getName() + " Exception is :" + e.getMessage() ));
        log.debug(" Before Thread starts "+ Thread.currentThread().getName());
        thread1.start();
        thread.start();
        log.debug(" After Thread starts " + Thread.currentThread().getName());

    }
}
