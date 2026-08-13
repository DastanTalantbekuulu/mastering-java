import java.util.ArrayList;
import java.util.Scanner;

public class Problem155 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = 2; // scanner.nextInt();
        int n = 3; // scanner.nextInt();
        permute(k, 1, n, 1);
    }

    private static void permute(int k, int place, int n, int fixed) {
        if(place <= k)
        permute(k, place + 1, n, fixed + 1);
    }

    private static int pow(int n, int k) {
        int r = n;
        while (--k > 0) {
            r = r * n;
        }
        return r;
    }

}
