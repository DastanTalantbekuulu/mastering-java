import java.util.Scanner;

/**
 * Compound
 * C(o(m(po)u)n)d
 * Compounds
 * C(o(m(p(o)u)n)d)s
 * CompoundUndoManager
 * C(o(m(p(o(u(n(d(U(n)d)o)M)a)n)a)g)e)r
 * CompoundUndoManagers
 * C(o(m(p(o(u(n(d(U(nd)o)M)a)n)a)g)e)r)s
 * CompoundUndoManagers
 * C(o(m(p(o(u(n(d(U(nd)o)M)a)n)a)g)e)r)s
 */
public class InsertBracket {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(brackets(input, 1, input.length()));
        System.out.println(brackets(input));
        brackets(input, 0);
        scanner.close();
    }

    private static void brackets(String input, int index) {
        if (index >= input.length()) {
            return;
        }
        if (input.length() % 2 == 0 && (input.length() / 2) - 1 == index) {
            System.out.print(input.charAt(index));
            System.out.print(input.charAt(index + 1));
            index = index + 2;
        } else if (input.length() % 2 > 0 && input.length() / 2 == index) {
            System.out.print(input.charAt(index));
            index = index + 1;
        }
        if (input.length() / 2 > index) {
            System.out.print(input.charAt(index));
            System.out.print("(");
        } else if (input.length() / 2 < index) {
            System.out.print(")");
            System.out.print(input.charAt(index));
        }
        index = index + 1;
        brackets(input, index);
    }

    private static String brackets(String input, int i, int j) {
        input = input.substring(0, i) + "(" + input.substring(i, j - 1) + ")" +
                input.substring(j - 1);
        i = i + 2;
        if (j - i == 1 || i == j) {
            return input;
        }
        return brackets(input, i, j);
    }

    private static String brackets(String input) {
        if (input.length() <= 2) {
            return input;
        }
        return input.substring(0, 1) + "(" +
                brackets(input.substring(1, input.length() - 1)) +
                ")" + input.substring(input.length() - 1);
    }

}