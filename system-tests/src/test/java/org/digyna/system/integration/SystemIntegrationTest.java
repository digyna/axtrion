package org.digyna.system.integration;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.digyna.system.TestApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Core Architecture Setup")
@Feature("Framework Infrastructure")
@Story("Spring Application Context Initialization")
@SpringBootTest(
        classes = TestApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class SystemIntegrationTest {
    @LocalServerPort
    int port;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    @DisplayName("Integration: Spring context smoke test template")
    void contextLoads() {
        // 1. Verify that the Spring Container is active and healthy
        assertNotNull(applicationContext, "The Spring ApplicationContext must be successfully initialized");

        // 2. Verify that the embedded server allocated a valid, positive TCP port
        assertTrue(port > 0, "The embedded web server should start on a valid port number, actual: " + port);
    }
}
