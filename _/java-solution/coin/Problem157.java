package coin;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.TreeSet;

public class Problem157 {
    // private static ArrayDeque<Integer> coin;
    // private static ArrayDeque<Integer> pay;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int target = scanner.nextInt();
        int m = scanner.nextInt();
        // coin = new ArrayDeque<>();
        // pay = new ArrayDeque<>();
        // while (m > 0) {
        // coin.add(scanner.nextInt());
        // m = m - 1;
        // }
        // System.out.println(counts(m, n, 0));
        // pay.forEach(System.out::println);
        int[] coin = new int[m];
        for (int i = 0; i < m; i++) {
            coin[i] = scanner.nextInt();
        }
        // System.out.println(numberOfCombinations(coin, target));
        List<Integer> am = makeChange(target, coin);
        am.forEach(System.out::println);
    }

    public static int numberOfCombinations(int[] coin, int target) {
        int n = coin.length;
        int[][] dp = new int[n + 1][target + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= target; j++) {
                if (i == 0) {
                    dp[i][j] = 0;
                } else if (j == 0) {
                    dp[i][j] = 1;
                } else {
                    if (j - coin[i - 1] >= 0) {
                        dp[i][j] = dp[i - 1][j] + dp[i][j - coin[i - 1]];
                    } else {
                        dp[i][j] = dp[i - 1][j];
                    }
                }
            }
        }
        return dp[n][target];
    }
    public static List<Integer> makeChange(int target, int[] coin) {
        if (target == 0) {
            return new ArrayList<Integer>();
        }
        if (coin.length == 0) {
            return new ArrayList<Integer>();
        }
        if (coin[0] > target) {
            int[] newDenominations = new int[coin.length - 1];
            for (int i = 1; i < coin.length; i++) {
                newDenominations[i-1] = coin[i];
            }
            return makeChange(target, newDenominations);
        }
        List<Integer> useIt = makeChange(target - coin[0], coin);
        if (!useIt.isEmpty()) {
            useIt.add(coin[0]);
            return useIt;
        }
        List<Integer> loseIt = makeChange(target, new int[coin.length - 1]);
        if (useIt.isEmpty() && loseIt.isEmpty()) {
            return new ArrayList<Integer>();
        }
        if (useIt.isEmpty() || (loseIt.size() < useIt.size())) {
            return loseIt;
        }
        return useIt;
    }
}
