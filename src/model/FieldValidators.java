package model;

import java.util.regex.Pattern;

/**
 * Small, reusable validation helpers for UI text fields.
 * Kept in model package so UI classes can use it without extra wiring.
 */
public final class FieldValidators {
    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private FieldValidators() {}

    /** Returns true if email is blank (optional) or matches a simple pattern. */
    public static boolean isValidEmail(String email) {
        if (isBlank(email)) return true; // optional
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /** Returns true if phone is blank (optional) or looks like a valid phone number. */
    public static boolean isValidPhone(String phone) {
        if (isBlank(phone)) return true; // optional
        String t = phone.trim();
        if (t.matches(".*[A-Za-z].*")) return false;
        String digits = t.replaceAll("[^0-9]", "");
        return digits.length() >= 7 && digits.length() <= 15;
    }

    /** Convenience null/empty check used across validators. */
    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
