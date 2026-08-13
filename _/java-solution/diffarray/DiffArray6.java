package diffarray;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class DiffArray6 {
    private static final int BUFFER_SIZE = 1 << 16;
    private static DataInputStream dataInputStream  = new DataInputStream(System.in);
    private static byte[] buffer = new byte[BUFFER_SIZE];
    private static int bufferPointer = 0;
    private static int bytesRead = 0;

    public static void main(String[] args) throws IOException {
        int n = nextInt();
        Set<Integer> set1 = new HashSet<>(n);
        while (--n >= 0) {
            set1.add(nextInt());
        }
        n = nextInt();
        Set<Integer> set2 = new HashSet<>(n);
        while (--n >= 0) {
            set2.add(nextInt());
        }
        if ((set1.size() == set2.size()) && set1.containsAll(set2)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
        if (dataInputStream == null) {
            return;
        }
        dataInputStream.close();
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
