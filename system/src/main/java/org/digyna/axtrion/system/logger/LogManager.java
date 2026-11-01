package org.digyna.axtrion.system.logger;

import org.digyna.axtrion.system.config.environment.AppEnvironment;
import org.digyna.axtrion.system.config.properties.AppProperties;
import org.digyna.axtrion.system.config.properties.LoggerProperties;
import org.digyna.axtrion.system.logger.internal.DefaultLogger;

public class LogManager {
    private static volatile DefaultLogger instance;

    private LogManager(){}

    public static DefaultLogger getLogger() {
        if(instance == null ) {
            synchronized (LogManager.class) {
                if(instance == null) {
                    instance = new DefaultLogger(AppEnvironment.config(LoggerProperties.class), AppEnvironment.config(AppProperties.class).isDebug());
                }
            }
        }

        return instance;
    }

    public static void reset() {
        instance = null;
    }
}
