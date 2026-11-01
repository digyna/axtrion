package org.digyna.axtrion.system.logger.handlers;

import org.digyna.axtrion.system.logger.LogLevel;

public interface HandlerInterface {
    /**
     * Handles logging the message.
     * If this returns false, further handlers will not be invoked.
     *
     * @param level The log level (e.g. {@link LogLevel#ERROR}, {@link LogLevel#DEBUG}).
     * @param message The already-interpolated message.
     * @return true to continue processing other handlers; false to abort.
     */
    boolean handle(LogLevel level, String message);

    /**
     * Determines whether this handler is interested in the given level.
     *
     * @param level The log level to check.
     * @return true if this handler will handle messages at this level.
     */
    boolean canHandle(LogLevel level);

    /**
     * Sets the date format to use when formatting log entries.
     *
     * @param format A date-format string (e.g. "yyyy-MM-dd HH:mm:ss").
     * @return this HandlerInterface instance for chaining.
     */
    HandlerInterface setDateFormat(String format);
}
