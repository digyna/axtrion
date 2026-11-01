package org.digyna.axtrion.system.logger.bridge;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.digyna.axtrion.system.logger.LogLevel;
import org.digyna.axtrion.system.logger.Logger;

public class LogbackAppender extends AppenderBase<ILoggingEvent> {

    private final Logger logger;

    public LogbackAppender(Logger logger) {
        this.logger = logger;
    }

    @Override
    protected void append(ILoggingEvent event) {
        if (event.getLoggerName().startsWith("org.digyna.axtrion.system.logger")) {
            return;
        }

        LogLevel level = mapLevel(event.getLevel().toString());
        String formattedMessage = String.format("[%s] %s", event.getLoggerName(), event.getFormattedMessage());

        logger.log(level, formattedMessage);
    }

    private LogLevel mapLevel(String logbackLevel) {
        return switch (logbackLevel) {
            case "ERROR" -> LogLevel.ERROR;
            case "WARN" -> LogLevel.WARNING;
            case "INFO" -> LogLevel.INFO;
            case "DEBUG", "TRACE" -> LogLevel.DEBUG;
            default -> LogLevel.NOTICE;
        };
    }
}
