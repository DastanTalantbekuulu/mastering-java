package kg.nurtelecom.registration.api.util;

public class NumberUtil {

    public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return false;
        }
        return true;
    }
}
