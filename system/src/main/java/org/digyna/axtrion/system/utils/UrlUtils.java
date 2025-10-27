package org.digyna.axtrion.system.utils;

import lombok.experimental.UtilityClass;

import java.net.URI;

@UtilityClass
public class UrlUtils {
    public static boolean isValidHttpUrl(URI uri) {
        return uri != null
                && uri.getScheme() != null
                && uri.getHost() != null
                && (uri.getScheme().equalsIgnoreCase("http")
                || uri.getScheme().equalsIgnoreCase("https"));
    }
}
