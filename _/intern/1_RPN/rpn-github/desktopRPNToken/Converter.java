
public class Converter {

    private final MyStack postfix;
    private final MyStack stack;

    public Converter() {
        postfix = new MyStack();
        stack = new MyStack();
    }

    public MyStack toPostfix(MyStack infix)
            throws UnsupportedTokenException, InvalidSyntaxException {
        postfix.clear();
        stack.clear();

        for (int i = 0; i < infix.size(); i++) {
            Token token = infix.get(i);
            if (token instanceof ConstantToken) {
                processConstantToken(token, postfix, stack);
            } else if (token instanceof BinaryOperatorToken) {
                processBinaryOperator((BinaryOperatorToken) token, postfix, stack);
            } else if (token instanceof UnaryOperatorToken) {
                stack.push(token);
            } else if (token instanceof BracketToken) {
                BracketToken bracketToken = (BracketToken) token;
                if (bracketToken.getIsOpenBracket()) {
                    stack.push(bracketToken);
                } else {
                    processCloseBracket(bracketToken, postfix, stack);
                }
            } else {
                throw new UnsupportedTokenException(infix, token);
            }
        }

        while (!stack.empty()) {
            Token operator = stack.pop();
            if (operator instanceof BinaryOperatorToken) {
                postfix.push(operator);
            } else {
                throw new InvalidSyntaxException("Expected binary operator but found " + operator.toString());
            }
        }
        return postfix;
    }

    private void processConstantToken(Token token, MyStack postfix, MyStack stack) {
        postfix.push(token);
        while (!stack.empty() &&
                (stack.peek() instanceof UnaryOperatorToken)) {
            postfix.push(stack.pop());
        }
    }

    private void processBinaryOperator(BinaryOperatorToken token, MyStack postfix, MyStack stack) {
        while (!stack.empty()) {
            Token operator = stack.peek();
            if (!(operator instanceof BinaryOperatorToken)) {
                break;
            }
            BinaryOperatorToken tokenFromStack = (BinaryOperatorToken) operator;
            int diff = token.getPriority() - tokenFromStack.getPriority();

            if (diff < 0 || (diff == 0 && token.getLeftAssociative())) {
                postfix.push(stack.pop());
            } else {
                break;
            }
        }
        stack.push(token);
    }

    private void processCloseBracket(BracketToken token, MyStack postfix,MyStack stack) throws InvalidSyntaxException {
        Token operator = null;
        while (!stack.empty()) {
            operator = stack.pop();
            if (operator instanceof BracketToken) {
                break;
            }
            postfix.push(operator);
        }
        if (!(operator instanceof BracketToken)) {
            throw new InvalidSyntaxException("No matching open bracket found");
        }
        while (!stack.empty() &&
                (stack.peek() instanceof UnaryOperatorToken)) {
            postfix.push(stack.pop());
        }
    }
}
