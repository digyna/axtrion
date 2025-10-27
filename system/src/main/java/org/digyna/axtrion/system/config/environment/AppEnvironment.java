package org.digyna.axtrion.system.config.environment;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public final class AppEnvironment {
    public static final String PRODUCTION = "production";
    public static final String DEVELOPMENT = "development";
    public static final String TESTING = "testing";
    public static String environment;
    private static final Map<Class<?>, Object> registry = new ConcurrentHashMap<>();

    /**
     * Checks if the current environment is development.
     */
    public static boolean isDevelopment() {
        return DEVELOPMENT.equalsIgnoreCase(environment);
    }

    /**
     * Checks if the current environment is testing.
     */
    public static boolean isTesting() {
        return TESTING.equalsIgnoreCase(environment);
    }

    /**
     * Checks if the current environment is production.
     */
    public static boolean isProduction() {
        return PRODUCTION.equalsIgnoreCase(environment);
    }

    /**
     * Registers any property configuration class into the framework's static context.
     */
    public static <T> void register(T propertiesInstance, Class<T> type) {
        if (propertiesInstance != null) {
            registry.put(type, propertiesInstance);
        }
    }

    /**
     * Retrieves any registered configuration strictly typed, avoiding manual casting.
     */
    public static <T> T config(Class<T> type) {
        Object config = registry.get(type);
        if (config == null) {
            throw new IllegalStateException("Configuration of type " + type.getName() + " has not been initialized yet.");
        }
        return type.cast(config);
    }
}
