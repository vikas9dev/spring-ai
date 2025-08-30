package com.knowprogram.openai.config;

import com.knowprogram.openai.advisors.TokenUsageAuditAdvisor;
import com.knowprogram.openai.tools.TimeTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import java.util.List;

@Configuration
public class HelpDeskChartClientConfig {

    @Value("classpath:/prompt-templates/helpDeskSystemPromptTemplate.st")
    Resource helpDeskSystemPromptTemplate;

    @Bean("helpDeskChartClient")
    public ChatClient helpDeskChartClient(ChatClient.Builder chatClientBuilder,
                                     ChatMemory chatMemory,
                                     TimeTools timeTools) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        return chatClientBuilder
                .defaultSystem(helpDeskSystemPromptTemplate)
                .defaultTools(timeTools)
                .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor))
                .build();
    }

//    @Bean
//    ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
//        return new DefaultToolExecutionExceptionProcessor(true);
//    }

}
