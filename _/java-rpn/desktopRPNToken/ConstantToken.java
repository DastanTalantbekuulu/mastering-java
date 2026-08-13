
public class ConstantToken extends Token {
    private final double value;

    public ConstantToken(String literal, double value) {
        super(literal);
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
