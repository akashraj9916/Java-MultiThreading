package com.multithreading;

import java.util.concurrent.Semaphore;

/**
 *  Demo to show how Semaphore can block a thread
 */
public class SemaphoreLockDemo {
    public static void main(String[] args) throws InterruptedException {
        DiskDrive diskDrive = new DiskDrive();
        Thread thread = new Thread(new ReadDisk(diskDrive));
        Thread thread1 = new Thread(new WriteDisk(diskDrive));
        Thread thread2 = new Thread(new ReadDisk(diskDrive));
        Thread thread3 = new Thread(new ReadDisk(diskDrive));
       // Thread thread1 = new Thread(new WriteDisk(diskDrive));

        thread.start();
        thread2.start();
        thread1.start();
        thread3.start();
        thread3.join();
        thread2.join();
        thread1.join();
        thread.join();
        System.out.println(" Done ....");

    }
    private static class ReadDisk implements Runnable{
        private DiskDrive diskDrive;
        public ReadDisk(DiskDrive diskDrive){
            this.diskDrive = diskDrive;
        }
        @Override
        public void run() {
            try {
                diskDrive.read();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class WriteDisk implements Runnable{
        private DiskDrive diskDrive;
        public WriteDisk(DiskDrive diskDrive){
            this.diskDrive = diskDrive;
        }
        @Override
        public void run() {
            try {
                diskDrive.write();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    private static class DiskDrive{
        Semaphore semaphore = new Semaphore(1);
        Semaphore semaphore1 = new Semaphore(0);
        public void read() throws InterruptedException {
            semaphore.acquire();
            Thread.sleep(500);
            System.out.println(" I am reading " + Thread.currentThread().getName());

        }
        public void write() throws InterruptedException {
           // Thread.sleep(2000);
            Thread.sleep(2000);
            System.out.println(" I am writing " + Thread.currentThread().getName());

           semaphore.release(1);
        }
    }
}
