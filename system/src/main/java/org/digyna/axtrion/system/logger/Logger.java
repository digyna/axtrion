package org.digyna.axtrion.system.logger;

@SuppressWarnings("unused")
public interface Logger {
    /**
     * Logs a message object with the {@link LogLevel#EMERGENCY EMERGENCY} level.
     *
     * @param message the message string to log.
     */
    void emergency(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#EMERGENCY EMERGENCY} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void emergency(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#EMERGENCY EMERGENCY} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void emergency(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#ALERT ALERT} level.
     *
     * @param message the message string to log.
     */
    void alert(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#ALERT ALERT} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void alert(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#ALERT ALERT} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void alert(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#CRITICAL CRITICAL} level.
     *
     * @param message the message string to log.
     */
    void critical(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#CRITICAL CRITICAL} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void critical(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#CRITICAL CRITICAL} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void critical(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#ERROR ERROR} level.
     *
     * @param message the message string to log.
     */
    void error(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#ERROR ERROR} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void error(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#ERROR ERROR} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void error(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#WARNING WARNING} level.
     *
     * @param message the message string to log.
     */
    void warn(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#WARNING WARNING} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void warn(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#WARNING WARNING} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void warn(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#NOTICE NOTICE} level.
     *
     * @param message the message string to log.
     */
    void notice(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#NOTICE NOTICE} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void notice(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#NOTICE NOTICE} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void notice(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#INFO INFO} level.
     *
     * @param message the message string to log.
     */
    void info(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#INFO INFO} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void info(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#INFO INFO} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message object to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void info(String message, Throwable throwable);

    /**
     * Logs a message object with the {@link LogLevel#DEBUG DEBUG} level.
     *
     * @param message the message string to log.
     */
    void debug(String message);

    /**
     * Logs a message with parameters at the {@link LogLevel#DEBUG DEBUG} level.
     *
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void debug(String message, Object... params);

    /**
     * Logs a message at the {@link LogLevel#DEBUG DEBUG} level including the stack trace of the {@link Throwable}
     * <code>throwable</code> passed as parameter.
     *
     * @param message the message to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void debug(String message, Throwable throwable);

    /**
     * Logs a message object with the given level.
     *
     * @param level the logging level
     * @param message the message string to log.
     */
    void log(LogLevel level, String message);

    /**
     * Logs a message with parameters at the given level.
     *
     * @param level the logging level
     * @param message the message to log; the format depends on the message factory.
     * @param params parameters to the message.
     */
    void log(LogLevel level, String message, Object... params);

    /**
     * Logs a message at the given level including the stack trace of the {@link Throwable} <code>throwable</code> passed as
     * parameter.
     *
     * @param level the logging level
     * @param message the message to log.
     * @param throwable the {@code Throwable} to log, including its stack trace.
     */
    void log(LogLevel level, String message, Throwable throwable);
}
