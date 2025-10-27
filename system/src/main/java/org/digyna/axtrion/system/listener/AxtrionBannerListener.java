package org.digyna.axtrion.system.listener;

import org.digyna.axtrion.system.common.FrameworkVersion;
import org.digyna.axtrion.system.utils.DateUtils;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

import static org.digyna.axtrion.system.config.environment.AppEnvironment.environment;

public class AxtrionBannerListener implements SpringApplicationRunListener {
    private static final String DEFAULT_ALWAYS = "ALWAYS";

    public AxtrionBannerListener(SpringApplication application, String[] args) {}

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment env) {
        String version = FrameworkVersion.getVersion();
        String commit = FrameworkVersion.getCommit();
        String rawTime = FrameworkVersion.getTime();
        String timezoneStr = env.getProperty("app.time-zone");
        String dateFormatStr = env.getProperty("app.date-format");
        String formattedTime = DateUtils.formatInstant(rawTime, timezoneStr, dateFormatStr);

        Map<String, Object> axtrionProps = new HashMap<>();
        axtrionProps.put("axtrion.version", version);
        axtrionProps.put("axtrion.commit", commit);
        axtrionProps.put("axtrion.time", formattedTime);
        axtrionProps.put("axtrion.environment", environment);
        axtrionProps.put("spring.output.ansi.enabled", DEFAULT_ALWAYS);

        MapPropertySource propertySource = new MapPropertySource("envAxtrion", axtrionProps);
        env.getPropertySources().addFirst(propertySource);
    }
}
