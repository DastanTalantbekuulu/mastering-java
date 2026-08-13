public class Solution {
    private final MyStack stack;

    public Solution() {
        stack = new MyStack();
    }

    public Token evaluate(MyStack postfix)
            throws UnsupportedTokenException, InvalidSyntaxException {
        if (postfix.empty()) {
            throw new InvalidSyntaxException("Empty expression");
        }
        stack.clear();
        for (int i = 0; i < postfix.size(); i++) {
            Token token = postfix.get(i);
            System.out.println("solution "+token);
            if (token instanceof ConstantToken) {
                evaluateConstantToken((ConstantToken) token, stack);
            } else if (token instanceof BinaryOperatorToken) {
                evaluateBinaryOperator((BinaryOperatorToken) token, stack);
            } else if (token instanceof UnaryOperatorToken) {
                evaluateUnaryOperator((UnaryOperatorToken) token, stack);
            } else {
                throw new UnsupportedTokenException(postfix, token);
            }
        }
        if (stack.size() > 1) {
            throw new InvalidSyntaxException("Invalid Reverse Polish Notation");
        }

        return stack.pop();
    }

    private void evaluateConstantToken(ConstantToken token, MyStack stack) {
        stack.push(token);
    }

    private void evaluateBinaryOperator(BinaryOperatorToken token, MyStack stack)
            throws InvalidSyntaxException, UnsupportedTokenException {
        if (stack.size() < 2) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }
        ConstantToken operand2 = (ConstantToken) stack.pop();
        ConstantToken operand1 = (ConstantToken) stack.pop();
        double result = BinaryOperator.getAction(token, operand1, operand2);
        stack.push(new ConstantToken(result + "", result));
    }

    private void evaluateUnaryOperator(UnaryOperatorToken token, MyStack stack)
            throws InvalidSyntaxException, UnsupportedTokenException {
        if (stack.empty()) {
            throw new InvalidSyntaxException("Missing operands for " + token.toString());
        }

        ConstantToken operand = (ConstantToken) stack.pop();
        double result = UnaryOperator.getAction(token, operand);

        stack.push(new ConstantToken(result + "", result));
    }
}
