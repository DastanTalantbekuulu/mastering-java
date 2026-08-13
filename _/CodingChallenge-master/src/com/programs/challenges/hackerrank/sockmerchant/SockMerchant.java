package com.programs.challenges.hackerrank.sockmerchant;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


/**
 * For example, there are  socks with colors . There is one pair of color  and one of color .
 * There are three odd socks left, one of each color. The number of pairs is 2.
 * sockMerchant has the following parameter(s):
 *
 * <li>n: the number of socks in the pile</li>
 * <li>ar: the colors of each sock</li>
 *
 * Sample Input
 * 9
 * 10 20 20 10 10 30 50 10 20
 * Sample Output
 * 3
 */
public class SockMerchant {

    // Complete the sockMerchant function below.
    static int sockMerchant(int n, int[] ar) {
        if(ar == null || ar.length == 0){
            return 0;
        }

        int totalPairs = 0;

        Map<Integer, Integer> countMap = new HashMap<>();
        for(Integer i : ar){
            if(countMap.containsKey(i)){
                int value = countMap.get(i);
                countMap.put(i, ++value);
            } else {
                countMap.put(i,1);
            }
        }

        for (Map.Entry<Integer,Integer> entry : countMap.entrySet()){
            if(entry.getValue() % 2 == 0){
                totalPairs = totalPairs + (entry.getValue() / 2);
            } else if(entry.getValue() > 1){
                totalPairs = totalPairs + ((entry.getValue() - 1) / 2);
            }
        }

        return totalPairs;

    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int [] ar = {10,20,20,10,10,30,50,10,20,20};

        int result = sockMerchant(ar.length, ar);


        System.out.println(result);
    }

}
