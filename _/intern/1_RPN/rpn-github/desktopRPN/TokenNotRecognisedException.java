
public class TokenNotRecognisedException extends Exception {

    public TokenNotRecognisedException(String inputString, int position) {
        super("Unrecognised token at char " + position + " in \"" + inputString + "\"");
    }
}
