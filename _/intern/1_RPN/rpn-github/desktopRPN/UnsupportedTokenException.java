public class UnsupportedTokenException extends Exception {

    public UnsupportedTokenException(MyStack expression, String token) {
        super("Unsupported token " + token + " of type " + expression);

    }
}
