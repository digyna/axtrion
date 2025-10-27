package org.digyna.axtrion.started;

import lombok.RequiredArgsConstructor;
import org.digyna.axtrion.FrameworkVersion;
import org.digyna.axtrion.properties.AppProperties;
import org.digyna.axtrion.utils.DateUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

@RequiredArgsConstructor
public class AxtrionStarted implements ApplicationListener<ApplicationStartedEvent> {
    private final AppProperties appProperties;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        double timeInSeconds = event.getTimeTaken().toMillis() / 1000.0;
        String buildTime = DateUtils.formatInstant(FrameworkVersion.getTime(), appProperties.getTimeZone(), appProperties.getDateFormat());

        String message = String.format(
                "[Axtrion] System Framework v%s [%s - Build: %s] is READY. Initialized successfully in %.2f seconds.",
                FrameworkVersion.getVersion(),
                FrameworkVersion.getCommit(),
                buildTime,
                timeInSeconds
        );

        System.out.println(message);
    }
}
