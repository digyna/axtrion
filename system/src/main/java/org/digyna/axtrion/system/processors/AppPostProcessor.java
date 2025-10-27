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

public class AppPostProcessor implements EnvironmentPostProcessor, Ordered {

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String activeEnv = environment.getProperty("spring.profiles.active");
        String defaultEnv = environment.getProperty("spring.profiles.default");

        boolean isProduction = "production".equalsIgnoreCase(activeEnv) || "production".equalsIgnoreCase(defaultEnv);

        if (!isProduction) {
            try {
                ClassPathResource resource = new ClassPathResource("application.properties");
                if (resource.exists()) {
                    Properties props = PropertiesLoaderUtils.loadProperties(resource);

                    String fileDefault = props.getProperty("spring.profiles.default");
                    String fileActive = props.getProperty("spring.profiles.active");

                    isProduction = "production".equalsIgnoreCase(fileDefault)
                            || "production".equalsIgnoreCase(fileActive);
                }
            } catch (Exception ignored) {}
        }

        if (isProduction) {
            Map<String, Object> axtrionProdProps = Map.of(
                    "logging.level.root", "WARN",
                    "spring.main.banner-mode", "off"
            );

            environment.getPropertySources().addFirst(
                    new MapPropertySource("axtrionProductionProperties", axtrionProdProps)
            );
        }
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }
}
