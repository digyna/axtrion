package org.digyna.axtrion.system.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.digyna.axtrion.system.utils.DateUtils.DEFAULT_DATE_FORMAT;

public class DefaultAppInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        Map<String, Object> properties = new HashMap<>();
        ConfigurableEnvironment env = context.getEnvironment();
        String environment = (env.getActiveProfiles().length > 0) ? env.getActiveProfiles()[0] : env.getDefaultProfiles()[0];

        boolean debug = "testing".equals(environment) || "development".equals(environment);
        properties.put("app.debug", debug);

        String writablePath = "production".equalsIgnoreCase(environment) ? "../writable" : "./writable";
        properties.put("app.paths.writable-directory", writablePath);

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
