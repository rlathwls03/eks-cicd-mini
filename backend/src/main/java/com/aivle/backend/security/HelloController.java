package com.aivle.backend.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to AI Book Manager!";
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Team7!";
    }
}

