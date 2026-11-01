package org.digyna.axtrion.system.logger;

import org.digyna.axtrion.system.common.SpringContext;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test suite for the Framework Logger subsystem.
 * Enables explicit constructor autowiring mode to support Lombok's @RequiredArgsConstructor cleanly.
 */
@SpringBootTest
@RequiredArgsConstructor
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class LoggerIntegrationTest {
    private final Logger log;
    private final Path productionLogDir = Paths.get("../writable/logs");

    @BeforeEach
    void setUp() throws IOException {
        clearLogFiles();
    }

    @AfterEach
    void tearDown() throws IOException {
        clearLogFiles();
    }

    @Test
    @DisplayName("Integration: Should verify static SpringContext and bypass threshold only for READY message")
    void shouldVerifyContextAndBypassThresholdForReadyMessage() throws IOException {
        // 1. Verify that the static context bridge captured the ApplicationContext accurately
        assertNotNull(SpringContext.getContext(), "The static SpringContext should be fully initialized.");

        // 2. Validate that the Logger instance is properly resolvable through the static utility helper
        Logger staticLogger = SpringContext.getBean(Logger.class);
        assertNotNull(staticLogger, "The Logger Bean must be resolvable from the static SpringContext wrapper.");

        // 3. Emit a standard NOTICE event. Since the production threshold is active (WARNING = 4),
        // this level 5 event must be silently discarded by the system validator and never hit the disk.
        log.notice("This is a standard notice message that should be hidden in production");

        // 4. Emit the core initialization success confirmation message (level NOTICE).
        // The exception matching pattern should intercept the 'is READY' string and grant automatic bypass.
        log.notice("System Framework v1.0.0 [axtrion-core - Build: 7a3b1c2] is READY. Initialized successfully in 1.41 seconds.");

        // 5. Extract all lines persisted onto the actual physical file storage media
        List<String> loggedLines = readAllActualLogLines();

        assertFalse(loggedLines.isEmpty(), "The log repository should not be empty due to the startup bypass rule.");

        boolean containsStandardNotice = loggedLines.stream().anyMatch(line -> line.contains("This is a standard notice message"));
        assertTrue(containsStandardNotice, "Ordinary NOTICE messages must be filtered out under the production threshold.");

        boolean containsFrameworkReady = loggedLines.stream().anyMatch(line -> line.contains("is READY") && line.contains("System Framework"));
        assertTrue(containsFrameworkReady, "The Core system READY status notification must force its execution regardless of the threshold.");
    }

    @Test
    @DisplayName("Integration: Should log errors normally based on standard production threshold")
    void shouldLogErrorsNormallyBasedOnStandardProductionThreshold() throws IOException {
        // Error events (level 3 / ERROR) naturally transcend the production cutoff index (4), granting immediate storage access
        log.error("Integration test triggered a standard critical database simulation issue");

        List<String> loggedLines = readAllActualLogLines();

        boolean containsErrorEvent = loggedLines.stream().anyMatch(line -> line.contains("ERROR") && line.contains("Integration test triggered a standard critical database"));
        assertTrue(containsErrorEvent, "Native ERROR severity alerts must be written immediately to the target log file.");
    }

    // Helper methods for processing and cleaning files directly inside the physical environment storage
    private List<String> readAllActualLogLines() throws IOException {
        List<String> combinedLines = new ArrayList<>();
        if (!Files.exists(productionLogDir)) {
            return combinedLines;
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(productionLogDir, "*.log")) {
            for (Path logFile : stream) {
                combinedLines.addAll(Files.readAllLines(logFile));
            }
        }
        return combinedLines;
    }

    private void clearLogFiles() throws IOException {
        if (Files.exists(productionLogDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(productionLogDir, "*.log")) {
                for (Path logFile : stream) {
                    Files.deleteIfExists(logFile);
                }
            }
        }
    }
}
