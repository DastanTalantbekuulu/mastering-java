import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Scanner;

public class Task756_copy {
    private static final int BUFFER_SIZE = 1 << 16;
    private static DataInputStream dataInputStream = new DataInputStream(System.in);
    private static byte[] buffer = new byte[BUFFER_SIZE];
    private static int bufferPointer = 0;
    private static int bytesRead = 0;

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        StringBuilder result = new StringBuilder();
        // int k = nextInt();
        // int[] array = new int[n];
        // ArrayDeque<Integer> deque = new ArrayDeque<>(n);
        // for (int i = 0; i < n; i++) {
        //     deque.addLast(nextInt());
        //     // array[i]=nextInt();
        // }
        // // deque.forEach(System.out::println);
        // recursion(deque, k);
        int[] array = new int[n];
        int k = nextInt();
        for (int i = 0; i < array.length; i++) {
            array[i] = nextInt();
        }
        recursion(0, array, k);
        // System.out.println(result.toString());
    }
    // input
    // 7 3
    // 1 3 2 4 5 3 1

    // output
    // 1
    // 2
    // 2
    // 3
    // 1
    // private static void recursion(ArrayDeque deque, int k) {
    //     if (deque.size() == k - 1) {
    //         return;
    //     }
    //     // if (index + k == deque.length + 1) {
    //     // return;
    //     // }
    //     int min = (int) deque.pollFirst();
    //     for (int j = 0; j <k; j++) {
    //         // System.out.println("k " + j+" "+deque.peekFirst());
    //         if (((int) deque.index(j)) < min) {
    //             min = (int) deque.peekFirst();
    //         }
    //         // System.out.println(min);
    //     }
    //     // System.out.println(min);
    //     // min(index + 1, k - 1, array[index], array);
    //     recursion(deque, k);
    // }

    private static void recursion(int index, int[] array, int k) {
        if (index + k == array.length + 1) {
            return;
        }
        // int min = array[index];
        // for (int i = index + 1, j = k - 1; j > 0; j--, i++) {
        //     if (array[i] < min) {
        //         min = array[i];
        //     }
        // }
        // result.append(min);
        // result.append("\n");
        // System.out.println(min);
        // result.append(min(index + 1, k - 1, array[index], array));
        // result.append("\n");
        min(index + 1, k - 1, array[index], array);
        recursion(index + 1, array, k);
    }

    private static void min(int index, int k, int min, int[] array) {
        if (k == 0) {
            System.out.println(min);
            return;
        }
       min(index + 1, k - 1, ((array[index] < min) ? array[index] : min), array);
    }

    private static int nextInt() throws IOException {
        int result = 0;
        byte currentByte = read();
        while (currentByte <= ' ') {
            currentByte = read();
        }
        boolean isNegative = (currentByte == '-');
        if (isNegative) {
            currentByte = read();
        }
        do {
            result = result * 10 + currentByte - '0';
        } while ((currentByte = read()) >= '0' && currentByte <= '9');
        return isNegative ? -result : result;
    }

    private static byte read() throws IOException {
        if (bufferPointer == bytesRead) {
            bytesRead = dataInputStream.read(buffer, bufferPointer = 0, BUFFER_SIZE);
            if (bytesRead == -1) {
                buffer[0] = -1;
            }
        }
        return buffer[bufferPointer++];
    }
}
