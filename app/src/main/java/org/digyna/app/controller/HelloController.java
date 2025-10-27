package org.digyna.app.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false) String name) {
        String who = (name == null || name.isBlank()) ? "world" : name;
        return "Hello, " + who + " from Axtrion App!";
    }
}
