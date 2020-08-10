package com.advanced;

import java.util.concurrent.RecursiveTask;

public class ForkJoinParallelCalculation {
    public static void main(String[] args) {

    }
    public class Calculator extends RecursiveTask<Integer> {
        int arr[] ;
        int low;
        int high;
        public Calculator (){

        }

        @Override
        protected Integer compute() {
            return null;
        }
    }
}
