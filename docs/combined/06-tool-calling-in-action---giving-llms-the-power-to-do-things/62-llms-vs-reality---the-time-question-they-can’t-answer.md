## Building a REST API to Query Current Time with LLM

This section focuses on building a new REST API that asks an LLM model about the current time in a given location. The goal is to demonstrate the limitations of LLMs in answering real-time questions and then prepare for implementing tool calling to overcome this limitation.

First, we create a new controller named `TimeController`.

1.  Create a new class `TimeController` inside the `controller` package.
2.  Create a dedicated chat client configuration for this controller. Copy the existing `ChatMemoryChatClientConfig` and rename it to `TimeChatClientConfig`.

    ```java
    // TimeChatClientConfig.java
    @Configuration
    public class TimeChatClientConfig {

        @Bean
        public ChatClient timeChatClient(AiClient aiClient,
                                       PromptTemplate promptTemplate,
                                       @Qualifier("tokenCountAdvisor") AiClientStreamInterceptor tokenCountAdvisor,
                                       @Qualifier("userLoggerAdvisor") AiClientStreamInterceptor userLoggerAdvisor,
                                       @Qualifier("memoryAdvisor") AiClientStreamInterceptor memoryAdvisor) {
            return new ChatClient(aiClient, promptTemplate, List.of(tokenCountAdvisor, userLoggerAdvisor, memoryAdvisor));
        }
    }
    ```

3.  Remove unnecessary configurations (chat memory, RAG-related configurations, advisor) from `TimeChatClientConfig`, focusing solely on tool calling.
4.  Rename the chat client bean to `timeChatClient`.
5.  Inject necessary advisors like `loggerAdvisor`, `tokenUserAdvisor`, and `memoryAdvisor`.

Next, configure the `TimeController`.

1.  Add necessary annotations to the `TimeController`: `@RestController` and `@RequestMapping("/api/tools")`.
2.  Perform dependency injection using the constructor. Inject the `timeChatClient`.

    ```java
    @RestController
    @RequestMapping("/api/tools")
    public class TimeController {

        private final ChatClient chatClient;

        public TimeController(@Qualifier("timeChatClient") ChatClient chatClient) {
            this.chatClient = chatClient;
        }

        // ... rest of the code
    }
    ```

3.  Ensure the `@Qualifier` annotation specifies the correct bean name (`timeChatClient`).

Now, create a simple REST API endpoint.

1.  Add a new REST API endpoint `/local-time` that accepts a user message and forwards it to the LLM. The LLM's response is then returned as output.

    ```java
    @PostMapping("/local-time")
    public String localTime(@RequestBody String message) {
        return chatClient.call(message);
    }
    ```

2.  The method name is `localTime`.
3.  Initially, no system prompt is configured, allowing any question to be asked. However, the intention is to restrict questions to the current time.

Build and run the application.

1.  Save the changes and build the application.
2.  ⚠️ **Warning:** Ensure Docker Desktop is running in the background. This is required due to RAG-related configurations from previous sections, which set up a new Quadrant container on application startup. If you don't need these configurations, you can remove them.
3.  Start the Spring Boot application.

Test the API endpoint using Postman.

1.  Invoke the `/api/tools/local-time` endpoint with a message like "What is the current time in Hyderabad, India?".
2.  Observe the response from the LLM. It should indicate that the LLM lacks real-time capabilities and cannot provide the current time.

Compare the results with ChatGPT.

1.  Ask the same question ("What is the current time in Hyderabad, India?") in ChatGPT.
2.  Notice that ChatGPT provides the correct current time.

Clarification:

*   In our Spring AI application, we are directly interacting with the core LLM model.
*   ChatGPT acts as an LLM wrapper application with additional capabilities, such as web searching, to access real-time information.
*   We cannot directly send requests to ChatGPT from our Spring application because it's a proprietary wrapper application owned by OpenAI.
*   Developers can only interact with the core LLM model.

Conclusion:

The LLM model, in its core form, cannot access real-time data. This limitation will be addressed by implementing tool calling in the subsequent steps. 💡 **Tip:** Tool calling will allow the LLM to interact with external tools to retrieve real-time information.
