import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Main {

    // public static void main(String[] args) {
    // Scanner scanner = new Scanner(System.in);
    // int a = scanner.nextInt();
    // int b = scanner.nextInt();
    // System.out.println(a + " " + b);
    // }
    // }

    public static void main(String[] args) throws IOException {
        // Используем BufferedReader и BufferedWriter для ускорения ввода/вывода
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int n = Integer.parseInt(tokenizer.nextToken());
        int k = Integer.parseInt(tokenizer.nextToken());

        int[] array = new int[n];
        tokenizer = new StringTokenizer(reader.readLine());
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(tokenizer.nextToken());
        }

        // Решение с использованием deque
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // Удаляем элементы, выходящие за пределы текущего окна
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // Удаляем элементы, которые больше текущего
            while (!deque.isEmpty() && array[deque.peekLast()] > array[i]) {
                deque.pollLast();
            }

            // Добавляем текущий индекс
            deque.offerLast(i);

            // Минимум текущего окна записываем в вывод
            if (i >= k - 1) {
                writer.write(array[deque.peekFirst()] + "\n");
            }
        }

        writer.flush();
        writer.close();
    }
}
