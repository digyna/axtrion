package org.digyna.axtrion.system.logger.handlers;

import org.digyna.axtrion.system.config.environment.AppEnvironment;
import org.digyna.axtrion.system.config.properties.AppProperties;
import org.digyna.axtrion.system.config.properties.logger.HandlerProperties;
import org.digyna.axtrion.system.logger.LogLevel;
import org.digyna.axtrion.system.utils.DateUtils;
import org.digyna.axtrion.system.utils.StringUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

@SuppressWarnings("unused")
public class FileHandler extends BaseHandler {
    private static final String LOGGER_PACKAGE = "org.digyna.axtrion.system.logger";
    private static final StackWalker WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);
    private static final ReentrantLock writeLock = new ReentrantLock();

    /**
     * Folder to hold logs
     *
     * @var String
     */
    protected String path;

    /**
     * Extension to use for log files
     *
     * @var String
     */
    protected String fileExtension;

    /**
     * Permissions for new log files
     *
     * @var String
     */
    protected String filePermissions;

    public FileHandler(HandlerProperties config) {
        super(config);
        String defaultPath = AppEnvironment.config(AppProperties.class).getPaths().getWritableDirectory() + "/logs";
        this.path = StringUtils.isBlank(config.getPath()) ? defaultPath : config.getPath();
        this.fileExtension = StringUtils.isBlank(config.getFileExtension()) ? "log" : config.getFileExtension();
        this.filePermissions = StringUtils.isBlank(config.getFilePermissions()) ? "0644" : config.getFilePermissions();
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public boolean handle(LogLevel level, String message) {
        String fileName = "log-" + LocalDate.now() + "." + fileExtension;

        Path logDirectory = Paths.get(this.path);
        Path filePath = logDirectory.resolve(fileName);

        boolean isNewFile = Files.notExists(filePath);

        writeLock.lock();
        try {
            Files.createDirectories(logDirectory);

            StackWalker.StackFrame callerFrame = WALKER.walk(stream ->
                    Objects.requireNonNull(stream.dropWhile(f -> f.getClassName().startsWith(LOGGER_PACKAGE))
                            .findFirst()
                            .orElse(null))
            );

            String where = String.format("%s.%s(%s:%d)",
                    callerFrame.getClassName(),
                    callerFrame.getMethodName(),
                    callerFrame.getFileName(),
                    callerFrame.getLineNumber()
            );

            String rawTime = Instant.ofEpochMilli(System.currentTimeMillis()).toString();
            String timestamp = DateUtils.formatInstant(rawTime, AppEnvironment.config(AppProperties.class).getTimeZone(), dateFormat);

            StringBuilder sb = new StringBuilder();
            sb.append(level)
                    .append(" - ")
                    .append(timestamp)
                    .append(" - ")
                    .append(where)
                    .append(" --> ")
                    .append(message)
                    .append(System.lineSeparator());

            printToConsole(level, sb.toString());

            // Write to the file in append mode.
            try (FileChannel channel = FileChannel.open(
                    filePath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.WRITE,
                    StandardOpenOption.APPEND
            )) {
                ByteBuffer buffer = ByteBuffer.wrap(sb.toString().getBytes(AppEnvironment.config(AppProperties.class).getCharset()));
                while (buffer.hasRemaining()) {
                    channel.write(buffer);
                }
            }

            if (isNewFile) {
                try {
                    String perms = String.format("%4s", filePermissions).replace(' ', '0');
                    Set<PosixFilePermission> posixPerms = PosixFilePermissions.fromString(convertToPosix(perms));
                    Files.setPosixFilePermissions(filePath, posixPerms);
                } catch (UnsupportedOperationException | IOException ignore) {}
            }

            return true;
        } catch (IOException ignore) {
            return false;
        } finally {
            writeLock.unlock();
        }
    }

    /**
     * Converts an octal permission string (e.g. "0644") to POSIX perms (e.g. "rw-r--r--").
     */
    private static String convertToPosix(String octal) {
        // octal is a 4-digit string like "0644" or "0755"
        int perms = Integer.parseInt(octal, 8);
        StringBuilder sb = new StringBuilder(9);
        char[] mapping = {'r','w','x'};
        for (int i = 6; i >= 0; i -= 3) {
            int triad = (perms >> i) & 0b111;
            for (int bit = 2; bit >= 0; bit--) {
                sb.append((triad & (1 << bit)) != 0 ? mapping[2-bit] : '-');
            }
        }
        return sb.toString();
    }
}
