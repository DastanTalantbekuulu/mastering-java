
import java.util.Stack;

public class ReversePolishNotation {

    private static final char END_CHAR = '$';

    private final StringBuilder infix;
    private final StringBuilder reverceInfix;
    private final Stack<StringBuilder> postfix = new Stack<>();
    private final Stack<StringBuilder> prefix = new Stack<>();

    public ReversePolishNotation(StringBuilder input) {
        input.insert(0, END_CHAR);
        input.insert(input.length(), END_CHAR);

        infix = input;
        reverceInfix = reverceStringBuilder(infix);
    }

    public Stack<StringBuilder> getPrefix() {
        Stack<Character> stack = new Stack<>();
        Stack<StringBuilder> result = new Stack<>();

        // StringBuilder reverce = reverceStringBuilder(infix);
        stack.push(reverceInfix.charAt(0));
        // System.out.println("reverce: " + reverce);

        for (int i = 1; i < reverceInfix.length(); i++) {
            if (Character.isDigit(reverceInfix.charAt(i))) {
                StringBuilder digit = new StringBuilder();
                while (!isDelimiter(reverceInfix.charAt(i))
                        && !isOperator(reverceInfix.charAt(i))
                        && !isEndChar(reverceInfix.charAt(i))) {
                    digit.append(reverceInfix.charAt(i));
                    i++;
                    if (i == reverceInfix.length()) {
                        break;
                    }
                }
                result.push(digit);
                i--;
            } else if (isOperator(reverceInfix.charAt(i))) {
                if (reverceInfix.charAt(i) == ')') {
                    stack.push(reverceInfix.charAt(i));
                } else if (reverceInfix.charAt(i) == '(') {
                    if (getPriority(reverceInfix.charAt(i)) < getPriority(stack.peek())) {
                        Character s = stack.pop();
                        while (s != ')') {
                            result.push(new StringBuilder(s.toString()));
                            s = stack.pop();
                        }
                    } else if (getPriority(reverceInfix.charAt(i)) > getPriority(stack.peek())) {
                        try {
                            throw new Exception();
                        } catch (Exception e) {
                            System.out.println("ERROR INPUT DATA");
                            return null;
                        }
                    } else {
                        stack.pop();
                    }
                } else if (isOperator(reverceInfix.charAt(i))) {
                    if (stack.empty() && getPriority(reverceInfix.charAt(i)) <= getPriority(stack.peek())) {
                        while (getPriority(reverceInfix.charAt(i)) <= getPriority(stack.peek())) {
                            result.push(new StringBuilder(stack.pop().toString()));
                        }
                    }
                    stack.push(reverceInfix.charAt(i));
                }
            } else if (isEndChar(reverceInfix.charAt(i))) {
                Character s = stack.pop();
                while (s != END_CHAR) {
                    result.push(new StringBuilder(s.toString()));
                    s = stack.pop();
                }
            }
            // System.err.println();
            // for (int e = 0; e < stack.size(); e++) {
            // System.err.print(stack.get(e));
            // }
        }
        // prefix = reverceStack(result);
        // prefix = reverceStack(result);
        int size = result.size();
        for (int i = 0; i < size; i++) {
            prefix.push(result.pop());
        }
        return prefix;
    }

    public Stack<StringBuilder> getPostfix() {
        Stack<Character> stack = new Stack<>();

        stack.push(infix.charAt(0));

        for (int i = 1; i < infix.length(); i++) {

            if (Character.isDigit(infix.charAt(i))) {
                StringBuilder digit = new StringBuilder();
                while (!isDelimiter(infix.charAt(i))
                        && !isOperator(infix.charAt(i))
                        && !isEndChar(infix.charAt(i))) {
                    digit.append(infix.charAt(i));
                    i++;
                    if (i == infix.length()) {
                        break;
                    }
                }
                postfix.push(digit);
                i--;
            } else if (isOperator(infix.charAt(i))) {
                if (infix.charAt(i) == '(') {
                    stack.push(infix.charAt(i));
                } else if (infix.charAt(i) == ')') {
                    if (getPriority(infix.charAt(i)) < getPriority(stack.peek())) {
                        Character s = stack.pop();
                        while (s != '(') {
                            postfix.push(new StringBuilder(s.toString()));
                            s = stack.pop();
                        }
                    } else if (getPriority(infix.charAt(i)) > getPriority(stack.peek())) {
                        try {
                            throw new Exception();
                        } catch (Exception e) {
                            System.out.println("ERROR INPUT DATA");
                            return null;
                        }
                    } else {
                        stack.pop();
                    }
                } else if (isOperator(infix.charAt(i))) {
                    if (getPriority(infix.charAt(i)) <= getPriority(stack.peek())) {
                        while (getPriority(infix.charAt(i)) <= getPriority(stack.peek())) {
                            postfix.push(new StringBuilder(stack.pop().toString()));
                        }
                    }
                    stack.push(infix.charAt(i));
                }

            } else if (isEndChar(infix.charAt(i))) {
                Character s = stack.pop();
                while (s != END_CHAR) {
                    postfix.push(new StringBuilder(s.toString()));
                    s = stack.pop();
                }
            }
        }
        return postfix;
    }

