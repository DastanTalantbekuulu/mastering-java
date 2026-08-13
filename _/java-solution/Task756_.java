import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Task756_ {
    public static void main(String[] args) throws IOException {
        // Scanner scanner = new Scanner(new BufferedReader(new
        // InputStreamReader(System.in)));
        InputReader scanner = new InputReader();

        int[] array = new int[scanner.nextInt()];
        int k = scanner.nextInt();
        for (int i = 0; i < array.length; i++) {
            array[i] = scanner.nextInt();
        }
        recursion(0, array, k);
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
    private static void recursion(int index, int[] array, int k) {
        if (index + k == array.length + 1) {
            return;
        }
        int min = array[index];
        for (int i = index + 1, j = k; j > 0; j--, i++) {
            min = (array[i] < min) ? array[i] : min;
        }
        System.out.println(min);
        // min(index+1, k - 1, array[index], array);
        recursion(index + 1, array, k);
    }

    private static void min(int index, int k, int min, int[] array) {
        if (k == 0) {
            System.out.println(min);
            return;
        }
        min(index + 1, k - 1, ((array[index] < min) ? array[index] : min), array);
    }

    private static class InputReader {
        private int c;
        private byte[] buf = new byte[1 << 16];
        private int bufIndex;
        private int numBytesRead;
        private static int[] ints = new int[58];

        public void close() throws IOException {
            System.in.close();
        }

        public InputReader() {
            int value = 0;
            for (int i = 48; i < 58; i++)
                ints[i] = value++;
        }

        private int readJunk(int token) throws IOException {
            if (numBytesRead == -1)
                return -1;
            do {
                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] > token)
                        return 0;
                    bufIndex++;
                }
                numBytesRead = System.in.read(buf);
                if (numBytesRead == -1)
                    return -1;
                bufIndex = 0;
            } while (true);
        }

        public int nextInt() throws IOException {
            if (readJunk(44) == -1)
                throw new IOException();
            int sgn = 1, res = 0;
            c = buf[bufIndex];
            if (c == 45) {
                sgn = -1;
                bufIndex++;
            }
            do {
                while (bufIndex < numBytesRead) {
                    if (buf[bufIndex] > 32) {
                        res = (res << 3) + (res << 1);
                        res += ints[buf[bufIndex++]];
                    } else {
                        bufIndex++;
                        return res * sgn;
                    }
                }
                numBytesRead = System.in.read(buf);
                if (numBytesRead == -1)
                    return res * sgn;
                bufIndex = 0;
            } while (true);
        }
    }
}
