package org.digyna.axtrion.system.utils;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateUtils {
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public DateTimeFormatter getDateFormat(String dateFormat) {
        try {
            return DateTimeFormatter.ofPattern(dateFormat);
        } catch (Exception e) {
            return DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        }
    }

    public static String formatInstant(String rawTime, ZoneId timeZone, String pattern) {
        return formatInstant(rawTime, timeZone, getDateFormat(pattern));
    }

    public static String formatInstant(String rawTime, ZoneId timeZone, DateTimeFormatter formatter) {
        try {
            Instant instant = Instant.parse(rawTime);
            return instant.atZone(timeZone).format(formatter);
        } catch (DateTimeParseException e) {
            return rawTime;
        }
    }
}
