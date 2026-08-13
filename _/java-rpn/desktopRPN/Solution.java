public class Solution {
    private final MyStack stack;

    public Solution() {
        stack = new MyStack();
    }

    public String evaluate(MyStack postfix)
            throws InvalidSyntaxException, UnsupportedTokenException {
        if (postfix.empty()) {
            throw new InvalidSyntaxException("Empty expression");
        }
        stack.clear();
        for (int i = 0; i < postfix.size(); i++) {
            String token = postfix.get(i);
            if (Arithmetic.isNumber(token)) {
                evaluateConstantToken(token, stack);
            } else if (Arithmetic.isUnaryOperator(token)) {
                evaluateUnaryOperator(token, stack);
            } else if (Arithmetic.isOperator(token)) {
                evaluateBinaryOperator(token, stack);
            } else {
                throw new UnsupportedTokenException(postfix, token);
            }
        }
        if (stack.size() > 1) {
            throw new InvalidSyntaxException("Invalid Reverse Polish Notation");
        }

        return stack.pop();
    }

    private void evaluateConstantToken(String token, MyStack stack) {
        stack.push(token);
    }

    private void evaluateBinaryOperator(String token, MyStack stack)
            throws InvalidSyntaxException, UnsupportedTokenException {
        if (stack.size() < 2) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }
        String operand2 = stack.pop();
        String operand1 = stack.pop();
        double result = Arithmetic.makeOperation(operand1, operand2, token);
        stack.push(result + "");
    }

    private void evaluateUnaryOperator(String token, MyStack stack)
            throws InvalidSyntaxException, UnsupportedTokenException {
        if (stack.empty()) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }

        String operand = stack.pop();
        double result = Arithmetic.makeOperation(operand, token);

        stack.push(result + "");
    }
}
