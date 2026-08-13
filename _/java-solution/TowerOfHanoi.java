import java.util.Scanner;

public class TowerOfHanoi {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int disk = scanner.nextInt();
        hanoi(disk, 1, 3, 2);
    }

    private static void hanoi(int n, int from, int to, int via) {
        if (n == 1) {
            System.out.println("1 " + from + " " + to);
        } else {
            hanoi(n - 1, from, via, to);
            System.out.println(n + " " + from + " " + to);
            hanoi(n - 1, via, to, from);
        }
    }
}