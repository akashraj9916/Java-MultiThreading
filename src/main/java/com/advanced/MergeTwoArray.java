package com.advanced;

import java.util.stream.IntStream;

public class MergeTwoArray {
    public static void main(String[] args) {
        int numbers1[] = new int[]{2, 4, 7, 9, 23, 56,78};
        int numbers2[] = new int[]{1, 4, 6, 17, 45};
        merge(numbers1,numbers2);
    }

    private static void merge(int[] numbers1, int[] numbers2) {
        int length1 = numbers1.length;
        int length2 = numbers2.length;
        int[] mergedarray = new int[length1 + length2];
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < length1 && j < length2) {
            if (numbers1[i] > numbers2[j]) {
                mergedarray[k] = numbers2[j];
                j++;
            } else {
                mergedarray[k] = numbers1[i];
                i++;
            }
            k++;

        }
        if (length1 < length2 ){
            int diff = length2 - (length1+1);
            for(int l = 0 ; l<= diff ; l++ ) {
                mergedarray[k] = numbers2 [length2 - (diff -l+1)];
                k++;
            }
        }
        else{
            int diff = length1 - (length2 +1);
            for(int l = 0 ; l<= diff  ; l++ ) {
                mergedarray[k] = numbers1 [length1 -(diff -l+1)];
                k++;
            }
        }
        IntStream.range(0,mergedarray.length).forEach(arr ->{
            System.out.println(mergedarray[arr]);
        });
    }
}
