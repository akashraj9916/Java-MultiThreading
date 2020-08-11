package com.advanced;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSortForkJoin {
    public static void main(String[] args) {
        int maxval = 15000000;
        int arr[] = new int[maxval];
        int arr1[] = new int[maxval];
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        Random random = new Random();
        MergeSortParallelForkJoin calculator = new MergeSortParallelForkJoin(arr1,0,arr.length-1);
        for(int i=0;i < maxval ;i++){
            arr[i] = random.nextInt(1000);
            arr1[i] = random.nextInt(1000);
        }

        MergeSortSingle mergeSortSingle = new MergeSortSingle();
        long seqStart = System.currentTimeMillis();
        mergeSortSingle.sort(arr,0,arr.length-1);
        long seqEnd = System.currentTimeMillis() - seqStart;
        System.out.println(" Total time taken by sequential :" + seqEnd);
       // printArray(arr);
        long parStart = System.currentTimeMillis();
        pool.invoke(calculator);
        long parEnd = System.currentTimeMillis() - parStart;
        System.out.println(" Total time taken by parallel :" + parEnd);
      //  printArray(arr1);
    }
    static void printArray(int arr[])
    {
        int n = arr.length;
        for (int i = 0; i < n; ++i)
            System.out.print(arr[i] + " ");
    }

    public static class MergeSortParallelForkJoin extends RecursiveAction {
        int[] arr ;
        int low,  high;
        public MergeSortParallelForkJoin(int[] arr, int low, int high) {
            this.arr = arr;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {
            if((high - low) <= 10){
              //  System.out.println(" Going for sequential....");
                sort(arr,low,high);
            }
            else{
             //   System.out.println(" Going for parrallel....");
                int mid = (low + high) /2;
                MergeSortParallelForkJoin task1 = new MergeSortParallelForkJoin(arr,low,mid);
                MergeSortParallelForkJoin task2 = new MergeSortParallelForkJoin(arr,mid+1,high);
                invokeAll(task1,task2);
                merge(arr,low,mid,high);

            }

        }
        void merge(int arr[], int l, int m, int r)
        {
            // Find sizes of two subarrays to be merged
            int n1 = m - l + 1;
            int n2 = r - m;

            /* Create temp arrays */
            int L[] = new int[n1];
            int R[] = new int[n2];

            /*Copy data to temp arrays*/
            for (int i = 0; i < n1; ++i)
                L[i] = arr[l + i];
            for (int j = 0; j < n2; ++j)
                R[j] = arr[m + 1 + j];

            /* Merge the temp arrays */

            // Initial indexes of first and second subarrays
            int i = 0, j = 0;

            // Initial index of merged subarry array
            int k = l;
            while (i < n1 && j < n2) {
                if (L[i] <= R[j]) {
                    arr[k] = L[i];
                    i++;
                }
                else {
                    arr[k] = R[j];
                    j++;
                }
                k++;
            }

            /* Copy remaining elements of L[] if any */
            while (i < n1) {
                arr[k] = L[i];
                i++;
                k++;
            }

            /* Copy remaining elements of R[] if any */
            while (j < n2) {
                arr[k] = R[j];
                j++;
                k++;
            }
        }
        void sort(int arr[], int l, int r)
        {
            if (l < r) {
                // Find the middle point
                int m = (l + r) / 2;

                // Sort first and second halves
                sort(arr, l, m);
                sort(arr, m + 1, r);

                // Merge the sorted halves
                merge(arr, l, m, r);
            }
        }

    }
}
