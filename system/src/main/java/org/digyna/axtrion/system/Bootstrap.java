package org.digyna.axtrion.system;

import org.digyna.axtrion.system.config.properties.AppProperties;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.digyna.axtrion.system.config.environment.AppEnvironment.PRODUCTION;
import static org.digyna.axtrion.system.config.environment.AppEnvironment.DEVELOPMENT;
import static org.digyna.axtrion.system.config.environment.AppEnvironment.TESTING;
import static org.digyna.axtrion.system.config.environment.AppEnvironment.environment;
import static org.digyna.axtrion.system.config.environment.AppEnvironment.register;
import static org.digyna.axtrion.system.utils.DateUtils.DEFAULT_DATE_FORMAT;

public class Bootstrap implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        ConfigurableEnvironment env = event.getEnvironment();
        injectProfileProperties(env);
        injectAppProperties(env);

        AppProperties appProperties = Binder.get(env).bind("app", AppProperties.class).get();
        register(appProperties, AppProperties.class);
    }

    /**
     * Centralizes application environment profiles and error reporting policies.
     */
    private void injectProfileProperties(ConfigurableEnvironment env) {
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

        environment = (env.getActiveProfiles().length > 0) ? env.getActiveProfiles()[0] : env.getDefaultProfiles()[0];

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
            case DEVELOPMENT:
                display_errors("always", env);
                break;
            case TESTING:
            case PRODUCTION:
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

    private void injectAppProperties(ConfigurableEnvironment env) {
        Map<String, Object> properties = new HashMap<>();

        boolean debug = TESTING.equals(environment) || DEVELOPMENT.equals(environment);
        properties.put("app.debug", debug);

        if (!env.containsProperty("app.paths.writable-directory")) {
            properties.put("app.paths.writable-directory", "./writable");
        }

        if (!env.containsProperty("app.default-locale")) {
            properties.put("app.default-locale", "en-US");
        }

        if (!env.containsProperty("app.time-zone")) {
            properties.put("app.time-zone", "UTC");
        }

        if (!env.containsProperty("app.date-format")) {
            properties.put("app.date-format", DEFAULT_DATE_FORMAT);
        }

        if (!env.containsProperty("app.charset")) {
            properties.put("app.charset", "UTF-8");
        }

        MapPropertySource propertySource = new MapPropertySource("envAppConfig", properties);
        env.getPropertySources().addLast(propertySource);
    }
}