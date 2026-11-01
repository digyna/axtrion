package org.digyna.axtrion.system.config;

import org.digyna.axtrion.system.logger.LogManager;
import org.digyna.axtrion.system.logger.Logger;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class LoggerConfig {
    @Bean
    @ConditionalOnMissingBean
    public Logger logger() {
        return LogManager.getLogger();
    }
}
