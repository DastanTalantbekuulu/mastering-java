import java.util.Scanner;

public class MessageForum {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(deleteMessage(n, scanner, new int[1001]));
    }

    private static int deleteMessage(int n, Scanner scanner, int[] map) {
        if (n > 0) {
            map[scanner.nextInt()] = scanner.nextInt();
            return deleteMessage(n - 1, scanner, map);
        }
        return delete(1, getKey(scanner.nextInt(), map), map);
    }

    private static int delete(int n, int del, int[] map) {
        if (del == -1) {
            return n;
        }
        return delete(n + 1, getKey(del, map), map);
    }

    private static int getKey(int del, int[] map) {
        for (int i = 1; i < map.length; i++) {
            if (map[i] == del) {
                map[i] = -1;
                return i;
            }
        }
        return -1;
    }
}
