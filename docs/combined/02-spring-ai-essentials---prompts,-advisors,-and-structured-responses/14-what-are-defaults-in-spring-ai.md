## Understanding and Using Defaults in Spring AI

In real-world applications, you'll likely build multiple REST APIs that interact with LLM models. Using the same chat client across these APIs can lead to repetitive code, especially when dealing with system messages. Spring AI provides a solution to this problem through the concept of **defaults**.

### What are Defaults? 🤔

Defaults are preconfigured values or behaviors that are automatically applied to each request made through a `ChatClient` bean, unless specifically overridden. This is useful for:

*   Avoiding repetition of the same system message or advisor.
*   Maintaining a consistent role or tone for the assistant.
*   Ensuring consistent logging or tool behavior across multiple calls.

### Default Methods Available 🛠️

Spring AI offers several default methods that can be used with the `ChatClientBuilder` object:

*   `defaultSystem()`: Sets a default system role message.
*   `defaultAdvisors()`: Configures default advisors.
*   `defaultTools()`: Sets default tools.
*   `defaultOptions()`: Configures default options.
*   `defaultUser()`: Sets a default user message.

We'll explore these methods in more detail as we discuss the respective concepts.

### Using `defaultSystem()` ⚙️

The `defaultSystem()` method allows you to set a system role message that will be included in all requests made with the same `ChatClient` bean.

📌 **Example:**

```java
@Bean
public ChatClient chatClient(ChatClientBuilder chatClientBuilder) {
    return chatClientBuilder
            .defaultSystem("You are an internal HR assistant. You can only help with HR policies.")
            .build();
}
```

With this setup, even if you have multiple REST APIs using the same `ChatClient` bean, the same default system message will be used, reducing duplicate code.

### Overriding Defaults ⚠️

You can override the default system message by explicitly setting a different system message within a specific REST API.

📌 **Example:**

```java
@GetMapping("/it-help")
public String itHelp(String message) {
    PromptTemplate promptTemplate = new PromptTemplate("...");
    Prompt prompt = promptTemplate.create(Map.of("message", message,
            "system", "You are an internal IT help desk assistant. Your role is to assist the employees with IT related issues, such as resetting the password, unlocking Locking accounts and answering questions related to IT policies. If a user requests help with anything outside of these responsibilities, respond politely and inform them that you are only able to assist with the IT support tasks within your defined scope."));
    return chatClient.call(prompt).getResult().getOutput();
}
```

In this example, the system message is overridden for the `/it-help` endpoint, changing the behavior of the LLM model for that specific API.

### Using `defaultUser()` 🧑‍💻

The `defaultUser()` method allows you to set a default user message that will be included in every prompt, unless overridden. This is useful in scenarios where the end user might not provide a prompt message.

📌 **Example:**

```java
@Bean
public ChatClient chatClient(ChatClientBuilder chatClientBuilder) {
    return chatClientBuilder
            .defaultUser("How can you help me?")
            .build();
}
```

### Refactoring the Code 🧹

To keep your controller clean, you can move the `ChatClient` bean creation logic to a separate configuration class.

1.  Create a new package named `config`.
2.  Create a new Java class named `ChatClientConfig` inside the `config` package.
3.  Annotate the class with `@Configuration`.
4.  Create a method that returns a `ChatClient` bean.
5.  Inject the `ChatClientBuilder` object into the method.
6.  Move the `ChatClient` bean creation logic from the controller to this method.
7.  Annotate the method with `@Bean`.

📌 **Example:**

```java
@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient(ChatClientBuilder chatClientBuilder) {
        return chatClientBuilder
                .defaultSystem("You are an internal HR assistant. You can only help with HR policies.")
                .build();
    }
}
```

In your controller, you can now inject the `ChatClient` bean as a dependency.

📌 **Example:**

```java
@RestController
public class ChatController {

    private final ChatClient chatClient;

    public ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // ...
}
```

### Role Messages 🗣️

*   **System Role Message:** Sets the context and instructions for the LLM model.
*   **User Role Message:** Represents the input or question from the user.
*   **Assistant Role Message:** Represents the response from the LLM model.
*   **Function/Tool Role Message:** Used when the LLM model invokes functions or tools.

📝 **Note:** The slides provided with this lecture contain sample code snippets and can be used to quickly review Spring AI concepts. 💡 **Tip:** Refer to the slides to brush up your Spring AI skills and regain knowledge after completing the course.
