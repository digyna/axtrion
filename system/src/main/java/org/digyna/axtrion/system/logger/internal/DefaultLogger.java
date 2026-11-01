package org.digyna.axtrion.system.logger.internal;

import org.digyna.axtrion.system.config.properties.LoggerProperties;
import org.digyna.axtrion.system.config.properties.logger.HandlerProperties;
import org.digyna.axtrion.system.utils.StringUtils;
import org.digyna.axtrion.system.logger.LogLevel;
import org.digyna.axtrion.system.logger.Logger;
import org.digyna.axtrion.system.logger.exeptions.LogException;
import org.digyna.axtrion.system.logger.handlers.HandlerInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.digyna.axtrion.system.common.constants.Chars.LF;
import static org.digyna.axtrion.system.common.constants.Chars.TAB;

public class DefaultLogger implements Logger {
    private static final String PACKAGE_PREFIX = "org.digyna.axtrion.system.logger.handlers";
    private static final String HANDLER_SUFFIX = "Handler";

    /**
     * used by the logThreshold Config setting to define
     * which errors to show.
     *
     * @var Map<Integer, LogLevel>
     */
    private static final Map<Integer, LogLevel> logLevels = Map.of(
            1, LogLevel.EMERGENCY,
            2, LogLevel.ALERT,
            3, LogLevel.CRITICAL,
            4, LogLevel.ERROR,
            5, LogLevel.WARNING,
            6, LogLevel.NOTICE,
            7, LogLevel.INFO,
            8, LogLevel.DEBUG
    );

    /**
     * List of levels to be logged. The rest will be ignored.
     *
     * @var List<LogLevel>
     */
    private final List<LogLevel> loggableLevels;

    /**
     * Format of the timestamp for log files.
     *
     * @var string
     */
    protected String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

    /**
     * Caches instances of the handlers.
     *
     * @var Map<String, HandlerInterface>
     */
    protected Map<String, HandlerInterface> handlers = new HashMap<>();

    /**
     * Holds the configuration for each handler.
     * The key is the handler's class name. The
     * value is an associative array of configuration
     * items.
     *
     * @var Map<String, HandlerProperties>
     */
    protected Map<String, HandlerProperties> handlerConfig;

    /**
     * Caches logging calls for debugbar.
     *
     * @var list<LogMessage>
     */
    public ArrayList<LogMessage> logCache;

    /**
     * Data structure for cached log messages.
     *
     * @param level The severity level of the log
     * @param msg   The actual content of the log message
     */
    public record LogMessage(LogLevel level, String msg) {}

    /**
     * Should we cache our logged items?
     *
     * @var boolean
     */
    protected boolean cacheLogs;

