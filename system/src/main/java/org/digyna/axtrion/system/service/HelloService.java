package org.digyna.axtrion.system.service;

import org.springframework.stereotype.Service;

@Service
public class HelloService {
    public String greet(String name) {
        String who = (name == null || name.isBlank()) ? "world" : name;
        return "Hello, " + who + " from Axtrion System!";
    }
}
