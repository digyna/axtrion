package org.digyna.axtrion.environment;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.digyna.axtrion.properties.AppProperties;

@RequiredArgsConstructor
public class EnvironmentLoader {
    private final AppProperties appProperties;

    @PostConstruct
    public void load() {
        AppEnvironment.initialize(appProperties);
    }
}
