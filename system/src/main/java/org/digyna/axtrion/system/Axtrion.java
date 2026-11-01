package org.digyna.axtrion.system;

import org.digyna.axtrion.system.common.SpringContext;
import org.digyna.axtrion.system.config.LoggerConfig;
import org.digyna.axtrion.system.config.environment.AppEnvironmentLoader;
import org.digyna.axtrion.system.config.properties.AppProperties;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
        AppProperties.class
})
@Import({
        Starter.class,
        SpringContext.class,
        AppEnvironmentLoader.class,
        LoggerConfig.class
})
public class Axtrion {}