    public DefaultLogger(LoggerProperties config, boolean debug) {
        List<Integer> numericLevels = (config.getThreshold().size() == 1) ? IntStream.rangeClosed(1, config.getThreshold().get(0)).boxed().toList() : config.getThreshold();

        this.loggableLevels = numericLevels.stream()
                .map(logLevels::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if(!StringUtils.isBlank(config.getDateFormat())) {
            this.dateFormat = config.getDateFormat();
        }

        if (config.getHandlers() == null || config.getHandlers().isEmpty()) {
            throw LogException.forNoHandlers("LoggerProperties");
        }

        // Save the handler configuration for later.
        // Instances will be created on demand.
        this.handlerConfig = config.getHandlers();
        this.cacheLogs     = debug;

        if (this.cacheLogs) {
            this.logCache = new ArrayList<>();
        }
    }

    @Override
    public void emergency(String message) {
        log(LogLevel.EMERGENCY, message);
    }

    @Override
    public void emergency(String message, Object... params) {
        log(LogLevel.EMERGENCY, message, params);
    }

    @Override
    public void emergency(String message, Throwable throwable) {
        log(LogLevel.EMERGENCY, message, throwable);
    }

    @Override
    public void alert(String message) {
        log(LogLevel.ALERT, message);
    }

    @Override
    public void alert(String message, Object... params) {
        log(LogLevel.ALERT, message, params);
    }

    @Override
    public void alert(String message, Throwable throwable) {
        log(LogLevel.ALERT, message, throwable);
    }

    @Override
    public void critical(String message) {
        log(LogLevel.CRITICAL, message);
    }

    @Override
    public void critical(String message, Object... params) {
        log(LogLevel.CRITICAL, message, params);
    }

    @Override
    public void critical(String message, Throwable throwable) {
        log(LogLevel.CRITICAL, message, throwable);
    }

    @Override
    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

    @Override
    public void error(String message, Object... params) {
        log(LogLevel.ERROR, message, params);
    }

    @Override
    public void error(String message, Throwable throwable) {
        log(LogLevel.ERROR, message, throwable);
    }

    @Override
    public void warn(String message) {
        log(LogLevel.WARNING, message);
    }

    @Override
    public void warn(String message, Object... params) {
        log(LogLevel.WARNING, message, params);
    }

    @Override
    public void warn(String message, Throwable throwable) {
        log(LogLevel.WARNING, message, throwable);
    }

    @Override
    public void notice(String message) {
        log(LogLevel.NOTICE, message);
    }

    @Override
    public void notice(String message, Object... params) {
        log(LogLevel.NOTICE, message, params);
    }

    @Override
    public void notice(String message, Throwable throwable) {
        log(LogLevel.NOTICE, message, throwable);
    }

    @Override
    public void info(String message) {
        log(LogLevel.INFO, message);
    }

    @Override
    public void info(String message, Object... params) {
        log(LogLevel.INFO, message, params);
    }

    @Override
    public void info(String message, Throwable throwable) {
        log(LogLevel.INFO, message, throwable);
    }

    @Override
    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    @Override
    public void debug(String message, Object... params) {
        log(LogLevel.DEBUG, message, params);
    }

    @Override
    public void debug(String message, Throwable throwable) {
        log(LogLevel.DEBUG, message, throwable);
    }

    @Override
    public void log(LogLevel level, String message) {
        if (!this.loggableLevels.contains(level)) {
            return;
        }

        if (this.cacheLogs) {
            this.logCache.add(new LogMessage(level, message));
        }

        for (Map.Entry<String, HandlerProperties> entry : this.handlerConfig.entrySet()) {
            String className = getFullyQualifiedClassName(entry.getKey());
            HandlerProperties config = entry.getValue();

            if (!this.handlers.containsKey(className)) {
                try {
                    Class<?> clazz = Class.forName(className);
                    Object instance = clazz.getConstructor(HandlerProperties.class).newInstance(config);
                    this.handlers.put(className, (HandlerInterface) instance);
                } catch (Exception e) {
                    throw LogException.forNoHandlers("HandlerInterface");
                }
            }

            HandlerInterface handler = this.handlers.get(className);

            if (!handler.canHandle(level)) {
                continue;
            }

            if (!handler.setDateFormat(this.dateFormat).handle(level, message)) {
                break;
            }
        }
    }

    @Override
    public void log(LogLevel level, String message, Object... params) {
        StringBuilder sb = new StringBuilder();

        if(params != null) {
            Throwable throwable = null;
            int limit = params.length;

            if (params.length > 0 && params[params.length - 1] instanceof Throwable) {
                throwable = (Throwable) params[params.length - 1];
                limit--;
            }

            sb.append(formatMessage(message, params, limit));

            if (throwable != null) {
                sb.append(LF);
                sb.append(throwable).append(LF);
                printStack(sb, throwable.getStackTrace());
            }
        } else {
            sb.append(message);
        }

        log(level, sb.toString());
    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {
        StringBuilder sb = new StringBuilder();

        sb.append(message);

        if(throwable != null) {
            sb.append(LF);
            sb.append(throwable).append(LF);
            printStack(sb, throwable.getStackTrace());
        }

        log(level, sb.toString());
    }

    private String formatMessage(final String msgPattern, final Object[] params, final int limit) {
        if (msgPattern == null) return "";
        if (params == null || limit <= 0 || params.length == 0) return msgPattern;

        StringBuilder sb = new StringBuilder(msgPattern.length() + 50);
        int paramIndex = 0;
        int currentPosition = 0;
        int placeholderPosition;

        // Determine a safe limit to prevent ArrayIndexOutOfBoundsException
        int safeLimit = Math.min(limit, params.length);

        // Search for the "{}" placeholder in the text string
        while ((placeholderPosition = msgPattern.indexOf("{}", currentPosition)) != -1) {
            // Stop searching if we have processed all available safe parameters
            if (paramIndex >= safeLimit) {
                break;
            }

            // Append the text preceding the placeholder
            sb.append(msgPattern, currentPosition, placeholderPosition);

            // Inject the object safely handling null values
            Object param = params[paramIndex++];
            sb.append(param != null ? param.toString() : "null");

            // Move the cursor past the "{}" placeholder
            currentPosition = placeholderPosition + 2;
        }

        // Append the remaining final text fragment
        sb.append(msgPattern, currentPosition, msgPattern.length());
        return sb.toString();
    }

    public static String getFullyQualifiedClassName(String className) {
        if (className == null || className.isEmpty()) {
            throw new IllegalArgumentException("Class name prefix cannot be null or empty.");
        }

        String firstLetter = className.substring(0, 1).toUpperCase();
        String restOfWord = className.substring(1);

        return String.format("%s.%S%s%s", PACKAGE_PREFIX, firstLetter, restOfWord, HANDLER_SUFFIX);
    }

    private void printStack(final StringBuilder sb, final StackTraceElement[] trace) {
        for (final StackTraceElement element : trace) {
            sb.append(TAB).append("at ").append(element).append(LF);
        }
    }
}
