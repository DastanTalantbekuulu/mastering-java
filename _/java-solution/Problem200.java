import java.util.Scanner;

public class Problem200 {

    private static char grid[][];
    private static int count = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        grid = new char[n + 1][n];
        String line;
        for (int i = 1; i <= n; i++) {
            line = '*' + scanner.next();
            grid[i] = line.toCharArray();
        }
        int y = scanner.nextInt();
        int x = scanner.nextInt();
        countEmptyCell(y, x);
        System.out.println(count);
        scanner.close();
    }

    private static void countEmptyCell(int y, int x) {
        if (grid[y][x] == '*') {
            return;
        }
        count++;
        grid[y][x] = '*';
        countEmptyCell(y - 1, x);
        countEmptyCell(y + 1, x);
        countEmptyCell(y, x - 1);
        countEmptyCell(y, x + 1);
    }
}