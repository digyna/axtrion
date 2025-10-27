package org.digyna.axtrion.system.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    /**
     * --------------------------------------------------------------------------
     * Default Locale
     * --------------------------------------------------------------------------
     *
     * The {@code Locale} roughly represents the language and region that your
     * visitor is viewing the site from.
     */
    private String defaultLocale = "en";

    /**
     * --------------------------------------------------------------------------
     * Negotiate Locale
     * --------------------------------------------------------------------------
     *
     * If {@code true}, the current {@code Request} object will automatically
     * determine the language to use based on the {@code Accept-Language} header.
     */
    private boolean negotiateLocale = false;

    /**
     * --------------------------------------------------------------------------
     * Application Timezone
     * --------------------------------------------------------------------------
     *
     * The default timezone that will be used in your application to display
     * dates with the date helper, and can be retrieved through app_timezone().
     *
     * The time zones used by java.util.TimeZone come from the IANA Time Zone Database:
     * @see <a href="https://www.iana.org/time-zones">IANA Time Zone Database</a>
     *
     * To get all available zones in this Java version, use:
     * TimeZone.getAvailableIDs()
     */
    private String timezone = "UTC";

    /**
     * --------------------------------------------------------------------------
     * Default Character Set
     * --------------------------------------------------------------------------
     *
     * This determines the character set that will be used by default in various
     * methods that require a character set to be provided.
     *
     * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/charset/Charset.html">
     *      java.nio.charset.Charset - list of supported charsets
     *      </a>
     */
    private String charset = "UTF-8";
}
