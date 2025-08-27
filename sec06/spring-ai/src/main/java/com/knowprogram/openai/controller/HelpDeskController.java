package com.knowprogram.openai.controller;

import com.knowprogram.openai.tools.HelpdeskTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class HelpDeskController {
    private final ChatClient helpDeskChartClient;
    private final HelpdeskTools helpdeskTools;

    @GetMapping("/help-desk")
    public ResponseEntity<String> helpDesk(@RequestHeader String username, @RequestParam String message) {
        String answer = helpDeskChartClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(message)
                .tools(helpdeskTools)
                .toolContext(Map.of("username", username))
                .call()
                .content();
        return ResponseEntity.ok(answer);
    }
}
