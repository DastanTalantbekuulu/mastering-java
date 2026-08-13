package com.programs.challenges.hackerrank.arrayrotate;

public class ArrayRotateJuggling {

    public static void main(String[] args) {

        int[]arr = { 1, 2, 3, 4, 5, 6, 7 };
        rotate(arr,1,arr.length);
        print(arr);

    }

    static void print(int[]arr){
        for(int a : arr){
            System.out.print(a+" ");
        }
    }

    static void rotate(int[] arr, int d, int n){

        d = d %n; // to handle d>n

        int lastIndex,k,temp;

        int gcd = gcd(d,n);
        for(int i = 0; i < gcd;i++){
            temp = arr[i];
            lastIndex = i;
            while(true){
                k = lastIndex + d;
                if(k >= n)
                    k = k - n;
                if(k == i)
                    break;
                arr[lastIndex] = arr[k];
                lastIndex = k;
            }
            arr[lastIndex] = temp;
        }

    }

    static int gcd(int a,int b){
        if(b == 0)
            return a;
        return gcd(b,a % b);

    }

}
