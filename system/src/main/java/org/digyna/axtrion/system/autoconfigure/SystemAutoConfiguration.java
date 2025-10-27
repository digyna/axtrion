package org.digyna.axtrion.system.autoconfigure;

import org.digyna.axtrion.system.common.SpringContext;
import org.digyna.axtrion.system.environment.EnvironmentLoader;
import org.digyna.axtrion.system.properties.AppProperties;
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
        SpringContext.class,
        EnvironmentLoader.class
})
public class SystemAutoConfiguration {}
