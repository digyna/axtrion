package org.digyna.axtrion.system;

import org.digyna.axtrion.system.common.FrameworkVersion;
import org.digyna.axtrion.system.config.properties.AppProperties;
import org.digyna.axtrion.system.utils.DateUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import lombok.RequiredArgsConstructor;

import java.time.Instant;

import static org.digyna.axtrion.system.config.environment.AppEnvironment.environment;

@RequiredArgsConstructor
public class Starter implements ApplicationListener<ApplicationStartedEvent> {
    private final AppProperties appProperties;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        double timeInSeconds = event.getTimeTaken().toMillis() / 1000.0;
        String buildTime = DateUtils.formatInstant(FrameworkVersion.getTime(), appProperties.getTimeZone(), appProperties.getDateFormat());
        String rawTime = Instant.ofEpochMilli(System.currentTimeMillis()).toString();
        String currentTimestamp = DateUtils.formatInstant(rawTime, appProperties.getTimeZone(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

        System.out.format(
                "%s  Axtrion Framework v%s [%s - Build: %s] is READY [%s]. Initialized successfully in %.2f seconds.",
                currentTimestamp,
                FrameworkVersion.getVersion(),
                FrameworkVersion.getCommit(),
                buildTime,
                environment,
                timeInSeconds
        );
    }
}
