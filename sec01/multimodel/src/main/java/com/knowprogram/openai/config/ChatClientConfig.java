package com.knowprogram.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    @Qualifier("openAiChatClient")
    @ConditionalOnProperty(name = "spring.ai.openai.api-key")
    public ChatClient openAiChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }

    @Bean
    @Qualifier("bedrockChatClient")
    @ConditionalOnProperty(name = "spring.ai.bedrock.aws.access-key")
    public ChatClient bedrockChatClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.build();
    }
}
