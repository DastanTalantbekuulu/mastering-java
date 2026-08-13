package diffarray;
import java.util.Scanner;

public class DiffArray3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] arr1 = new int[n];
        while (--n >= 0) {
            arr1[n] = scanner.nextInt();
        }
        n = scanner.nextInt();
        boolean flag = true;
        while (--n >= 0) {
            if (!compare(scanner.nextInt(), arr1)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    private static boolean compare(int a, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == a) {
                return true;
            }
        }
        return false;
    }
}
