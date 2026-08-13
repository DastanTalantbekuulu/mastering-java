
public class Converter {
    private final MyStack postfix;
    private final MyStack stack;

    public Converter() {
        postfix = new MyStack();
        stack = new MyStack();
    }

    public MyStack toPostfix(MyStack infix) throws UnsupportedTokenException {
        postfix.clear();
        stack.clear();

        for (int i = 0; i < infix.size(); i++) {
            String token = infix.get(i);
            if (Arithmetic.isNumber(token)) {
                processValue(token, postfix);
            } else if (Arithmetic.isBracket(token)) {
                processBracket(token, stack, postfix);
                // minus sign "−" −x unicode u2212
            } else if (token.equals("\u2212")) {
                stack.push(token);
            } else if (Arithmetic.isOperator(token)) {
                processOperator(token, postfix, stack);
            } else {
                throw new UnsupportedTokenException(token);
            }
        }
        while (!stack.empty()) {
            postfix.push(stack.pop());
        }
        return postfix;
    }

    private void processValue(String token, MyStack postfix) {
        postfix.push(token);
    }

    private void processBracket(String token, MyStack stack, MyStack postfix) {
        if (token.equals("(")) {
            stack.push(token);
        } else if (token.equals(")")) {
            while (!stack.empty() && !stack.peek().equals("(")) {
                postfix.push(stack.pop());
            }
            stack.pop();
        }
    }

    private void processOperator(String token, MyStack postfix, MyStack stack) {
        while (!stack.empty()) {
            String tokenFromStack = stack.peek();
            if (!Arithmetic.isOperator(tokenFromStack)) {
                break;
            }
            if (0 >= Arithmetic.getPriority(token) - Arithmetic.getPriority(tokenFromStack)) {
                postfix.push(stack.pop());
            } else {
                break;
            }
        }
        stack.push(token);
    }
}
