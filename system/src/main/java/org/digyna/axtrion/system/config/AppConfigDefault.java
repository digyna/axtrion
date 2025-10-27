package org.digyna.axtrion.system.config;

import lombok.RequiredArgsConstructor;
import org.digyna.axtrion.system.properties.AppProperties;

@RequiredArgsConstructor
public class AppConfigDefault implements AppConfig {
    private final AppProperties properties;

    @Override
    public String getDefaultLocale() {
        return properties.getDefaultLocale();
    }

    @Override
    public boolean isNegotiateLocale() {
        return properties.isNegotiateLocale();
    }

    @Override
    public String getTimezone() {
        return properties.getTimezone();
    }

    @Override
    public String getCharset() {
        return properties.getCharset();
    }
}
