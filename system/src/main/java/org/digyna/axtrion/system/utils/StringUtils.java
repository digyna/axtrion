package org.digyna.axtrion.system.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Set;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.function.IntFunction;

@NoArgsConstructor
public class StringUtils {
    private static final Set<String> TRUE_VALUES = Set.of("true", "1", "y", "yes", "t");

    public static String notNull(String str)
    {
        return StringUtils.notNull(str, "");
    }

    public static String notNull(String str, String seq)
    {
        if (str != null && !StringUtils.isBlank(str))
            return str;
        else
            return seq;
    }

    public static boolean isEmpty(final String str)
    {
        return (str == null || "".equals(str));
    }

    public static boolean isBlank(final String str)
    {
        return (str == null || "".equals(str.trim()));
    }

    public static boolean isNumeric(String number)
    {
        number = number.replace(">=",">").replace("<=","<");

        if (number.length() == 0)
            return false;
        if (number.toUpperCase().indexOf("NAN") > -1)
            return false;
        if (number.toUpperCase().indexOf("INFINITY") > -1)
            return false;
        if (number.substring(0, 1).equals(">"))
            number = number.substring(1);
        if (number.length() == 0)
            return false;
        if (number.substring(0, 1).equals("<"))
            number = number.substring(1);
        try
        {
            Double.parseDouble(number);
        }
        catch (NumberFormatException nfe)
        {
            return false;
        }
        return true;
    }

    public static String md5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            System.out.println("Error generando hash MD5 " + e.getMessage());
        }

        return null;
    }

    public static boolean parseBoolean(String str) {
        if (StringUtils.isBlank(str)) return false;
        str = str.trim().toLowerCase();

        return TRUE_VALUES.contains(str);
    }

    public static Byte parseByte(String str, Byte defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Byte.parseByte(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Short parseShort(String str, Short defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Short.parseShort(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Integer parseInt(String str, Integer defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Long parseLong(String str, Long defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Float parseFloat(String str, Float defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Float.parseFloat(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static Double parseDouble(String str, Double defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Double.parseDouble(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static BigDecimal parseBigDecimal(String str, BigDecimal defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return new BigDecimal(str.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public static byte[] parseBytes(String str, byte[] defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        return str.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * Parsea un String a {@link java.sql.Date}.
     *
     * <p>Formato esperado:</p>
     * <pre>yyyy-MM-dd</pre>
     *
     * Ejemplo: 2026-03-28
     *
     * @param str valor a convertir
     * @param defaultValue valor retornado si str es null o inválido
     * @return Date parseada o defaultValue
     */
    public static Date parseDate(String str, Date defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Date.valueOf(str.trim());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * Parsea un String a {@link java.sql.Time}.
     *
     * Formato esperado: HH:mm:ss
     *
     * Ejemplo: 14:30:00
     */
    public static Time parseTime(String str, Time defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Time.valueOf(str.trim());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * Parsea un String a {@link java.sql.Timestamp}.
     *
     * Formato esperado: yyyy-MM-dd HH:mm:ss
     *
     * Ejemplo: 2026-03-28 14:30:00
     */
    public static Timestamp parseTimestamp(String str, Timestamp defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            return Timestamp.valueOf(str.trim());
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * Parsea un String a {@link TimeZone}.
     *
     * Ejemplos de IDs válidos:
     * UTC
     * GMT
     * America/Mexico_City
     *
     * @param str ID de la zona horaria
     * @param defaultValue valor retornado si es null o inválido
     * @return TimeZone válida o defaultValue
     */
    public static TimeZone parseTimeZone(String str, TimeZone defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        str = str.trim();

        if (Arrays.asList(TimeZone.getAvailableIDs()).contains(str)) {
            return TimeZone.getTimeZone(str);
        }

        return defaultValue;
    }

    public static URI parseURI(String str, URI defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        str = str.trim();

        if (!str.contains("://")) {
            str = "http://" + str;
        }

        try {
            return URI.create(str);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static <T> T[] parseArray(String str, String delimiter, Function<String, T> converse, IntFunction<T[]> generator) {
        try {
            return Arrays.stream(str.split(delimiter))
                    .map(String::trim)
                    .map(converse)
                    .toArray(generator);
        } catch (Exception e) {
            return null;
        }
    }

    public static JsonNode parseJsonNode(String str, JsonNode defaultValue) {
        if (StringUtils.isBlank(str)) return defaultValue;

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(str.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
