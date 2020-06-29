package com.multithreading;

import org.apache.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Thread join makes sure that the One thread is completed before 2nd starts
 */
public class ThreadJoin {
    static Logger log = Logger.getLogger(HackerAndPolice.class.getName());

    public static void main(String[] args) throws InterruptedException {
        List<Long> longs = Arrays.asList(2L,50000L,5637L,8795L,3654L,5637L,8795L,3654L);
        List<ThreadFactorial> threadpool = new ArrayList<>();
        for (Long aLong : longs) {
            threadpool.add(new ThreadFactorial(aLong));
        }
        int i =0;
        for(Thread thread : threadpool){
            thread.setName("Thread Number "+i);
            thread.start();
            i++;
        }
        for(Thread thread : threadpool){
            thread.join();
        }
        for(int j =0 ;j <longs.size() ; j++) {
            ThreadFactorial threadFactorial = threadpool.get(j);
            if (threadFactorial.isFinished()) {
                log.debug(" Finished calculating value for : " + longs.get(j) + " is : "+ threadFactorial.getFactorial());
            } else {
                log.debug(" Still calculating value for : " + longs.get(j));
            }
        }
    }
    public static class ThreadFactorial extends Thread{
        private long number;
        private BigInteger factorial = BigInteger.ZERO;
        private boolean isFinished = false;
        public ThreadFactorial(long number){
         this.number = number;
        }
        public BigInteger factorial(long number){
            BigInteger temp = BigInteger.ONE;
            for (long i =number; i> 1; i--){
                temp = temp.multiply(BigInteger.valueOf(i));
            }
            return temp;
        }
        public boolean isFinished(){
            return isFinished;
        }

        @Override
        public void run() {
            this.factorial = factorial(number);
            isFinished =true;
        }
        public BigInteger getFactorial() {
            return factorial;
        }
    }
}
