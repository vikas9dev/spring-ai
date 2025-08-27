package com.knowprogram.openai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class TimeController {
    private final ChatClient timeChatClient;

    @GetMapping("/local-time")
    public ResponseEntity<String> localTime(@RequestParam String message, @RequestHeader String username) {
        return ResponseEntity.ok(timeChatClient.prompt().advisors(a -> a.param(CONVERSATION_ID, username)).user(message).call().content());
    }

}
