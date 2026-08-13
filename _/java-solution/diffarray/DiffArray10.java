package diffarray;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DiffArray10 {
  public static void main(String[] args) throws IOException {
    Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    int n = scanner.nextInt();
    Set<Integer> set1 = new HashSet<>(n);
    while (--n >= 0) {
      set1.add(scanner.nextInt());
    }
    n = scanner.nextInt();
    Set<Integer> set2 = new HashSet<>(n);
    while (--n >= 0) {
      set2.add(scanner.nextInt());
    }
    if ((set1.size() == set2.size()) && set1.containsAll(set2)) {
      System.out.println("YES");
    } else {
      System.out.println("NO");
    }
    scanner.close();
  }
}