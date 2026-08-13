
public class Token {

    private final String literal;

    protected Token(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
    public String toString() {
        return this.getLiteral();
    }
}
