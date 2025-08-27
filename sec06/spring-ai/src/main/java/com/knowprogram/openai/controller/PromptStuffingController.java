package com.knowprogram.openai.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PromptStuffingController {

    @Value("classpath:prompt-templates/system-prompt-template.st")
    private Resource systemPromptTemplate;

    private final ChatClient chatClient;

    public PromptStuffingController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam String message) {
        return chatClient
                .prompt()
                .system(systemPromptTemplate)
                .user(message)
                .call()
                .content();
    }
}
