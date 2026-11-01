package org.digyna.axtrion.system.config.properties;

import org.digyna.axtrion.system.config.properties.logger.HandlerProperties;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@Builder(toBuilder = true)
@ConfigurationProperties(prefix = "logger")
public class LoggerProperties {
    private List<Integer> threshold;
    private String dateFormat;
    private Map<String, HandlerProperties> handlers;
}
