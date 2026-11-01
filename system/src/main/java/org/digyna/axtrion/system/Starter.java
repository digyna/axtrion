package org.digyna.axtrion.system;

import org.digyna.axtrion.system.common.FrameworkVersion;
import org.digyna.axtrion.system.config.properties.AppProperties;
import org.digyna.axtrion.system.logger.Logger;
import org.digyna.axtrion.system.utils.DateUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import lombok.RequiredArgsConstructor;

import static org.digyna.axtrion.system.config.environment.AppEnvironment.environment;

@RequiredArgsConstructor
public class Starter implements ApplicationListener<ApplicationStartedEvent> {
    private final AppProperties appProperties;
    private final Logger logger;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        double timeInSeconds = event.getTimeTaken().toMillis() / 1000.0;
        String buildTime = DateUtils.formatInstant(FrameworkVersion.getTime(), appProperties.getTimeZone(), appProperties.getDateFormat());

        String banner = String.format(
                "Axtrion Framework v%s [%s - Build: %s] is READY [%s]. Initialized successfully in %.2f seconds.",
                FrameworkVersion.getVersion(),
                FrameworkVersion.getCommit(),
                buildTime,
                environment,
                timeInSeconds
        );
        logger.notice(banner);
    }
}
