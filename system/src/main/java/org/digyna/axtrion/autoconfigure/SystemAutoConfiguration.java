package org.digyna.axtrion.autoconfigure;

import org.digyna.axtrion.common.SpringContext;
import org.digyna.axtrion.environment.EnvironmentLoader;
import org.digyna.axtrion.started.AxtrionStarted;
import org.digyna.axtrion.properties.AppProperties;
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
        AxtrionStarted.class,
        SpringContext.class,
        EnvironmentLoader.class
})
public class SystemAutoConfiguration {}
