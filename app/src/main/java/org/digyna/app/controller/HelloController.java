package org.digyna.app.controller;

import lombok.RequiredArgsConstructor;
import org.digyna.axtrion.system.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloController {
    private final HelloService helloService;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name", required = false) String name) {
        return helloService.greet(name);
    }
}
