package diffarray;
import java.util.HashSet;
import java.util.Set;
import java.io.IOException;

public class DiffArray9 {
  public static void main(String[] args) throws IOException {
    InputReader inputReader = new InputReader();
    int n = inputReader.nextInt();
    Set<Integer> set1 = new HashSet<>(n);
    while (--n >= 0) {
      set1.add(inputReader.nextInt());
    }
    n = inputReader.nextInt();
    Set<Integer> set2 = new HashSet<>(n);
    while (--n >= 0) {
      set2.add(inputReader.nextInt());
    }
    if ((set1.size() == set2.size()) && set1.containsAll(set2)) {
      System.out.println("YES");
    } else {
      System.out.println("NO");
    }
    inputReader.close();
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