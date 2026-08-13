package kg.nurtelecom.util.casting;

public class CastingException extends RuntimeException {
    public CastingException(String message) {
        super(message);
    }

    public CastingException(String message, Exception e) {
        super(message, e);
    }
}
