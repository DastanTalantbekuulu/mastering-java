
public class UnsupportedTokenException extends Exception {

    public UnsupportedTokenException(MyStack expression, Token token) {
        super("Unsupported token " + token.getLiteral() + " of type " + expression);

    }
}
