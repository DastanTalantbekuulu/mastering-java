import java.util.Scanner;

public class Task1791 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String s1 = scanner.nextLine();
        String s2 = scanner.nextLine();

        int distance = calculateDistance(s1, s2);
        System.out.println(distance);
    }

    public static int calculateDistance(String s1, String s2) {
        if (s1 == null || s2 == null) {
            return 0;
        }

        int len1 = s1.length();
        int len2 = s2.length();

        int[][] num = new int[len1 + 1][len2 + 1];

        for (int i = 0; i <= len1; i++) {
            num[i][0] = i;
        }
        for (int j = 0; j <= len2; j++) {
            num[0][j] = j;
        }

        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    num[i][j] = num[i - 1][j - 1];
                } else {
                    int del = num[i - 1][j] + 1;
                    int ins = num[i][j - 1] + 1;
                    int rep = num[i - 1][j - 1] + 1;
                    num[i][j] = Math.min(del, Math.min(ins, rep));
                }
            }
        }
        return num[len1][len2];
    }
}
