## Building a Hello World Spring AI Application with OpenAI

Let's create a "Hello World" Spring AI application integrated with an LLM model from OpenAI (ChatGPT).

### Project Setup

1.  Navigate to [start.spring.io](https://start.spring.io). This is the standard website for creating Spring Boot applications.
2.  Configure the project details:
    *   Language: Java
    *   Build Tool: Maven (default)
    *   Spring Boot Version: Accept the default.
    *   Group: `com.Easybeats`
    *   Artifact: `openAI`
    *   Description: (Optional)
    *   Package Name: (Default based on Group and Artifact)
    *   Packaging: Jar
    *   Java Version: Accept the default.
3.  Add the necessary dependencies:
    *   **Spring Web**: 🌐 To expose REST APIs.
    *   **Spring Boot DevTools**: 🛠️ For fast application restarts, live reload, and enhanced development experience.
    *   **Spring AI OpenAI Starter**: 🤖 To integrate with OpenAI's LLM models. Search for "AI" and select the OpenAI starter.

    📝 **Note:** Spring AI supports various vendors like Anthropic, Mistral AI, and Azure Stability AI. This abstraction simplifies development.
4.  Click "Generate" to create the Maven project.
5.  Extract the generated project to a folder (e.g., `section01` under a `spring` folder).
    📌 **Example:** `spring/section01/openAI.zip`
6.  Open the extracted folder in your IDE (IntelliJ IDEA is used in the lecture, but any IDE is fine).

### Project Structure and Dependencies

Open the `pom.xml` file to verify the dependencies:

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-openai-spring-boot-starter</artifactId>
        <version>0.8.0</version> <!-- Or the latest version -->
    </dependency>
</dependencies>
```

### Creating the REST Controller

1.  Create a new package named `controller` under `com.easybeats.openai`.
2.  Create a new Java class named `ChatController` inside the `controller` package.
3.  Annotate the class:

    ```java
    @RestController
    @RequestMapping("/api")
    public class ChatController {
        // ...
    }
    ```

4.  Create a `chat` method to handle incoming messages:

    ```java
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return message; // Placeholder for now
    }
    ```

    This simple REST API will receive a message from the end user and, for now, just return the same message.

### Integrating with OpenAI

1.  Inject a `ChatClient` bean into the `ChatController`:

    ```java
    private final ChatClient chatClient;

    public ChatController(ChatClientBuilder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    ```

    The `ChatClient` interface is implemented by `DefaultChatClient`.
2.  Explain the Bean Creation Process:
    *   When the OpenAI dependency is added, Spring Boot creates a bean of type `ChatModel` (specifically, `OpenAIChatModel`).
    *   The framework interacts with the OpenAI chat model using this bean.
    *   Spring Boot also creates a bean of type `ChatClientBuilder` (specifically, `DefaultChatClientBuilder`).
    *   The `ChatClientBuilder` is injected into the `ChatController`.
    *   The `build()` method of `ChatClientBuilder` creates a `DefaultChatClient` instance.
3.  Use the `ChatClient` to send the prompt to the LLM model:

    ```java
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt(message).call().getContent();
    }
    ```

    This code uses the `ChatClient`'s fluent API:
    *   `prompt(message)`: Sets the user's message as the prompt.
    *   `call()`: Invokes the LLM model.
    *   `getContent()`: Retrieves the response from the LLM model.

### Configuring Logging

1.  Open the `application.properties` file.
2.  Add the following property to customize the console log pattern:

    ```properties
    logging.pattern.console=%clr(%d{HH:mm:ss.SSS}){green} %clr(%5p) {blue}%clr([%t]){red} %clr(---){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx
    ```

    This pattern displays:
    *   Time (green)
    *   Log Level (blue)
    *   Thread Name (red)
    *   Logger Message (yellow)

    💡 **Tip:** Copy this value from the GitHub repository to avoid typing errors.

### Running the Application and Handling Errors

1.  Build the project using Maven.
2.  Run the Spring Boot application.
3.  You will likely encounter an exception: "OpenAI API key must be set."

    ⚠️ **Warning:** OpenAI's LLM models are not free. You need to:
    *   Create an account with OpenAI.
    *   Generate an API key in their platform.
    *   Provide the API key to the Spring AI framework.

    The next step is to set up the OpenAI API key.
