package org.digyna.axtrion.system.config.properties.logger;

import org.digyna.axtrion.system.logger.LogLevel;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class HandlerProperties {
    private List<LogLevel> handles;
    private String fileExtension;
    private String filePermissions;
    private String path;
}
