package org.digyna.axtrion.listener;

import org.digyna.axtrion.FrameworkVersion;
import org.digyna.axtrion.utils.DateUtils;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.Map;
import java.util.HashMap;

public class AxtrionBannerListener implements SpringApplicationRunListener {
    private static final String DEFAULT_ALWAYS = "ALWAYS";

    public AxtrionBannerListener(SpringApplication application, String[] args) {}

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        String version = FrameworkVersion.getVersion();
        String commit = FrameworkVersion.getCommit();
        String rawTime = FrameworkVersion.getTime();
        String timezoneStr = environment.getProperty("app.time-zone");
        String dateFormatStr = environment.getProperty("app.date-format");
        String formattedTime = DateUtils.formatInstant(rawTime, timezoneStr, dateFormatStr);

        Map<String, Object> axtrionProps = new HashMap<>();
        axtrionProps.put("axtrion.version", version);
        axtrionProps.put("axtrion.commit", commit);
        axtrionProps.put("axtrion.time", formattedTime);
        axtrionProps.put("spring.output.ansi.enabled", DEFAULT_ALWAYS);

        environment.getPropertySources().addFirst(
                new MapPropertySource("axtrionBannerProperties", axtrionProps)
        );
    }
}
