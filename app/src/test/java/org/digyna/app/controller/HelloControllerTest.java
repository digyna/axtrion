package org.digyna.app.controller;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Greetings")
@Feature("HelloController")
@Story("Unit test")
@ExtendWith(MockitoExtension.class)
class HelloControllerTest {

    @InjectMocks
    HelloController controller;

    @Test
    @DisplayName("Should delegate to HelloService and return the greeting")
    void hello_returnsGreetingFromService() {
        String result = controller.hello("Axtrion");

        assertEquals("Hello, Axtrion from Axtrion System!", result);
    }
}
