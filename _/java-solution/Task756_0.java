import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Task756_0 {
    // input
    // 7 3
    // 1 3 2 4 5 3 1

    // output
    // 1
    // 2
    // 2
    // 3
    // 1
    public static void main(String[] args) {
        // long start = System.currentTimeMillis();
        // Scanner scanner = new Scanner(System.in);
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

        int n = scanner.nextInt();
        int k = scanner.nextInt();

        int[] array = new int[n];
        // int index = 0;
        // int min = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            // for (int i = 0, j = 1; i < n; i++, j++) {
            array[i] = scanner.nextInt();
            // System.out.println(i+" "+j+" "+array[i]);
            // if (j == k) {
            // min = array[index];
            // for (j = 1; j < k; j++) {
            // if (array[index + j] < min) {
            // min = array[index + j];
            // }
            // }
            // System.out.println(min);
            // j = 2;
            // index = index + 1;
            // }
        }
        recursion(0, array, k);
        // System.out.println(System.currentTimeMillis() - start);
    }

    private static void recursion(int index, int[] array, int k) {
        if (index + k == array.length + 1) {
            return;
        }
        int min = array[index];
        for (int i = index, j = k; j > 0; j--, i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        System.out.println(min);

        // System.out.println(min(index + 1, k - 1, array[index], array));
        // min(index + 1, k - 1, array[index], array);
        recursion(index + 1, array, k);
    }

    private static void min(int index, int k, int min, int[] array) {
        if (k == 0) {
            System.out.println(min);
            return;
        }
        // min = (array[index] < min) ? array[index] : min;
        min(index + 1, k - 1, ((array[index] < min) ? array[index] : min), array);
    }

    // =======================================================================
    // private static int min(int index, int k, int min, int[] array) {
    // if (k == 0) {
    // return min;
    // }
    // min = (array[index] < min) ? array[index] : min;
    // return min(index + 1, k - 1, min, array);
    // }

    // private static int min1(int index, int k, int min, int[] array) {
    // for (int i = index; k > 0; k--, i++) {
    // if (array[i] < min) {
    // min = array[i];
    // }
    // }
    // return min;
    // }

    // private static int min2(int index, int k, int min, int[] array) {
    // for(int i = index; k > 0; k--, i++) {
    // if (array[i] < min) {
    // min = array[i];
    // }
    // }
    // return min;
    // }
    // private static void func(int index, int[] array, int k, int x, int min,
    // Scanner scanner) {
    // array[index] = scanner.nextInt();
    // }
}
