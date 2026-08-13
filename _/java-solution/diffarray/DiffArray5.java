package diffarray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class DiffArray5 {
    
    private static BufferedReader bufferedReader;
    private static StringTokenizer stringTokenizer;

    public static void main(String[] args) throws IOException {
        bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(next());
        Set<Integer> set1 = new HashSet<>(n);
        while (--n >= 0) {
            set1.add(Integer.parseInt(next()));
        }
        n = Integer.parseInt(next());
        Set<Integer> set2 = new HashSet<>(n);
        while (--n >= 0) {
            set2.add(Integer.parseInt(next()));
        }
        if ((set1.size() == set2.size()) && set1.containsAll(set2)) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static String next() {
        while (stringTokenizer == null || !stringTokenizer.hasMoreElements()) {
            try {
                stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            } catch (IOException ioe) {
            }
        }
        return stringTokenizer.nextToken();
    }
}
