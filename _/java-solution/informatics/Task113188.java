package informatics;

import java.util.HashMap;
import java.util.Scanner;

public class Task113188 {
    String example = """
            5 1
            1 3
            6 -5
            6 -4
            2 2
            2 -1
            """;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int count = scanner.nextInt();
        int priority = scanner.nextInt();
        HashMap<Integer, Friend> map = new HashMap<>();

        for (int i = 0; i < count; i++) {
            map.put(i, new Friend(scanner.nextInt(), scanner.nextInt()));
        }
        
        scanner.close();
    }
    static class Friend{
        int priority;
        int add;
        public Friend(int priority, int add){
            this.priority = priority;
            this.add = add;
        }
    }

}
