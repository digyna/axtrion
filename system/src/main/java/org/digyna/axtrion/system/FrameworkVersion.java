package org.digyna.axtrion.system;

import lombok.experimental.UtilityClass;

import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public final class FrameworkVersion {
    private static final Properties properties = new Properties();

    private static final String DEFAULT_COMMIT= "dev";
    private static final String DEFAULT_UNKNOWN = "unknown";
    private static final String DEFAULT_VERSION = "0.0.0";

    private static final String ARTIFACT;
    private static final String COMMIT;
    private static final String GROUP;
    private static final String NAME;
    private static final String TIME;
    private static final String VERSION;

    static {
        try (InputStream input = FrameworkVersion.class
                .getClassLoader()
                .getResourceAsStream("META-INF/axtrion-build.properties")) {

            if (input != null) {
                properties.load(input);
                ARTIFACT = properties.getProperty("build.artifact", DEFAULT_UNKNOWN);
                COMMIT   = properties.getProperty("build.commit", DEFAULT_COMMIT);
                GROUP    = properties.getProperty("build.group", DEFAULT_UNKNOWN);
                NAME     = properties.getProperty("build.name", DEFAULT_UNKNOWN);
                TIME     = properties.getProperty("build.time", DEFAULT_UNKNOWN);
                VERSION  = properties.getProperty("build.version", DEFAULT_VERSION);
            } else {
                ARTIFACT = "system";
                COMMIT   = DEFAULT_COMMIT;
                GROUP    = "org.digyna";
                NAME     = "axtrion-system";
                TIME     = DEFAULT_UNKNOWN;
                VERSION  = DEFAULT_VERSION;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Error initializing Axtrion system metadata", e);
        }
    }

    public static String getArtifact() {
        return ARTIFACT;
    }

    public static String getCommit() {
        return COMMIT;
    }

    public static String getGroup() {
        return GROUP;
    }

    public static String getName() {
        return NAME;
    }

    public static String getTime() {
        return TIME;
    }

    public static String getVersion() {
        return VERSION;
    }
}
