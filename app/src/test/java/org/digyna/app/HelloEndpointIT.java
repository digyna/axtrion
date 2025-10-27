package org.digyna.app;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("Greetings")
@Feature("HTTP endpoint")
@Story("Integration test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HelloEndpointIT {

    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void helloEndpoint_responds() {
        String body = this.restTemplate.getForObject("http://localhost:" + port + "/hello?name=Axtrion", String.class);
        assertTrue(body.contains("Axtrion"));
    }
}
