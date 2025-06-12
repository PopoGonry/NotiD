package com.popogonry.notid.cli;

public class ValidationCheck {

    public static boolean isNumeric(String input) {
        if (input == null || input.isEmpty()) return false;
        for (char c : input.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    public static boolean intSelectCheck(int min, int max, String value) {
        if(!isNumeric(value)) return false;
        int intValue = Integer.parseInt(value);
        return min <= intValue && intValue <= max;
    }

    public static boolean isValidBirthdate(String input) {
        return input != null && input.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }
    public static boolean isValidDateAndTime(String input) {
        return input != null && input.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$");
    }

    public static boolean isValidPhoneNumber(String input) {
        return input != null && input.matches("^\\d{3}-\\d{4}-\\d{4}$");
    }
}
