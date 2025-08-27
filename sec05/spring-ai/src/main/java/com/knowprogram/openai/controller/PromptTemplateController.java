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
public class PromptTemplateController {

    @Value("classpath:prompt-templates/email-prompt.st")
    private Resource userPromptTemplate;

    private final ChatClient chatClient;

    public PromptTemplateController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/email")
    public String emailResponse(@RequestParam String customerName, @RequestParam String customerMessage) {
        return chatClient
                .prompt()
                .system("""
                            You are a professional customer service assistant which helps drafting
                            email responses to improve the productivity of the customer support team
                        """)
                .user(promptTemplateSpec -> promptTemplateSpec.text(userPromptTemplate)
                        .param("customerName", customerName)
                        .param("customerMessage", customerMessage))
                .call()
                .content();
    }
}
