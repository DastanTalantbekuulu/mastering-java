package com.programs.challenges.hackerrank.arraymanipulation;

public class ArrayManipulation {

    // Complete the arrayManipulation function below.
    static long arrayManipulation(int n, int[][] queries) {
        long[] finalArr = new long[n + 2];
        for (int i = 0 ; i < queries.length;i++){
            int a = queries[i][0];
            int b = queries[i][1];
            int k = queries[i][2];
            finalArr[a] += k;
            finalArr[b+1] -= k;
        }

        print(finalArr);
        System.out.println();
        return getMaximum(finalArr);
    }

    static long getMaximum(long[] finalArr) {
        long max = Long.MIN_VALUE;
        long sum = 0;
        for (int i = 0; i < finalArr.length; i++) {
            sum += finalArr[i];
            max = Math.max(max, sum);
        }
        return max;
    }
    
    static void print(long [] arr) {
    	System.out.println("Final Array - ");
    	for(int i=0;i < arr.length; i++) {
    		System.out.print(arr[i]+"|");
    	}
    }

    public static void main(String[] args) {
        int[][] queries = new int[3][3];

        queries[0][0] = 1;
        queries[0][1] = 2;
        queries[0][2] = 100;
        queries[1][0] = 2;
        queries[1][1] = 5;
        queries[1][2] = 100;
        queries[2][0] = 3;
        queries[2][1] = 4;
        queries[2][2] = 100;

        print(queries);

       
        System.out.println("Result :"+arrayManipulation(5,queries));

    }

    static void print(int [][] arr){
        for(int i = 0 ; i < arr.length;i++){
            for(int j = 0; j < arr[0].length; j++){
                System.out.print(arr[i][j]+" | ");
            }
            System.out.println();
        }
    }

}
