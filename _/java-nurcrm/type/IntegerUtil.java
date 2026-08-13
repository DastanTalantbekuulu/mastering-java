package kg.nurtelecom.util.type;

public class IntegerUtil {

    /**
     * Тестовые случаи:<br/>
     * "123.0" → true (целое, неотрицательное)<br/>
     * "123" → true (целое, неотрицательное)<br/>
     * "0" → true (целое, неотрицательное)<br/>
     * "123.5" → false (не целое)<br/>
     * "abc" → false (не число)<br/>
     */
    public static boolean isInteger(String str) {
        try {
            // Если строка заканчивается на ".0", убираем его
            if (str.endsWith(".0")) {
                str = str.substring(0, str.length() - 2);
            }
            int i = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Тестовые случаи:<br/>
     * "123.0" → true (целое, неотрицательное)<br/>
     * "123" → true (целое, неотрицательное)<br/>
     * "0" → true (целое, неотрицательное)<br/>
     * "123.5" → false (не целое)<br/>
     * "-123" → false (отрицательное)<br/>
     * "abc" → false (не число)<br/>
     */
    public static boolean isZeroOrPositiveInteger(String str) {
        try {
            // Если строка заканчивается на ".0", убираем его
            if (str.endsWith(".0")) {
                str = str.substring(0, str.length() - 2);
            }
            int i = Integer.parseInt(str);
            return i >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static Integer parseInt(String str) {
        try {
            // Если строка заканчивается на ".0", убираем его
            if (str.endsWith(".0")) {
                str = str.substring(0, str.length() - 2);
            }
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw e;
        }
    }
}
