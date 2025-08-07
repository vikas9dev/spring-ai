## Understanding the Stateless Nature of LM Models

This section explores the stateless nature of Large Language Models (LLMs) and how it impacts conversational applications. We'll see a demo illustrating this limitation and set the stage for how Spring AI can help address it.

Imagine the movie "Before I Go to Sleep," where the protagonist wakes up each day with no memory of the past. 🎬 This is analogous to how LLMs function by default.

### The Problem: LLMs Don't Remember

*   By default, LLMs are **stateless**. This means they don't retain information from previous interactions.
*   Each interaction is treated as a brand new conversation. 🗣️
*   This can be a significant issue when building applications that require maintaining context across multiple turns, such as chatbots. 🤖

### Demo: Demonstrating Statelessness with Spring AI

To illustrate this, let's create a simple Spring AI application.

1.  **Create a `ChatClient` Bean:**

    We'll create a basic `ChatClient` bean that interacts with an LLM. This configuration intentionally avoids any memory management.

    ```java
    @Configuration
    public class ChatMemoryChatClientConfig {

        @Bean(name = "chatMemoryChatClient")
        public ChatClient chatMemoryChatClient(AiClient aiClient) {
            return aiClient.getChatClient();
        }
    }
    ```

    📝 **Note:** We name the bean `chatMemoryChatClient` to avoid conflicts with other `ChatClient` beans in the project.

2.  **Inject the `ChatClient` into a Controller:**

    We'll create a REST controller to expose an endpoint that uses the `ChatClient`.

    ```java
    @RestController
    @RequestMapping("/chat-memory")
    public class ChatMemoryController {

        private final ChatClient chatClient;

        public ChatMemoryController(@Qualifier("chatMemoryChatClient") ChatClient chatClient) {
            this.chatClient = chatClient;
        }

        @GetMapping
        public String chat(@RequestParam("message") String message) {
            return chatClient.call(message);
        }
    }
    ```

    📌 **Example:** The `@Qualifier` annotation is used to specify which `ChatClient` bean to inject, since we have multiple defined.

3.  **Test the API:**

    *   Send a message to the API: "My name is Madan."
    *   The LLM responds with a generic greeting.
    *   Then, ask "What is my name?"
    *   The LLM responds that it doesn't know your name. 🤯

    This demonstrates that the LLM has no memory of the previous interaction.

### ChatGPT vs. Our Demo: Why the Difference?

You might be wondering why ChatGPT *does* remember previous conversations, even though it's also based on an LLM.

*   ChatGPT is a **wrapper application** around the core LLM. 🎁
*   The developers of ChatGPT have implemented mechanisms to maintain conversation history and feed it back into the LLM with each new interaction.
*   We need to build something similar using Spring AI to achieve conversational memory in our applications.

### Next Steps

In the following sections, we'll explore how Spring AI can help us solve this problem and build applications that can maintain conversation context. 🚀
