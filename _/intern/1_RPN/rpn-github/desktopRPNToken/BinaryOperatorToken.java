
public class BinaryOperatorToken extends OperatorToken {

    private final int priority;
    private final boolean leftAssociative;

    public BinaryOperatorToken(String literal, int priority, boolean leftAssociative) {
        super(literal);
        this.priority = priority;
        this.leftAssociative = leftAssociative;
    }

    public int getPriority() {
        return priority;
    }

    public boolean getLeftAssociative() {
        return leftAssociative;
    }
}
