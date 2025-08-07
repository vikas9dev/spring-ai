## Building a Custom Token Usage Audit Advisor

In this section, we'll explore how to build a custom advisor to log the number of tokens charged by an LLM model for processing requests. This is crucial because LLM models have different pricing models based on token usage.

### Understanding LLM Pricing

Each LLM model has its own pricing structure based on tokens. 📌 **Example:** OpenAI's pricing is per 1 million tokens.  As of now, Spring AI uses GPT-4 Mini as the default model. For this model, input tokens cost $0.05 per 1 million, while output tokens cost $0.60 per 1 million.

This approach of using models via APIs is budget-friendly for many enterprise applications compared to building an LLM from scratch. Spring AI aims to leverage this.

### Creating a Token Usage Audit Advisor

Since the cost is determined by tokens, we can build an advisor to log the number of tokens used for each request. This information can be logged to the console or saved in a database for real-world applications.

Here's how to create the advisor:

1.  Create a new package named `advisors`.
2.  Inside the `advisors` package, create a new Java class named `TokenUsageAuditAdvisor`.
3.  Implement the `CallAdvisor` interface.  In later lectures, we will explore `StreamAdvisor` for streaming communication.

    ```java
    public class TokenUsageAuditAdvisor implements CallAdvisor {
        // Implementation details will be added here
    }
    ```

4.  Override the `adviceCall`, `getName`, and `getOrder` methods.

    *   Set the name to the class name (`TokenUsageAuditAdvisor`).
    *   Set the order to `1`. This gives it higher preference than the default `SimpleLoggerAdvisor` (which has order `0`).

    ```java
    @Override
    public String getName() {
        return "TokenUsageAuditAdvisor";
    }

    @Override
    public int getOrder() {
        return 1;
    }
    ```

5.  Introduce a logger variable:

    ```java
    private static final Logger logger = LoggerFactory.getLogger(TokenUsageAuditAdvisor.class);
    ```

6.  Inside the `adviceCall` method, send the request to the LLM model.

    ```java
    @Override
    public ClientResponse adviceCall(ClientRequest request, ChatClient.CallFunction callFunction) {
        ClientResponse response = callFunction.apply(request);
        // Further logic here
        return response;
    }
    ```

7.  Get the chat response and assign it to a `ChatResponse` object.  Make sure to return the `ClientResponse` so that the next advisor can receive it.

    ```java
    ChatResponse chatResponse = response.getChatResponse();
    ```

8.  Retrieve token details from the `ChatResponse` object's metadata.  This part is specific to OpenAI. ⚠️ **Warning:** If you're using a different LLM vendor, you'll need to research how they provide token details. If you're using a locally deployed LLM, there might not be a token concept.

    ```java
    if (chatResponse.getMetadata() != null) {
        Usage usage = (Usage) chatResponse.getMetadata().get("usage");
        if (usage != null) {
            logger.info("Token usage details: " + usage.toString());
        }
    }
    ```

9.  Log the token usage details.

    ```java
    logger.info("Token usage details: " + usage.toString());
    ```

    The `Usage` object contains details like:

    *   Prompt tokens (request tokens)
    *   Completion tokens (response tokens)
    *   Total tokens (sum of prompt and completion tokens)

    The `totalTokens` value represents the total tokens charged by the LLM.

10. Build the project.

### Configuring the Advisor

There are two ways to configure the advisor:

1.  **Directly in the controller:**  Configure the advisor at the controller level where you invoke the LLM models. ⚠️ **Warning:** This is not recommended for common advisor logic.

    ```java
    @RestController
    public class ChatController {

        @Autowired
        private ChatClient chatClient;

        @GetMapping("/chat")
        public String chat() {
            return chatClient.call("Tell me a joke", List.of(new TokenUsageAuditAdvisor()));
        }
    }
    ```

2.  **At the `ChatClient` level (recommended):** Configure the advisor in the `ChatClientConfig` using `defaultAdvisors`.

    ```java
    @Configuration
    public class ChatClientConfig {

        @Bean
        public ChatClient chatClient() {
            return new DefaultChatClientBuilder()
                    .withDefaultAdvisors(List.of(new SimpleLoggerAdvisor(), new TokenUsageAuditAdvisor()))
                    .build();
        }
    }
    ```

    You can pass a list of advisor objects using `List.of()`.

### Testing the Advisor

1.  Set a breakpoint in the `TokenUsageAuditAdvisor`.
2.  Invoke the chat REST API.
3.  Debug the `usage` object to see the token details.
4.  Check the console logs for the token usage details.

### Conclusion

You've now learned how to build a custom advisor and configure it.  💡 **Tip:**  In real-world applications, you can save these token details to a database and charge users accordingly.  Make sure you are very clear about these advisors concept. Please try to create your own custom advisor with your own custom logic, and try to validate if everything is working end to end.
