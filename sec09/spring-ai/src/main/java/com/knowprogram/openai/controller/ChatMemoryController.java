package com.knowprogram.openai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatMemoryController {

    private final ChatClient chatMemoryChatClient;

    @GetMapping("/chat-memory")
    public ResponseEntity<String> chatMemory(@RequestParam String message, @RequestHeader String username) {
        return ResponseEntity.ok(chatMemoryChatClient
                .prompt()
                .user(message)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .call().content());
    }
}
