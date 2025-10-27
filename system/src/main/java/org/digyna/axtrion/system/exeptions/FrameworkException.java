package org.digyna.axtrion.system.exeptions;

/**
 * Class FrameworkException
 *
 * A collection of exceptions thrown by the framework
 * that can only be determined at run time.
 */
public class FrameworkException extends RuntimeException {
    public FrameworkException(String message) {
        super(message);
    }

    /**
     * @return static
     */
    public static FrameworkException forEnabledZlibOutputCompression()
    {
        return new FrameworkException("Your zlib.output_compression ini directive is turned on. This will not work well with output buffers.");
    }

    /**
     * @return static
     */
    public static FrameworkException forInvalidFile(String path)
    {
        return new FrameworkException(String.format("Invalid file: \"%s\"", path));
    }

    /**
     * @return static
     */
    public static FrameworkException forInvalidDirectory(String path)
    {
        return new FrameworkException(String.format("Directory does not exist: \"%s\"", path));
    }

    /**
     * @return static
     */
    public static FrameworkException forCopyError(String path)
    {
        return new FrameworkException(String.format("An error was encountered while attempting to replace the file \"%s\". Please make sure your file directory is writable.", path));
    }

    /**
     * @return static
     */
    public static FrameworkException forNoHandlers(String clazz)
    {
        return new FrameworkException(String.format("\"%s\" must provide at least one Handler.", clazz));
    }

    /**
     * @return static
     */
    public static FrameworkException forFabricatorCreateFailed(String table, String reason)
    {
        return new FrameworkException(String.format("Fabricator failed to insert on table \"%s\": %s", table, reason));
    }
}
