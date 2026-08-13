import java.util.Scanner;

public class TaskNumber100 {

    public static void main(String[] args) {
      
        Scanner scanner = new Scanner(System.in);

        String lineOne = scanner.nextLine();
        String lineTwo = scanner.nextLine();

        int len = lineOne.length();

        for (int shift = 0; shift < len; shift++) {
            String rotated = lineOne.substring(len - shift) + lineOne.substring(0, len - shift);

            if (rotated.equals(lineTwo)) {
                System.out.println(shift);
                return;
            }
        }

        System.out.println(-1);
    }
}
