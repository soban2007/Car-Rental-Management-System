package util;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isValidPhone(String phone) {
        // Simple numeric sequence check matching length standard 7-15 digits
        return Pattern.matches("^[0-9]{7,15}$", phone);
    }

    public static boolean isPositiveDouble(String value) {
        try {
            double d = Double.parseDouble(value);
            return d > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isPositiveInteger(String value) {
        try {
            int i = Integer.parseInt(value);
            return i > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}