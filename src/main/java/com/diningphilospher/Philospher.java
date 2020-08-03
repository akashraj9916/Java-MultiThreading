package com.diningphilospher;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Philospher implements Runnable{
    private int id;
    private ChopStick leftChopstick;
    private ChopStick rightChopstick;
    private Random random;
    private AtomicInteger eatingCount = new AtomicInteger(0);

    private volatile boolean  isFull= false;

    public Philospher(int id, ChopStick leftChopstick,ChopStick rightChopstick){
        this.id =id;
        this.leftChopstick = leftChopstick;
        this.rightChopstick = rightChopstick;
        this.random =new Random();
    }

    @Override
    public void run() {
        while ( !isFull) {

            try {
                thinking();
                if(leftChopstick.pick(this,State.LEFT)){
                    if(rightChopstick.pick(this,State.RIGHT)){
                        eating();
                        rightChopstick.putdown(this,State.RIGHT);
                    }
                    leftChopstick.putdown(this,State.LEFT);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void thinking() throws InterruptedException {
        System.out.println(" Philospher is thinking: " + this);
        Thread.sleep(random.nextInt(1000));
    }
    public void eating() throws InterruptedException {
        System.out.println(" Philospher is eating: " + this);
        this.eatingCount.incrementAndGet();
        Thread.sleep(random.nextInt(1000));
    }
    public int eatingCounter(){
        return this.eatingCount.get();
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFull(boolean full) {
        isFull = full;
    }
    @Override
    public String toString() {
        return
                "id=" + id +
                ", leftChopstick=" + leftChopstick +
                ", rightChopstick=" + rightChopstick;

    }
}
