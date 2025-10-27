package org.digyna.axtrion.utils;

import lombok.experimental.UtilityClass;

import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class DateUtils {
    public static final String DEFAULT_TIME_ZONE = "UTC";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_UNKNOWN = "unknown";

    public static ZoneId getTimeZone(String timeZone) {
        try {
            return ZoneId.of(timeZone);
        } catch (DateTimeException e) {
            return ZoneId.of(DEFAULT_TIME_ZONE);
        }
    }

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

    public static String formatInstant(String rawTime, String timeZone, String pattern) {
        return formatInstant(rawTime, getTimeZone(timeZone), getDateFormat(pattern)
        );
    }

    public static String formatInstant(String rawTime, ZoneId timeZone, DateTimeFormatter formatter) {
        if (StringUtils.isBlank(rawTime) || rawTime.equals(DEFAULT_UNKNOWN)) {
            return DEFAULT_UNKNOWN;
        }

        try {
            Instant instant = Instant.parse(rawTime);
            return instant.atZone(timeZone).format(formatter);
        } catch (DateTimeParseException e) {
            return rawTime;
        }
    }
}
