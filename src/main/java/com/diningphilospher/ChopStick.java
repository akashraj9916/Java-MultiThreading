package com.diningphilospher;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class ChopStick {
    private int id;
    private ReentrantLock reentrantLock;
    public ChopStick(int id){
        this.id =id;
        this.reentrantLock = new ReentrantLock();
    }
    public boolean pick(Philospher philospher,State state) throws InterruptedException {
        if(reentrantLock.tryLock(10, TimeUnit.MILLISECONDS)){
            System.out.println(" Philospher "+ philospher.getId()+ " picked up" + state.toString() +" chopstick of id "+this);
            return true;
        }
        return false;
    }
    public void putdown(Philospher philospher,State state){
        reentrantLock.unlock();
        System.out.println(" Philospher "+ philospher.getId()+ " put down " + state.toString() +" chopstick of id "+this);
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "ChopStick{" +
                "id=" + id +
                '}';
    }

}
