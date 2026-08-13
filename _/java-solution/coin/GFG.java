package coin;

class GFG {

    public static void main(String args[]) {
        int coins[] = { 11, 20, 30, 40, 11, 99 };
        int m = coins.length;
        int n = 100;

        
    }

    private static int count(int coins[], int m, int sum) {
        if (sum == 0) {
            return 1;
        }
        if (sum < 0) {
            return 0;
        }
        if (m <= 0) {
            return 0;
        }
        return count(coins, m - 1, sum) + count(coins, m, sum - coins[m - 1]);
    }

    private static int minCoins(int coins[], int m, int sum) {
        if (sum == 0) {
            return 0;
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < m; i++) {
            if (coins[i] <= sum) {
                int sub_res = minCoins(coins, m, sum - coins[i]);
                if (sub_res != Integer.MAX_VALUE && sub_res + 1 < res) {
                    res = sub_res + 1;
                }
            }
        }
        return res;
    }
}
