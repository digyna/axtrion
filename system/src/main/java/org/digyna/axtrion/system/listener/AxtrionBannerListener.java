package org.digyna.axtrion.system.listener;

import org.digyna.axtrion.system.FrameworkVersion;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.HashMap;

import static org.digyna.axtrion.system.utils.DateUtils.DEFAULT_DATE_FORMAT;

public class AxtrionBannerListener implements SpringApplicationRunListener {
    private static final String DEFAULT_TIME_ZONE = "UTC";
    private static final String DEFAULT_UNKNOWN = "unknown";
    private static final String DEFAULT_ALWAYS = "ALWAYS";

    public AxtrionBannerListener(SpringApplication application, String[] args) {
    }

    @Override
    public void environmentPrepared(ConfigurableBootstrapContext bootstrapContext, ConfigurableEnvironment environment) {
        String version = FrameworkVersion.getVersion();
        String commit = FrameworkVersion.getCommit();
        String rawTime = FrameworkVersion.getTime();

        String timezoneStr = environment.getProperty("app.time-zone", DEFAULT_TIME_ZONE);
        String dateFormatStr = environment.getProperty("app.date-format", DEFAULT_DATE_FORMAT);

        String formattedTime;
        try {
            if (rawTime != null && !rawTime.equals(DEFAULT_UNKNOWN)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormatStr);
                ZoneId zoneId = ZoneId.of(timezoneStr);
                Instant instant = Instant.parse(rawTime);
                formattedTime = instant.atZone(zoneId).format(formatter);
            } else {
                formattedTime = DEFAULT_UNKNOWN;
            }
        } catch (Exception e) {
            formattedTime = DEFAULT_UNKNOWN;
        }

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
