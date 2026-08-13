import java.util.Scanner;

public class Task113656 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        if (text.length() % 2 == 0){
            System.out.println(bracketEven(text,"",0));
        } else {
            System.out.println(bracketsOdd(text,"",0));
        }

    }
    public static String bracketsOdd(String initialStr, String output, int i) {
        String s = output;
        if (i < initialStr.length()) {
            s = s + initialStr.charAt(i);
            if (i < (initialStr.length() / 2)) {
                s = s + "(";
            } if ((initialStr.length() / 2) <= i && i < initialStr.length() - 1){
                s = s + ")";
            }

            return bracketsOdd(initialStr, s, i + 1);
        }
        return s;

    }
    public static String bracketEven(String initialStr, String output, int i) {
        String s = output;
        if (i < initialStr.length()) {
            s = s + initialStr.charAt(i);
            if (i < (initialStr.length() / 2) - 1) {
                s = s + "(";
            } if ((initialStr.length() / 2) <= i && i < initialStr.length() - 1){
                s = s + ")";
            }

            return bracketEven(initialStr, s, i + 1);
        }
        return s;
    }

}
