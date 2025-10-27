package org.digyna.system.unit;

import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("Core Architecture Setup")
@Feature("Framework Infrastructure")
@Story("Unit Test Runner Verification")
class SystemUnitTest {

    @Test
    @DisplayName("Unit: Classic Hello World verification template")
    void shouldRunClassicHelloWorldUnitTest() {
        String expectedMessage = "Hello World";
        String actualMessage = "Hello World";

        // Basic assertion to ensure JUnit runner works perfectly without Spring overhead
        assertEquals(expectedMessage, actualMessage, "The unit test messages must match");
    }
}
