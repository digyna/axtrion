package org.digyna.axtrion.system.logger;

import org.digyna.axtrion.system.config.environment.AppEnvironment;
import org.digyna.axtrion.system.config.properties.AppProperties;
import org.digyna.axtrion.system.config.properties.LoggerProperties;
import org.digyna.axtrion.system.config.properties.app.PathProperties;
import org.digyna.axtrion.system.config.properties.logger.HandlerProperties;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoggerTest {
    @TempDir
    Path tempDir;

    @BeforeEach
    void setUp() {
        LogManager.reset();
        AppEnvironment.register(
                AppProperties.builder()
                        .charset(StandardCharsets.UTF_8)
                        .debug(true)
                        .timeZone(ZoneId.of("America/Mexico_City"))
                        .paths(PathProperties.builder()
                                .writableDirectory("./writable")
                                .build())
                        .build(), AppProperties.class
        );
        AppEnvironment.register(
                LoggerProperties.builder()
                        .threshold(List.of(4))
                        .dateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                        .handlers(Map.of(
                                "file", HandlerProperties.builder()
                                        .handles(List.of(LogLevel.EMERGENCY, LogLevel.ALERT, LogLevel.CRITICAL, LogLevel.ERROR, LogLevel.WARNING, LogLevel.NOTICE, LogLevel.INFO, LogLevel.DEBUG))
                                        .fileExtension("log")
                                        .filePermissions("0644")
                                        .path(tempDir.toString())
                                        .build())
                        )
                        .build(), LoggerProperties.class
        );
    }

    @Test
    @DisplayName("Should write simple error message to file")
    void shouldWriteSimpleErrorMessageToFile() throws IOException {
        Logger log = LogManager.getLogger();

        log.error("Critical system error");

        List<String> lines = readLogLines();
        assertEquals(1, lines.size(), "The log file should have exactly one line.");
        assertTrue(lines.get(0).contains("ERROR"), "It must contain the log level.");
        assertTrue(lines.get(0).contains("Critical system error"), "It must contain the sent message.");
    }

    @Test
    @DisplayName("Should format and write message with parameters to file")
    void shouldFormatAndWriteMessageWithParametersToFile() throws IOException {
        Logger log = LogManager.getLogger();

        log.error("Critical system error with {}", "parameters");

        List<String> lines = readLogLines();
        assertEquals(1, lines.size(), "The log file should have exactly one line.");
        assertTrue(lines.get(0).contains("ERROR"), "It must contain the log level.");
        assertTrue(lines.get(0).contains("Critical system error with parameters"), "The placeholders '{}' must be replaced by parameters.");
    }

    @Test
    @DisplayName("Should write message with parameters and throwable stacktrace to file")
    void shouldWriteMessageWithParametersAndThrowableToFile() throws IOException {
        Logger log = LogManager.getLogger();

        try {
            throw new NullPointerException("Simulated null pointer exception");
        } catch (NullPointerException e) {
            log.error("Critical system error with {} and throwable", "parameters", e);
        }

        List<String> lines = readLogLines();
        // Depending on how your logger stores the stack trace, the file will have multiple lines.
        assertTrue(lines.size() > 1, "The log file should contain multiple lines due to the stacktrace.");
        assertTrue(lines.get(0).contains("ERROR"), "It must contain the log level.");
        assertTrue(lines.get(0).contains("Critical system error with parameters and throwable"), "The message and placeholders must be formatted correctly.");
    }

    @Test
    @DisplayName("Should write message and only throwable stacktrace to file")
    void shouldWriteMessageAndOnlyThrowableToFile() throws IOException {
        Logger log = LogManager.getLogger();

        try {
            throw new ArithmeticException("/ by zero");
        } catch (ArithmeticException e) {
            log.error("Critical system error with throwable", e);
        }

        List<String> lines = readLogLines();
        assertTrue(lines.size() > 1, "The log file should contain multiple lines due to the stacktrace.");
        assertTrue(lines.get(0).contains("ERROR"), "It must contain the log level.");
        assertTrue(lines.get(0).contains("Critical system error with throwable"), "It must contain the base message.");
    }

    private List<String> readLogLines() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(tempDir, "*.log")) {
            Iterator<Path> iterator = stream.iterator();
            assertTrue(iterator.hasNext(), "The log file should exist in the temporary directory.");
            return Files.readAllLines(iterator.next());
        }
    }
}
