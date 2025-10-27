package org.digyna.axtrion.system.properties;

import org.digyna.axtrion.system.properties.app.PathProperties;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.charset.Charset;
import java.util.Locale;

@Data
@Builder(toBuilder = true)
@ConfigurationProperties(prefix = "app")
public class AppProperties {
    /**
     * --------------------------------------------------------------------------
     * Default Locale
     * --------------------------------------------------------------------------
     * <p>
     * The {@code Locale} roughly represents the language and region that your
     * visitor is viewing the site from.
     * </p>
     */
    private Locale defaultLocale;

    /**
     * --------------------------------------------------------------------------
     * Negotiate Locale
     * --------------------------------------------------------------------------
     * <p>
     * If {@code true}, the current {@code Request} object will automatically
     * determine the language to use based on the {@code Accept-Language} header.
     * </p>
     */
    private boolean negotiateLocale;

    /**
     * --------------------------------------------------------------------------
     * Application Timezone
     * --------------------------------------------------------------------------
     * <p>
     * The default timezone that will be used in your application to display
     * dates with the date helper, and can be retrieved through app_timezone().
     * </p>
     * <p>
     * The time zones used by java.util.TimeZone come from the IANA Time Zone Database:
     * </p>
     * @see <a href="https://www.iana.org/time-zones">IANA Time Zone Database</a>
     * <p>
     * To get all available zones in this Java version, use:
     * TimeZone.getAvailableIDs()
     * </p>
     */
    private String timezone;

    /**
     * --------------------------------------------------------------------------
     * Default Character Set
     * --------------------------------------------------------------------------
     * <p>
     * This determines the character set that will be used by default in various
     * methods that require a character set to be provided.
     * </p>
     * @see <a href="https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/nio/charset/Charset.html">
     *      java.nio.charset.Charset - list of supported charsets
     *      </a>
     */
    private Charset charset;

    /**
     * --------------------------------------------------------------------------
     * Debug Mode
     * --------------------------------------------------------------------------
     * <p>
     * If {@code true}, the application will run in debug mode, providing
     * additional information useful for development and troubleshooting.
     * </p>
     * <p>
     * This should be disabled in production environments to avoid exposing
     * sensitive information or detailed application errors to users.
     * </p>
     */
    private boolean debug;

    /**
     * --------------------------------------------------------------------------
     * Paths Configuration
     * --------------------------------------------------------------------------
     * <p>
     * Contains the filesystem paths used by the application.
     * </p>
     * <p>
     * This configuration groups directories that may require special handling,
     * such as write permissions, in a centralized location.
     * </p>
     */
    private PathProperties paths;
}
