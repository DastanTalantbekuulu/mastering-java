import java.util.HashMap;
import java.util.Scanner;

public class MessageTree {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(deleteMessage(scanner.nextInt(), scanner, new HashMap<>()));
    }

    private static int deleteMessage(int n, Scanner scanner, HashMap<Integer, Integer> map) {
        if (n > 0) {
            map.put(scanner.nextInt(), scanner.nextInt());
            return deleteMessage(n - 1, scanner, map);
        }
        return delete(1, get(map, scanner.nextInt()), map);
    }

    private static int delete(int count, int del, HashMap<Integer, Integer> map) {
        if (del == -1) {
            return count;
        }
        return delete(count + 1, get(map, del), map);
    }

    private static int get(HashMap<Integer, Integer> map, int value) {
        for (int key : map.keySet()) {
            if (map.get(key) == value) {
                return key;
            }
        }
        return -1;
    }
}
