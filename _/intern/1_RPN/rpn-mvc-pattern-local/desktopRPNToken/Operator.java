
public  class Operator {
    public static final OperatorToken[] operatorTokens = new OperatorToken[]{
            new BinaryOperatorToken("+", 2, true),
            //  hyphen-minus "-" x - y unicode u002D ASCII code 45
            new BinaryOperatorToken("-", 2, true),
            new BinaryOperatorToken("*", 3, true),
            new BinaryOperatorToken("×", 3, true),
            new BinaryOperatorToken("/", 3, true),
            new BinaryOperatorToken("÷", 3, true),
            new BinaryOperatorToken("^", 4, false),
            // minus sign "−" −x unicode u2212
            new UnaryOperatorToken("\u2212"),
            new UnaryOperatorToken("sqrt"),
            new UnaryOperatorToken("√")
    };
}
