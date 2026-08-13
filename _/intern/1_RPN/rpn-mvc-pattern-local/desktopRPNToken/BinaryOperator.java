public class BinaryOperator extends Operator  {

    public static double getAction(OperatorToken token, ConstantToken operand1, ConstantToken operand2)
            throws UnsupportedTokenException {
        switch (token.getLiteral()) {
            case "+":
                return operand1.getValue() + operand2.getValue();
            case "-":
                return operand1.getValue() - operand2.getValue();
            case "*":
                return operand1.getValue() * operand2.getValue();
            case "×":
                return operand1.getValue() * operand2.getValue();
            case "/":
                return operand1.getValue() / operand2.getValue();
            case "÷":
                return operand1.getValue() / operand2.getValue();
            case "^":
                return Math.pow(operand1.getValue(), operand2.getValue());
        }
        throw new UnsupportedTokenException(null, token);
    }
}
