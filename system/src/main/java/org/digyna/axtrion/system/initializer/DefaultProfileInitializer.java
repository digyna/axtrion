package org.digyna.axtrion.system.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

public class DefaultProfileInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        ConfigurableEnvironment env = context.getEnvironment();
        /*
         *---------------------------------------------------------------
         * APPLICATION ENVIRONMENT
         *---------------------------------------------------------------
         *
         * You can load different configurations depending on your
         * current environment. Setting the environment also influences
         * things like logging and error reporting.
         *
         * This can be set to anything, but default usage is:
         *
         *     development
         *     testing
         *     production
         *
         * NOTE: If you change these, also change the display_errors() code below
         */
        if (!env.containsProperty("spring.profiles.default")) {
            env.setDefaultProfiles("development");
        }

        String environment = (env.getActiveProfiles().length > 0) ? env.getActiveProfiles()[0] : env.getDefaultProfiles()[0];

        /*
         *---------------------------------------------------------------
         * ERROR REPORTING
         *---------------------------------------------------------------
         *
         * Different environments will require different levels of error reporting.
         * By default development will show errors but testing and live will hide them.
         */
        switch (environment)
        {
            case "development":
                display_errors("always", env);
                break;
            case "testing":
            case "production":
                display_errors("never", env);
                break;
            default:
                throw new IllegalStateException("The application environment is not set correctly.");
        }
    }

    private void display_errors(String value, ConfigurableEnvironment env) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("server.error.include-message", value);

        MapPropertySource propertySource = new MapPropertySource("envErrorConfig", properties);
        env.getPropertySources().addFirst(propertySource);
    }
}
