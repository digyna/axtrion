package org.digyna.app.controller;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.digyna.axtrion.system.service.HelloService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@Epic("Greetings")
@Feature("HelloController")
@Story("Unit test")
@ExtendWith(MockitoExtension.class)
class HelloControllerTest {

    @Mock
    HelloService helloService;

    @InjectMocks
    HelloController controller;

    @Test
    @DisplayName("Should delegate to HelloService and return the greeting")
    void hello_returnsGreetingFromService() {
        when(helloService.greet("Axtrion")).thenReturn("Hello, Axtrion from Axtrion System!");

        String result = controller.hello("Axtrion");

        assertEquals("Hello, Axtrion from Axtrion System!", result);
    }
}
