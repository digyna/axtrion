package org.digyna.axtrion.system.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BooleanUtils {
    // ==========================================
    // Validation Methods (Null Checks)
    // ==========================================

    /**
     * Checks if the Boolean is null.
     *
     * @param value the Boolean to check
     * @return true if the Boolean is null
     */
    public static boolean isNull(Boolean value) {
        return value == null;
    }

    /**
     * Checks if the Boolean is NOT null.
     *
     * @param value the Boolean to check
     * @return true if the Boolean is not null
     */
    public static boolean nonNull(Boolean value) {
        return value != null;
    }

    // ==========================================
    // Core Boolean Checks (Null-Safe)
    // ==========================================

    /**
     * Checks if a Boolean value is true, handling null by returning false.
     *
     * @param value the Boolean to check
     * @return true if the Boolean is non-null and true
     */
    public static boolean isTrue(Boolean value) {
        return Boolean.TRUE.equals(value);
    }

    /**
     * Checks if a Boolean value is false, handling null by returning false.
     *
     * @param value the Boolean to check
     * @return true if the Boolean is non-null and false
     */
    public static boolean isFalse(Boolean value) {
        return Boolean.FALSE.equals(value);
    }

    /**
     * Checks if a Boolean value is NOT true (either null or false).
     *
     * @param value the Boolean to check
     * @return true if the Boolean is null or false
     */
    public static boolean isNotTrue(Boolean value) {
        return !isTrue(value);
    }

    /**
     * Checks if a Boolean value is NOT false (either null or true).
     *
     * @param value the Boolean to check
     * @return true if the Boolean is null or true
     */
    public static boolean isNotFalse(Boolean value) {
        return !isFalse(value);
    }

    // ==========================================
    // Conversion & Default Values
    // ==========================================

    /**
     * Converts a Boolean to a primitive boolean, handling null by returning a default value.
     *
     * @param value the Boolean to convert
     * @param defaultValue the value to return if null
     * @return the primitive boolean value
     */
    public static boolean toBooleanDefaultIfNull(Boolean value, boolean defaultValue) {
        return value == null ? defaultValue : value;
    }

    /**
     * Negates a Boolean value safely, preserving null if the input is null.
     *
     * @param value the Boolean to negate
     * @return the negated Boolean, or null if input was null
     */
    public static Boolean negate(Boolean value) {
        if (value == null) {
            return null;
        }
        return value ? Boolean.FALSE : Boolean.TRUE;
    }

    // ==========================================
    // Parsing & Conversions (String / Integer)
    // ==========================================

    /**
     * Converts a String to a primitive boolean.
     * Accepts "true", "yes", "y", and "1" as true (case-insensitive).
     *
     * @param value the String to parse
     * @return true if the String matches any true keywords, false otherwise
     */
    public static boolean toBoolean(String value) {
        if (value == null) {
            return false;
        }
        String trimmed = value.trim().toLowerCase();
        return "true".equals(trimmed) || "yes".equals(trimmed) || "1".equals(trimmed) || "y".equals(trimmed);
    }

    /**
     * Converts an Integer to a Boolean.
     * Commonly used for database flags where 1 is true and 0 is false.
     *
     * @param value the Integer to convert
     * @return true if the Integer is greater than 0, false if 0, and null if input is null
     */
    public static Boolean toBoolean(Integer value) {
        if (value == null) {
            return null;
        }
        return value > 0;
    }
}
