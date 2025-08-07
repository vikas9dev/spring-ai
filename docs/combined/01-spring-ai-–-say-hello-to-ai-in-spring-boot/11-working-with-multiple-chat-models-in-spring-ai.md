## Leveraging Multiple Chat Models in Spring AI Applications

In real-world enterprise applications, it's common to work with multiple chat models within the same Spring AI application. No single AI model excels at all tasks, as different models possess varying capabilities. Adopting diverse models for specific use cases is a prevalent practice.

This section outlines the steps to integrate multiple models into a Spring AI application. A sample project named "multi-modal" has been created, incorporating both a Llama model and an OpenAI model. This project will be available on GitHub.

### Project Setup

1.  **Model Dependencies:** Include dependencies for the desired models (e.g., Llama, OpenAI) in your `pom.xml` or `build.gradle` file.
2.  **Configuration:** Configure the necessary properties in `application.properties` or `application.yml`.

    📌 **Example:**

    ```properties
    spring.openai.api-key=YOUR_OPENAI_API_KEY
    spring.ai.ollama.chat.model=llama2
    ```

    This configuration specifies the OpenAI API key and the Llama model to be used.

### Disabling Auto-Configuration

By default, Spring AI auto-configures a `ChatClient.Builder` bean. When working with multiple models, it's necessary to disable this default behavior and create separate `ChatClient` beans for each model.

1.  Disable the default `ChatClient.Builder` auto-configuration:

    ```properties
    spring.ai.chat.client.enabled=false
    ```

### Creating Chat Client Beans

Create a configuration class to define the `ChatClient` beans for each model.

1.  Create a new package named `config`.
2.  Inside the `config` package, create a class named `ChatClientConfig`.
3.  Annotate the class with `@Configuration`.

    ```java
    @Configuration
    public class ChatClientConfig {
        // Bean definitions will go here
    }
    ```

4.  Define `ChatClient` beans for each model. Two approaches are demonstrated below.

    *   **Approach 1: Using `ChatClient.create()`**

        ```java
        @Bean
        public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
            return ChatClient.create(openAiChatModel);
        }
        ```

        This method injects the `OpenAiChatModel` bean (auto-configured by Spring AI) and uses it to create a `ChatClient` bean.

    *   **Approach 2: Using `ChatClient.builder()`**

        ```java
        @Bean
        public ChatClient ollamaChatClient(OllamaChatModel ollamaChatModel) {
            ChatClient.Builder chatClientBuilder = ChatClient.builder(ollamaChatModel);
            // You can configure the builder here, e.g., set default system prompt
            // chatClientBuilder.defaultSystemPrompt("You are a helpful assistant.");
            return chatClientBuilder.build();
        }
        ```

        This approach provides more control over the `ChatClient` configuration. You can use methods like `defaultSystemPrompt()` to customize the model's behavior.

        💡 **Tip:** The second approach using `ChatClient.builder()` offers greater flexibility for configuring the chat client.

### Creating a Controller

Create a controller to expose REST endpoints that utilize the different chat models.

1.  Create a new package named `controller`.
2.  Inside the `controller` package, create a class named `MultiModelChatController`.
3.  Annotate the class with `@RestController` and `@RequestMapping("/api")`.

    ```java
    @RestController
    @RequestMapping("/api")
    public class MultiModelChatController {
        // REST API methods will go here
    }
    ```

4.  Inject the `ChatClient` beans into the controller using constructor injection and the `@Qualifier` annotation.

    ```java
    private final ChatClient openAiChatClient;
    private final ChatClient ollamaChatClient;

    public MultiModelChatController(
            @Qualifier("openAiChatClient") ChatClient openAiChatClient,
            @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.ollamaChatClient = ollamaChatClient;
    }
    ```

    📝 **Note:** The `@Qualifier` annotation is used to specify the bean name, which defaults to the method name in the configuration class.

5.  Create REST endpoints to interact with each chat model.

    ```java
    @GetMapping("/openai/chat")
    public String openAiChat(@RequestParam("message") String message) {
        return openAiChatClient.prompt(message).getContent();
    }

    @GetMapping("/llama/chat")
    public String llamaChat(@RequestParam("message") String message) {
        return ollamaChatClient.prompt(message).getContent();
    }
    ```

### Testing the Application

1.  Build the application.
2.  Ensure that the Llama model is running locally (if using Ollama).
3.  Configure the OpenAI API key.
4.  Use a tool like Postman to test the REST endpoints.

    📌 **Example:**

    *   Send a request to `/api/openai/chat?message=What is your name and which model are you using?`
    *   Send a similar request to `/api/llama/chat?message=What is your name and which model are you using?`

    Verify that each endpoint returns a response from the corresponding AI model.

### Key Takeaways

*   When using multiple chat models, it's essential to create separate `ChatClient` beans for each model.
*   Disable the default `ChatClient.Builder` auto-configuration.
*   Use constructor injection and the `@Qualifier` annotation to inject the `ChatClient` beans into your business logic.
*   The approach for creating and injecting `ChatClient` beans remains consistent regardless of the complexity of the application.