    public Double getSolution() {
        Stack<Double> solution = new Stack<>();

        for (StringBuilder item : postfix) {
            if (isOperator(item.charAt(0))) {
                double a, b;
                b = Double.parseDouble(solution.pop().toString());
                a = Double.parseDouble(solution.pop().toString());
                solution.push(makeOperation(a, b, item.charAt(0)));
            } else {
                solution.push(Double.valueOf(item.toString()));
            }
        }
        return solution.pop();
    }

    public boolean validate() {
        int bracket = 0;

        for (int i = 0; i < infix.length(); i++) {
            if (infix.charAt(i) == '(') {
                bracket++;
            } else if (infix.charAt(i) == ')') {
                bracket--;
            }

            if (!Character.isDigit(infix.charAt(i))
                    && !isOperator(infix.charAt(i))
                    && infix.charAt(i) != END_CHAR) {
                System.out.println("ERROR INPUT DATA");
                return false;
            }
        }

        if (bracket != 0) {
            System.out.println("ERROR INPUT DATA");
            return false;
        }

        return true;
    }

    private StringBuilder reverceStringBuilder(StringBuilder infix) {
        StringBuilder reverceInfix = new StringBuilder();
        Stack<StringBuilder> result = new Stack<>();

        // System.out.println("infix: " + infix);
        for (int i = 0; i < infix.length(); i++) {
            if (Character.isDigit(infix.charAt(i))) {
                StringBuilder digit = new StringBuilder();
                while (!isDelimiter(infix.charAt(i))
                        && !isOperator(infix.charAt(i))
                        && !isEndChar(infix.charAt(i))) {
                    digit.append(infix.charAt(i));
                    i++;
                    if (i == infix.length()) {
                        break;
                    }
                }
                // System.out.println("digit: " + digit);
                // reverceInfix.append(digit);
                result.push(digit);
                i--;
            } else {
                // System.out.println("infix.charAt(i): " + infix.charAt(i));
                // reverceInfix.append(infix.charAt(i));
                result.push(new StringBuilder(String.valueOf(infix.charAt(i))));
            }
        }
        int size = result.size();
        // System.out.println("size: " + size);
        for (int i = 0; i < size; i++) {
            // StringBuilder str = result.pop();
            reverceInfix.append(result.pop());
            // System.out.println("i: " + i);
            // System.out.println("result.pop(): " + str);
        }
        // System.out.println("infix: " + infix);

        // System.out.println("reverceInfix: " + reverceInfix);
        return reverceInfix;
    }

    private Stack<StringBuilder> reverceStack(Stack<StringBuilder> stack) {
        Stack<StringBuilder> reverceStack = new Stack<>();
        int size = stack.size();
        for (int i = 0; size > i; i++) {
            reverceStack.push(stack.pop());
        }
        return reverceStack;
    }

    private Double makeOperation(double a, double b, char operator) {
        return switch (operator) {
            case '+' ->
                a + b;
            case '-' ->
                a - b;
            case '*' ->
                a * b;
            case '/' ->
                a / b;
            case '^' ->
                0.00;
            default ->
                0.00;
        };
    }

    private byte getPriority(char c) {
        return switch (c) {
            case '$' ->
                0;
            case '(' ->
                1;
            case ')' ->
                1;
            case '+' ->
                2;
            case '-' ->
                2;
            case '*' ->
                3;
            case '/' ->
                3;
            case '^' ->
                3;
            default ->
                1;
        };
    }

    private boolean isEndChar(char c) {
        return END_CHAR == c;
    }

    private boolean isDelimiter(char c) {
        return " ".indexOf(c) != -1;
    }

    private boolean isOperator(char c) {
        return "+-*/^()".indexOf(c) != -1;
    }
}
