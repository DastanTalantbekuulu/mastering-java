public class UnsupportedTokenException extends Exception {

    public UnsupportedTokenException(String token) {
        super("Unsupported token " + token);

    }
}
