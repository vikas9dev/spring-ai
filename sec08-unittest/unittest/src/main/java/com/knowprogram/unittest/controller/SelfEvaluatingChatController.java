package com.knowprogram.unittest.controller;

import com.knowprogram.unittest.exception.InvalidAnswerException;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.evaluation.FactCheckingEvaluator;
import org.springframework.ai.evaluation.EvaluationRequest;
import org.springframework.ai.evaluation.EvaluationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evaluate")
public class SelfEvaluatingChatController {
    private final ChatClient chatClient;
    private FactCheckingEvaluator factCheckingEvaluator;

    @Value("classpath:/promptTemplates/hrPolicy.st")
    Resource hrPolicyTemplate;

    public SelfEvaluatingChatController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClientBuilder);
    }

    @GetMapping("/chat")
    @Retryable(retryFor = InvalidAnswerException.class, maxAttempts = 3)
    public String chat(@RequestParam String message) {
        String response = chatClient.prompt().user(message).call().content();
        try {
            validate(message, response);
            return response;
        } catch (InvalidAnswerException e) {
            // Handle the exception, e.g., return a user-friendly error message
            return "Error: Invalid answer from the LLM.";
        }
    }

    @GetMapping("/prompt-stuffing")
    public String promptStuffing(@RequestParam String message){
        return chatClient.prompt().system(hrPolicyTemplate).user(message).call().content();
    }

    private void validate(String message, String answer) {
        EvaluationRequest evaluationRequest = new EvaluationRequest(message, List.of(), answer);
        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);
        if (!evaluationResponse.isPass()) {
            throw new InvalidAnswerException(message, answer);
        }
    }

    @Recover
    public String recover(InvalidAnswerException e) {
        return "I'm sorry, I could not answer your question. Please try rephrasing it.";
    }

}
