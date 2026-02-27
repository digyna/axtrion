package org.digyna.axtrion.system.autoconfigure;

import org.digyna.axtrion.system.common.SpringContext;
import org.digyna.axtrion.system.config.AppConfig;
import org.digyna.axtrion.system.config.AppConfigDefault;
import org.digyna.axtrion.system.properties.AppProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties({AppProperties.class})
public class SystemAutoConfiguration {
    @Bean
    public SpringContext springContext() {
        return new SpringContext();
    }

    @Bean
    @ConditionalOnMissingBean(AppConfig.class)
    public AppConfig appConfig(AppProperties appProperties) {
        return new AppConfigDefault(appProperties);
    }
}
