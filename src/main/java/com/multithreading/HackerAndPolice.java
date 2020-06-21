package com.multithreading;

import org.apache.log4j.Logger;

import java.util.Random;

/**
 * Program includes 2 Hacker and 1 Police
 * Password will be int between 0 to 9999
 * Descending Hacker will run a loop from 9999 to 0
 * Ascending Hacker will run a loop from 0 to 9999
 * Police will be waiting for 10 secs by running a loop from 0 to 10 with sleep = 1 sec
 * Passwaord Vault will be having sleep for 5 ms
 */
public class HackerAndPolice {
    static Logger log = Logger.getLogger(HackerAndPolice.class.getName());
    private static int MAX_PASSWORD = 9999;
    public static void main(String[] args) {
        Random random = new Random();
        Integer password = random.nextInt(MAX_PASSWORD);
        log.debug(" ***** Password is :" +password);
        Vault vault = new Vault(password);
        new Thread(new AscendingHacker(vault)).start();
        new Thread(new DescendingHacker(vault)).start();
        new Thread(new Police()).start();
    }
 private static class Vault{
        private int password;
        public Vault(int password){
            this.password = password;
        }
        public boolean isPasswordBroken(int password) throws InterruptedException {
            Thread.sleep(5);
            return this.password == password;
        }
 }

    private static class AscendingHacker implements Runnable {
        private Vault vault;
        public AscendingHacker(Vault vault) {
            this.vault = vault;
        }

        @Override
        public void run() {
            for (int i = 0; i < MAX_PASSWORD; i++) {
                try {
                    log.debug(" Ascending Hacker trying ....");
                    if (vault.isPasswordBroken(i)) {
                        log.debug("Password Broken by Ascending Hacker");
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static class DescendingHacker implements Runnable {
        private Vault vault;
        public DescendingHacker(Vault vault) {
            this.vault = vault;
        }

        @Override
        public void run() {
            for (int i = MAX_PASSWORD; i > 0; i--) {
                try {
                    log.debug(" Descending Hacker trying ....");
                    if (vault.isPasswordBroken(i)) {
                        log.debug("Password Broken by Descending Hacker");
                        System.exit(0);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private static class Police implements Runnable {

        @Override
        public void run() {
            for (int i =10 ; i > 0 ;i--){
                log.debug("Time left to get caught by Police in secs: "+i );
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            log.debug(" Game over ");
            System.exit(0);
        }
    }
}
