package org.digyna.axtrion.system.processors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.util.Map;
import java.util.Properties;

import static org.digyna.axtrion.system.config.environment.AppEnvironment.PRODUCTION;

public class AppPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) {
        String activeEnv = env.getProperty("spring.profiles.active");
        String defaultEnv = env.getProperty("spring.profiles.default");

        boolean isProduction = PRODUCTION.equalsIgnoreCase(activeEnv) || PRODUCTION.equalsIgnoreCase(defaultEnv);

        if (!isProduction) {
            try {
                ClassPathResource resource = new ClassPathResource("application.properties");
                if (resource.exists()) {
                    Properties props = PropertiesLoaderUtils.loadProperties(resource);

                    String fileDefault = props.getProperty("spring.profiles.default");
                    String fileActive = props.getProperty("spring.profiles.active");

                    isProduction = PRODUCTION.equalsIgnoreCase(fileDefault) || PRODUCTION.equalsIgnoreCase(fileActive);
                }
            } catch (Exception ignored) {}
        }

        if (isProduction) {
            Map<String, Object> axtrionProdProps = Map.of(
                    "logging.level.root", "WARN",
                    "spring.main.banner-mode", "off"
            );

            MapPropertySource propertySource = new MapPropertySource("axtrionProductionProperties", axtrionProdProps);
            env.getPropertySources().addFirst(propertySource);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
