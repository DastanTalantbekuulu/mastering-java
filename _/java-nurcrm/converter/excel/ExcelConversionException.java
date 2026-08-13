package kg.nurtelecom.util.converter.excel;

public class ExcelConversionException extends RuntimeException {
    public ExcelConversionException(String message) {
        super(message);
    }

    public ExcelConversionException(String message, Exception e) {
        super(message, e);
    }
}
