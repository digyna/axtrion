package org.digyna.axtrion.sandbox.runner;

import org.digyna.axtrion.system.logger.Logger;
import org.springframework.boot.CommandLineRunner;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "sandbox.active.module", havingValue = "logger")
public class LoggerRunner implements CommandLineRunner {
    private final Logger log;

    @Override
    public void run(String... args) {
        log.info("=========================================================");
        log.info("EXECUTING LOGGER APPLICATION RUNNER SUCCESSFULLY");
        log.info("Logger environment is up and ready for verification.");
        log.info("=========================================================");
        log.error("Critical system error");
        log.error("Critical system error with {}", "parameters");
        try {
            throw new NullPointerException("Simulated null pointer exception");
        } catch (NullPointerException e) {
            log.error("Critical system error with {} and throwable", "parameters", e);
        }
        try {
            throw new ArithmeticException("/ by zero");
        } catch (ArithmeticException e) {
            log.error("Critical system error with throwable", e);
        }
    }
}
