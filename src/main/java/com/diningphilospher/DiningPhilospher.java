package com.diningphilospher;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DiningPhilospher {

    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(Constants.NUMBER_OF_PHILOSPHER);
        final Philospher[] philosphers = new Philospher[Constants.NUMBER_OF_PHILOSPHER];;
        try {
            ChopStick[] chopSticks = new ChopStick[Constants.NUMBER_OF_CHOPSTICK];
            IntStream.range(0, Constants.NUMBER_OF_CHOPSTICK).forEach(i -> {
                chopSticks[i] = new ChopStick(i);
            });
            IntStream.range(0, Constants.NUMBER_OF_PHILOSPHER).forEach(i -> {
                philosphers[i] = new Philospher(i, chopSticks[i], chopSticks[(i + 1) % Constants.NUMBER_OF_CHOPSTICK]);
                executorService.execute(philosphers[i]);
            });
            Thread.sleep(Constants.SIMULATION);
            for(Philospher p : philosphers){
                p.setFull(true);
            }
        }
        finally {
            executorService.shutdown();
            executorService.awaitTermination(2, TimeUnit.SECONDS);
            for(Philospher p : philosphers){
                System.out.println(" Philospher id: " + p.getId() +" has eaten: "+ p.eatingCounter());
            }
            long endTime = System.currentTimeMillis() -startTime;
            System.out.println(" Total time taken :" +endTime);
        }

    }
}
