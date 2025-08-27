package com.knowprogram.openai.config;

import com.knowprogram.openai.advisors.TokenUsageAuditAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        ChatOptions chatOptions = ChatOptions.builder().model("gpt-4.1-mini").maxTokens(100).temperature(0.8).build();

        return chatClientBuilder
                .defaultOptions(chatOptions)
                .defaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                .defaultSystem("""
                        You are an internal HR assistant. Your role is to help employees with questions related to 
                        HR policies such as new policies, working hours, benefits, and code of conduct. If a 
                        user asks for help with anything outside of these topics, kindly inform them that you can 
                        only assist with the queries related to air pulses.
                        """)
                .defaultUser("How can you help me?")
                .build();
    }
}
