## Configuring Chat Memory for a REST API

This section details how to configure chat memory capabilities for a REST API named "chat-memory."

We will integrate necessary advisors and chat memory beans into the chat client bean.

### Chat Memory Bean Configuration ⚙️

1.  **Inject Chat Memory Bean:** Inject a bean of type `chatMemory` into the constructor of the class where the chat client bean is created.

    ```java
    @Configuration
    public class ChatClientConfig {

        @Bean
        public ChatClient chatClient(ChatMemory chatMemory) {
            return new ChatClient(chatMemory);
        }
    }
    ```

2.  **Spring Framework Handling:** Spring Framework automatically creates a bean of `chatMemory` implementation. Currently, the framework has a single bean of type `MessageWindowChatMemory`.

3.  **Dependency Injection:** The `MessageWindowChatMemory` class depends on a bean of type `ChatMemoryRepository`. If there's only one implementation class in the classpath, a bean of that class will be injected into the `MessageWindowChatMemory` bean.

### Advisor Configuration 🧑‍💻

1.  **Create Advisor Objects:** Create objects of the required advisors. There are three types of advisors available:
    *   `MessageChatMemoryAdvisor`
    *   `PromptChatMemoryAdvisor`
    *   (Vector store advisor - not covered here due to missing dependencies)

2.  **Message Chat Memory Advisor:** Create an object of `MessageChatMemoryAdvisor`.

    ```java
    MessageChatMemoryAdvisor memoryAdvisor = MessageChatMemoryAdvisor.builder()
            .chatMemory(chatMemory)
            .build();
    ```

3.  **Logger Advisor:** Configure a logger advisor to log requests and responses.

    ```java
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    ```

4.  **Configure Advisors in Chat Client:** Use the `defaultAdvisors` method of the chat client builder to configure the advisors. Pass the advisors as a list.

    ```java
    ChatClient chatClient = ChatClient.builder()
            .defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor))
            .build();
    ```

### Testing the REST API 🧪

1.  **Initial Interaction:** Ask "What is my name?". The API should respond with "I'm sorry, but I don't know your name. How can I assist you today?".

2.  **Provide Name:** Provide your name, e.g., "My name is Madan". The API should respond with "Nice to meet you Madan. How can I assist you today?".

3.  **Subsequent Interaction:** Ask "What is my name?" again. The API should now respond with "Your name is Madan. How can I help you today?".

    This demonstrates that the chat memory is working. The model remembers the previous conversation.

4.  📝 **Note:** The model isn't truly "remembering" in the traditional sense. Instead, relevant past chat messages are sent to the language model (LLM) with each new question.

5.  **Log Verification:** Check the logs to confirm that the entire chat conversation is being sent as input to the LLM. The request will include user messages and assistant messages, structured by their role.

    📌 **Example:**
    *   User message: "What is my name?"
    *   Assistant message: "I'm sorry, but I don't know your name."
    *   User message: "My name is Madan."

### Current Limitations ⚠️

1.  **Single Conversation ID:** All chat messages are currently being saved with a default conversation ID. This means that if different users interact with the API, the model will mix up their information.

    📌 **Example:** If one user says "My name is Madan" and another says "My name is John Doe," the model might incorrectly tell Madan that his name is John Doe.

2.  **Demonstration:** Setting a breakpoint in the `before` method of the `MemoryChatMemoryAdvisor` reveals that the `conversationId` is always "default."

    ```java
    @Override
    public void before(ChatConversationContext context) {
        String conversationId = context.getConversationId();
        // Breakpoint here to inspect conversationId
        List<ChatMessage> messages = chatMemory.get(conversationId);
    }
    ```

3.  **Real-Time Application Issues:** This default conversation ID setup will cause problems in real-time applications where multiple users are interacting with the API.

### Next Steps 🚀

The next step is to solve the problem of the single, default conversation ID by implementing unique conversation IDs for different users. This will ensure that the chat memory is accurate and personalized for each user.
