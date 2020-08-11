package com.advanced;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinParallelCalculation {
    static int threshold = 0;
    public static void main(String[] args) {
        int arr[] = new int[300000000];
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Random random = new Random();
        RecursiveCalculator calculator = new RecursiveCalculator(arr,0,arr.length);
        for(int i=0;i < 300000000 ;i++){
            arr[i] = random.nextInt(1000);
        }

        threshold = arr.length /Runtime.getRuntime().availableProcessors();
        System.out.println(" Threshold :" + threshold);
        long seqStart = System.currentTimeMillis();
        System.out.println(" Max value from sequential : " + getWebData(arr));
        long seqEnd = System.currentTimeMillis() - seqStart;
        System.out.println(" Total time taken by sequential :" + seqEnd);
        long parStart = System.currentTimeMillis();
        System.out.println(" Max value from parallel : " + pool.invoke(calculator));
        long parEnd = System.currentTimeMillis() - parStart;
        System.out.println(" Total time taken by parallel :" + parEnd);

    }

    public static Integer getWebData(int arr[]){
        int max = arr[0];
        for(int i=0;i < arr.length ;i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
}
