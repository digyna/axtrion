package org.digyna.system.runner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "sandbox.active.module", havingValue = "hello-world")
public class HelloCommandLineRunner implements CommandLineRunner {

    @Override
    public void run(String... args) {
        System.out.println("=========================================================");
        System.out.println("EXECUTING SANDBOX APPLICATION RUNNER SUCCESSFULY");
        System.out.println("[INFO]: Sandbox environment is up and ready for manual verification.");
        System.out.println("=========================================================");
    }
}
