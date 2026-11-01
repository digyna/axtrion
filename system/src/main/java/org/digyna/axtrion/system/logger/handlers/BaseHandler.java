package org.digyna.axtrion.system.logger.handlers;

import org.digyna.axtrion.system.logger.LogLevel;
import org.digyna.axtrion.system.config.properties.logger.HandlerProperties;
import org.digyna.axtrion.system.utils.StringUtils;

import java.util.List;

import static org.digyna.axtrion.system.config.environment.AppEnvironment.isProduction;

public abstract class BaseHandler implements HandlerInterface {
    /**
     * Handles
     *
     * @var List<LogLevel>
     */
    protected List<LogLevel>  handles;

    /**
     * Date format for logging
     *
     * @var string
     */
    protected String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    public BaseHandler(HandlerProperties config) {
        if(config != null) {
            this.handles = config.getHandles();
        }
    }

    @Override
    public abstract boolean handle(LogLevel level, String message);

    @Override
    public boolean canHandle(LogLevel level) {
        return handles.contains(level);
    }

    @Override
    public HandlerInterface setDateFormat(String format) {
        this.dateFormat = format;

        return this;
    }

    protected void printToConsole(LogLevel level, String message) {
        if (isProduction() || StringUtils.isBlank(message)) {
            return;
        }

        boolean isErrorStream = switch (level) {
            case EMERGENCY, ALERT, CRITICAL, ERROR -> true;
            default -> false;
        };

        if (isErrorStream) {
            System.err.println(message.stripTrailing());
        } else {
            System.out.println(message.stripTrailing());
        }
    }
}
