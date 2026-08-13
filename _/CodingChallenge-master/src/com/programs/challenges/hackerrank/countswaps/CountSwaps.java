package com.programs.challenges.hackerrank.countswaps;

public class CountSwaps {

    // Complete the countSwaps function below.
    static void countSwaps(int[] a) {

        int swaps = 0;
        int n = a.length;

        for (int i = 0; i < n; i++) {

            for (int j = 0; j < n - 1 - i; j++) {
                // Swap adjacent elements if they are in decreasing order
                if (a[j] > a[j + 1]) {
                    swaps++;
                    swap(a, j, j + 1);
                }
            }
        }

        System.out.println("Array is sorted in "+swaps+" swaps.");
        System.out.println("First Element: "+a[0]);
        System.out.println("Last Element: "+a[n-1]);

    }

    static void swap(int[] a, int first, int second) {
        int temp = a[first];
        a[first] = a[second];
        a[second] = temp;
    }


    public static void main(String[] args) {
        int [] a = {1,3,2,1,1,5};
        countSwaps(a);
    }


}
