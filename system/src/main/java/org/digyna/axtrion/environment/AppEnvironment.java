package org.digyna.axtrion.environment;

import org.digyna.axtrion.properties.AppProperties;

public final class AppEnvironment {
    private static AppProperties properties;

    private AppEnvironment () {}

    public static void initialize(AppProperties properties) {
        AppEnvironment.properties = properties;
    }

    public static AppProperties get() {
        return AppEnvironment.properties;
    }
}
