package com.knowprogram.openai.advisors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.metadata.Usage;
import org.springframework.ai.chat.model.ChatResponse;

public class TokenUsageAuditAdvisor implements CallAdvisor {
    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);

    @Override
    public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
        ChatClientResponse clientResponse = callAdvisorChain.nextCall(chatClientRequest);
        ChatResponse chatResponse = clientResponse.chatResponse();
        if(chatResponse != null && chatResponse.getMetadata() != null) {
            Usage usage = chatResponse.getMetadata().getUsage();
            if(usage != null) {
                logger.info("Prompt tokens: {}", usage.getPromptTokens());
                logger.info("Completion tokens: {}", usage.getCompletionTokens());
                logger.info("Total Token usage: {}", usage.getTotalTokens());
                logger.info("Token usage details: {}", usage.toString());
            }
        }
        return clientResponse;
    }

    @Override
    public String getName() {
        return "TokenUsageAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
