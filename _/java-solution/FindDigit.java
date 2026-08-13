import java.util.Scanner;

public class FindDigit {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        int numInText = getDigitInText(input, 0);
        System.out.println(numInText);
    }

    private static int getDigitInText(String input, int i) {
        if ('0' <= input.charAt(0) && input.charAt(0) <= '9') {
            i = i + 1;
        }
        if (input.length() == 1) {
            return i;
        }
        return getDigitInText(input.substring(1), i);
    }
}