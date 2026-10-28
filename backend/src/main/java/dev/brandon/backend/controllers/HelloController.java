package dev.brandon.backend.controllers;

import dev.brandon.backend.dto.ChatRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @PostMapping ("/hello")
    public ChatRequest getMessage(@RequestBody ChatRequest chatRequest) {
        return chatRequest;
    }
}