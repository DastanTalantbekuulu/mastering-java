package kg.nurtelecom.util.parser;

import java.text.NumberFormat;

public class Parsers {
    private static NumberParser integers;
    private static BigDecimalParser bigDecimals;
    private static NumberParser numbers;
    private static BooleanParser booleans;

    private Parsers() {
    }

    public static NumberParser longs() {
        return integers();
    }

    public static NumberParser integers() {
        if (integers == null) {
            NumberFormat format = NumberFormat.getInstance();
            format.setParseIntegerOnly(true);
            integers = new NumberParser(format);
        }
        return integers;
    }

    public static BigDecimalParser bigDecimals() {
        if (bigDecimals == null) {
            bigDecimals = new BigDecimalParser(NumberFormat.getInstance());
        }
        return bigDecimals;
    }

    public static NumberParser numbers() {
        if (numbers == null) {
            numbers = new NumberParser(NumberFormat.getInstance());
        }
        return numbers;
    }

    public static BooleanParser booleans() {
        if (booleans == null) {
            booleans = new BooleanParser();
        }
        return booleans;
    }
}
