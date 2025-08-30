package com.knowprogram.openai.controller;

import com.knowprogram.openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient
                .prompt()
                .options(OpenAiChatOptions.builder().frequencyPenalty(0.5).presencePenalty(0.5).maxTokens(10).build())
                .user(message)
                .call().content();
    }

    @GetMapping("/it-help")
    public String itHelp(@RequestParam String message) {
        return chatClient
                .prompt()
                .system("""
                    You are an internal IT help desk assistant. Your role is to assist employees 
                    with IT-related issues, such as resetting passwords, unlocking accounts, 
                    and answering questions related to IT policies. If a user requests help with 
                    anything outside of these responsibilities, respond politely and inform them 
                    that you are only able to assist with IT support tasks within your defined scope.
                    """)
                .user(message)
                .call()
                .content();
    }

}
