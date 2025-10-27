package org.digyna.axtrion.system.properties.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class PathProperties {
    /**
     * ---------------------------------------------------------------
     * WRITABLE DIRECTORY NAME
     * ---------------------------------------------------------------
     * <p>This variable must contain the name of your "writable" directory.
     * The writable directory allows you to group all directories that
     * need write permission to a single place that can be tucked away
     * for maximum security, keeping it out of the app and/or
     * system directories.</p>
     */
    private String writableDirectory;
}
