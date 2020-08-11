package com.advanced;

import java.util.concurrent.RecursiveTask;

public class RecursiveCalculator extends RecursiveTask<Integer> {
    int arr[] ;
    int low;
    int high;
    public RecursiveCalculator (int arr[],int low,int high){
        this.arr = arr;
        this.low = low;
        this.high = high;
    }

    @Override
    protected Integer compute() {
        if((high -low) < ForkJoinParallelCalculation.threshold){
            //    System.out.println(" Going for sequential ......");
            return getWebData(arr);
        }
        else{
            //   System.out.println(" Going for parallel ......");
            int mid = (low + high) /2;
            RecursiveCalculator task1 = new RecursiveCalculator(arr,low,mid);
            RecursiveCalculator task2 = new RecursiveCalculator(arr,mid+1,high);
            invokeAll(task1,task2);
            return Math.max(task1.join(),task2.join());

        }
    }
    public Integer getWebData(int arr[]){
        //System.out.println(" Low :" + low + " high : "+ high );
        int max = arr[0];
        for(int i=0;i < arr.length ;i++){
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
}
