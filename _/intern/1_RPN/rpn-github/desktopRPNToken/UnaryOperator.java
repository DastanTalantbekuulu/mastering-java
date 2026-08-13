public class UnaryOperator extends Operator {
    public static double getAction(OperatorToken token, ConstantToken operand)
            throws UnsupportedTokenException {
        switch (token.getLiteral()) {
            case "\u2212":
                return -Math.abs(operand.getValue());
            case "sqrt":
                return Math.sqrt(operand.getValue());
            case "√":
                return Math.sqrt(operand.getValue());
        }
        throw new UnsupportedTokenException(null, token);
    }
}
