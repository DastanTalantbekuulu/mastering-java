package kg.nurtelecom.registration.common.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StaffAuthUtils {

    public static String generateUsername(String fullName) {
        return fullName.replaceAll(" ", ".").toLowerCase() + RandomStringUtils.randomNumeric(3);
    }

    public static String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(8);
    }
}
