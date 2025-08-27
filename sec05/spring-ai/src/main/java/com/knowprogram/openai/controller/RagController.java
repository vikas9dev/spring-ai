package com.knowprogram.openai.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@RestController
@RequestMapping("/api/rag")
@RequiredArgsConstructor
public class RagController {
    private final ChatClient chatMemoryChatClient;
    private final VectorStore vectorStore;

    @Value("classpath:/prompt-templates/system-prompt-random-data-template.st")
    Resource promptTemplate;

    @Value("classpath:/prompt-templates/HRSystemTemplate.st")
    Resource hrSystemTemplate;

    @GetMapping("/random/chat")
    public ResponseEntity<String> randomChat(@RequestParam String message, @RequestHeader String username) {
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query(message)
//                .topK(3)
//                .similarityThreshold(0.5)
//                .build();
//        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//        String similarContext = similarDocs.stream()
//                .map(Document::getText)
//                .collect(Collectors.joining(System.lineSeparator()));
        String answer = chatMemoryChatClient
                .prompt()
//                .system(promptSystemSpec -> promptSystemSpec.text(promptTemplate).param("documents", similarContext))
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(answer);
    }

    @GetMapping("/document/chat")
    public ResponseEntity<String> documentChat(@RequestParam String message, @RequestHeader String username) {
//        SearchRequest searchRequest = SearchRequest.builder()
//                .query(message)
//                .topK(3)
//                .similarityThreshold(0.5)
//                .build();
//        List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
//        String similarContext = similarDocs.stream()
//                .map(Document::getText)
//                .collect(Collectors.joining(System.lineSeparator()));
        String answer = chatMemoryChatClient
                .prompt()
//                .system(promptSystemSpec -> promptSystemSpec.text(hrSystemTemplate).param("documents", similarContext))
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
        return ResponseEntity.ok(answer);
    }
}
