package com.programs.challenges.hackerrank.arraymanipulation;

import java.util.ArrayList;
import java.util.List;

/**
 * The cost of a stock on each day is given in an array,
 * find the max profit that you can make by buying and selling in those days.
 * For example, if the given array is {100, 180, 260, 310, 40, 535, 695},
 * the maximum profit can earned by buying on day 0, selling on day 3.
 * Again buy on day 4 and sell on day 6.
 * If the given array of prices is sorted in decreasing order,
 * then profit cannot be earned at all.
 */
public class StockBuySell {

    public static void main(String[] args) {

        int[] arr = {100, 180, 260, 310, 40, 535, 695};
        solution(arr, arr.length);
    }

    static void solution(int[] prices, int n) {
        if(n < 2)
            return;

        int count = 0;
        List<Interval> buySellList = new ArrayList<>();

        int i = 0;
        while (i < n){

            while (i < n -1 && prices[i+1] <= prices[i]) {
                i++;
            }

            if(i == n-1)
                break;

            Interval interval = new Interval();
            interval.buy = i++;

            while (i < n && prices[i] >= prices[i - 1]){
                i++;
            }

            interval.sell = i - 1;
            buySellList.add(interval);

            count++;

        }


        if(count == 0){
            System.out.println("There is no day when buying the stock "
                    + "will make profit");
        } else {
            for(int j=0;j<count;j++){
                System.out.println("Buy on day: " + buySellList.get(j).buy
                        + "        "
                        + "Sell on day : " + buySellList.get(j).sell);
            }
        }


        return;

    }

    static class Interval {
        int buy, sell;
    }

}
