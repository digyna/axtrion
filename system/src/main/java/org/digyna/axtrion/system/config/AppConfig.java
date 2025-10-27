package org.digyna.axtrion.system.config;

public interface AppConfig {
    String getDefaultLocale();

    boolean isNegotiateLocale();

    String getTimezone();

    String getCharset();
}
