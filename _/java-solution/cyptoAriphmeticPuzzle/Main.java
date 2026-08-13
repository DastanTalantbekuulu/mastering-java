
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        // ===============================================================================================
        // OperatorService operatorService = new OperatorService();

        // OperatorModel[] operatorModels = operatorService.get();
        // for (OperatorModel model : operatorModels) {
        // System.out.print("Operator " + model.getOperator() + " ");
        // System.out.print("Priority " + model.getPriority() + " ");
        // System.out.print("Arity " + model.getArity() + " ");
        // System.out.println();
        // }
        // System.err.println(operatorService.getArity("+"));
        // System.err.println(operatorService.getPriority("+"));
        // System.err.println(operatorService.isOperator("+"));
        // ===============================================================================================
        // ===============================================================================================
        // System.out.println(ReversePolishNotation.convertToReversePolish("4+3 + (5 *
        // 2)"));
        // Puzzle puzzle = new Puzzle("DONALD", "GERALD", "ROBERT", true);
        // RPNModel model = new RPNModel("DONALD", "GERALD", "ROBERT", true);
        // System.out.println(model.getFirst());
        // System.out.println(model.getSecond());
        // System.out.println(model.getSumm());
        // System.out.println(model.isAdd());
        // System.out.println(Arrays.toString(model.getDigits()));
        // System.out.println(model.getLetters());
        // System.out.println(model.getLetterToDigit());
        // System.out.println(model.getUsedDigits());
        // solveCryptarithm(model.getDigits(), 0, model.getUsedDigits(),
        // model.getLetterToDigit(), model);
        // ===============================================================================================

        RevercePolishNotation rpn = new RevercePolishNotation(new StringBuilder("15/(7-(1+1))*3-(2+(1+1))"));

        Stack<StringBuilder> postfix = rpn.getPostfix();
        for (StringBuilder str : postfix) {
            System.out.print(str + " ");
        }

        System.out.println();
        Stack<StringBuilder> prefix = rpn.getPrefix();
        for (StringBuilder str : prefix) {
            System.out.print(str + " ");
        }

        // ===============================================================================================
        // String in = "15/(7-(1+1))*3-(2+(1+1))";
        // // String in =
        // "15/(7-(1+1))*3-(2+(1+1))*15/(7-(200+1))3-(2+(1+1))(15/(7-(1+1))*3-(2+(1+1))+15/(7-(1+1))*3-(2+(1+1)))";
        // Stack<String> infix = parseString(in);
        // Stack<String> prefix = getPrefix(infix);
        // Stack<String> postfix = getPostfix(infix);
        // System.out.println("infix: " + infix);
        // System.out.println("prefix: " + prefix);
        // System.out.println("postfix: " + postfix);
        // Stack<Character> stack = new Stack();
        // Stack<String> result = new Stack();
        // for (int i = in.length() - 1; i >= 0; i--) {
        // // System.out.println("infix: " + infix);
        // // System.out.println("infix.length(): " + infix.length());
        // char c = in.charAt(i);
        // // System.out.println("i: " + i);
        // // System.out.println("c: " + c);
        // if (c == ')' || c == '+' || c == '-' || c == '*' || c == '/') {
        // // System.out.println("sta");
        // stack.push(c);
        // // System.out.println("stack: " + stack);
        // } else if (47 < c && c < 58) {
        // // System.out.println("res");
        // result.push(String.valueOf(c));
        // // System.out.println("result: " + result);
        // } else if (c == '(') {
        // Character ch = stack.pop();
        // // result.push(ch);
        // do {
        // result.push(String.valueOf(ch));
        // if (stack.isEmpty()) {
        // break;
        // }
        // ch = stack.pop();
        // // System.out.println("sta->res");
        // // System.out.println("result: " + result);
        // } while (ch != ')');
        // }
        // // System.out.println("");
        // }
        // while (!stack.isEmpty()) {
        // result.push(String.valueOf(stack.pop()));
        // }
        // System.out.println("in: " + in);
        // System.out.println("infix: " + infix);
        // System.out.println("stack: " + stack);
        // System.out.println("result: " + result);
        // // " - * / 1 5 - 7 + 1 1 3 + 2 + 1 1";
        // ===============================================================================================
    }

    public static Stack<String> getPrefix(Stack<String> infix) {

        Stack<String> stack = new Stack();
        Stack<String> prefix = new Stack();
        int size = infix.size() - 1;

        // System.out.println("size: " + size);
        for (int i = size; i >= 0; i--) {
            String str = infix.get(i);
            char c = str.charAt(0);

            // System.out.println();
            // System.out.println("infix: " + infix);
            // System.out.print("iteration: " + i);
            // System.out.print(" char: " + c);
            // System.out.println(" string: " + str);
            if (str.length() == 1) {
                if (c == ')' || c == '+' || c == '-' || c == '*' || c == '/') {

                    stack.push(String.valueOf(c));

                    // System.out.println("stack <- " + String.valueOf(c));
                    // System.out.println("stack: " + stack);
                } else if (isNumber(c)) {

                    prefix.push(String.valueOf(c));

                    // System.out.println("stack: " + stack);
                    // System.out.println("prefix <- " + String.valueOf(c));
                } else if (c == '(') {
                    String s = stack.pop();
                    do {
                        prefix.push(String.valueOf(s));
                        if (stack.isEmpty()) {
                            break;
                        }
                        s = stack.pop();

                        // System.out.println("stack: " + stack);
                        // System.out.println("prefixWhile");
                    } while (!s.equals(")"));
                }

            } else {
                prefix.push(str);

                // System.out.println("stack: " + stack);
                // System.out.println("prefixElse <- " + String.valueOf(c));
            }
            // System.out.println("stack: " + stack);
            // System.out.println("prefix: " + prefix);

        }
        while (!stack.isEmpty()) {
            prefix.push(String.valueOf(stack.pop()));
        }
        // System.out.println("infix: " + infix);
        // System.out.println("stack: " + stack);
        // System.out.println("prefix: " + prefix);
        // Stack<String> reverceStack = new Stack<>();
        // size = prefix.size();
        // for (int i = 0; size > i; i++) {
        // reverceStack.push(prefix.pop());
        // }
        return reverceStack(prefix);
        // return prefix;
    }

    public static Stack<String> getPostfix(Stack<String> infix) {

        Stack<String> stack = new Stack();
        Stack<String> postfix = new Stack();
        int size = infix.size() - 1;

        System.out.println("size: " + size);
        for (int i = 0; i < size; i++) {
            String str = infix.get(i);
            char c = str.charAt(0);
            boolean b = false;

            System.out.println();
            System.out.println("infix: " + infix);
            System.out.print("iteration: " + i);
            System.out.print(" char: " + c);
            System.out.println(" string: " + str);
            if (str.length() == 1) {
                if (c == '(' || c == '+' || c == '-' || c == '*' || c == '/') {
                    if (c == '(') {
                        b = true;
                    }

                    stack.push(String.valueOf(c));

                    System.out.println("stack <- " + String.valueOf(c));
                    System.out.println("stack: " + stack);
                } else if (isNumber(c)) {

                    postfix.push(String.valueOf(c));

                    System.out.println("stack: " + stack);
                    System.out.println("postfix <- " + String.valueOf(c));
                } else if (c == ')') {
                    String s = stack.pop();
                    do {
                        postfix.push(String.valueOf(s));
                        if (stack.isEmpty()) {
                            break;
                        }
                        s = stack.pop();

                        System.out.println("stack: " + stack);
                        System.out.println("postfixWhile");
                    } while (!s.equals("("));
                    b = true;
                }

            } else {
                postfix.push(str);

                System.out.println("stack: " + stack);
                System.out.println("postfixElse <- " + str);
            }
            System.out.println("stack: " + stack);
            System.out.println("postfix: " + postfix);
            if (b) {
                while (!stack.isEmpty()) {
                    postfix.push(String.valueOf(stack.pop()));
                }
                b = false;
            }

        }
        while (!stack.isEmpty()) {
            postfix.push(String.valueOf(stack.pop()));
        }
        System.out.println("infix: " + infix);
        System.out.println("stack: " + stack);
        System.out.println("postfix: " + postfix);
        return postfix;
    }

    public static Stack<String> reverceStack(Stack<String> stack) {
        Stack<String> reverceStack = new Stack<>();
        int size = stack.size();
        for (int i = 0; size > i; i++) {
            reverceStack.push(stack.pop());
        }
        return reverceStack;
    }

    public static Stack<String> parseString(String str) {
        Stack<String> stack = new Stack<>();
        String digit = "";
        for (int i = 0; i < str.length(); i++) {
            while (isNumber(str.charAt(i))) {
                digit = digit.concat(String.valueOf(str.charAt(i++)));
                if (isOperator(str.charAt(i))) {
                    stack.push(digit);
                    digit = "";
                    break;
                }
            }
            if (isOperator(str.charAt(i))) {
                stack.push(String.valueOf(str.charAt(i)));
            }
        }
        return stack;
    }

    public static boolean isNumber(char c) {
        // return c - '0' >= 0 && c - '0' <= 9;
        return 47 < c && c < 58;
    }

    public static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '(' || c == ')';
    }
}
