import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class Task756_deq {
    private static final int BUFFER_SIZE = 1 << 16;
    private static final DataInputStream dataInputStream = new DataInputStream(System.in);
    private static final byte[] buffer = new byte[BUFFER_SIZE];
    private static int bufferPointer = 0, bytesRead = 0;

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        int k = nextInt();

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = nextInt();
        }

        Deque<Integer> deque = new ArrayDeque<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            while (!deque.isEmpty() && array[deque.peekLast()] > array[i]) {
                deque.pollLast();
            }

            deque.offerLast(i);

            if (i >= k - 1) {
                result.append(array[deque.peekFirst()]).append("\n");
            }
        }

        System.out.print(result);
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
