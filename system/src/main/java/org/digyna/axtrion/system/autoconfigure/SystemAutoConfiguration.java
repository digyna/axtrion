package org.digyna.axtrion.system.autoconfigure;

import org.digyna.axtrion.system.config.AppConfig;
import org.digyna.axtrion.system.config.AppConfigDefault;
import org.digyna.axtrion.system.config.DatabaseConfig;
import org.digyna.axtrion.system.config.DatabaseConfigDefault;
import org.digyna.axtrion.system.properties.AppProperties;
import org.digyna.axtrion.system.properties.DatabaseProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties({AppProperties.class, DatabaseProperties.class})
public class SystemAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(AppConfig.class)
    public AppConfig appConfig(AppProperties properties) {
        return new AppConfigDefault(properties);
    }

    @Bean
    @ConditionalOnMissingBean(DatabaseConfig.class)
    public DatabaseConfig databaseConfig(DatabaseProperties properties) {
        return new DatabaseConfigDefault(properties);
    }
}
