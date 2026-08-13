package coin;

import java.util.ArrayDeque;
import java.util.TreeMap;

public class Coin {
    private static TreeMap<Integer, Integer> coin;
    private static ArrayDeque<Integer> pay;

    public static void main(String[] args) {
        coin = new TreeMap<>();
        coin.put(11, 4);
        coin.put(20, 2);
        coin.put(30, 2);
        coin.put(40, 2);
        coin.put(99, 2);

        pay = new ArrayDeque<>();

        int coins[] = { 11, 20, 30, 40, 11, 99 };
        int m = coins.length;
        int n = 100;
    }

    private static int count(int n) {
        if (get(n) == n) {
            pay.add(n);
            return 1;
        }
        
        return 0;
    }

    private static int get(int n) {
        if (coin.containsKey(n) && coin.get(n) > 0) {
            coin.put(n, coin.get(n) - 1);
            return n;
        }
        return -1;
    }
}
