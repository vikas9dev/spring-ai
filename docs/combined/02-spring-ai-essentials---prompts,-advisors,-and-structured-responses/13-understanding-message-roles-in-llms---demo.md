## Understanding Message Roles in Spring AI

This section explores how Spring AI handles different message roles when interacting with Language Models (LM). We'll cover how user and system messages are treated and how to leverage them effectively.

I've created a project named "spring-ai" and uploaded it to GitHub for your reference. This project currently contains a simple "Hello World" logic, similar to what we've used previously.

The `ChatController` creates a `ChatClient` bean using the `ChatClientBuilder` and injects it. The application then invokes the LM model using the `prompt` method, passing a user message. The OpenAI API key is configured in `application.properties`.

### Sending System and User Messages Separately 🗣️

Currently, we're sending the user's prompt directly to the `prompt` method without differentiating message roles. Let's examine what happens behind the scenes when using the `prompt` method with a string data type.

The logic resides within the `DefaultChatClient` class.

```java
// Example of how the prompt method is invoked
chatClient.prompt("Hello, how can I help you?");
```

The constructor within the `Prompt.java` class creates a `UserMessage` object based on the provided content.

```java
// Inside Prompt.java
public class Prompt {
    public Prompt(String content) {
        // Creates a UserMessage object
    }
}
```

The `UserMessage` class represents a message from the end user or developer, typically a question or prompt.

### Message Interface and Implementations 💬

The `UserMessage` class extends `AbstractMessage`, which implements the `Message` interface. This interface represents any message sent or received in a chat application.

If we examine the implementation classes of `AbstractMessage`, we find several key classes:

*   `AssistantMessage`: Represents a message from the AI assistant.
*   `SystemMessage`: Represents a system-level instruction or context.
*   `ToolResponseMessage`: Represents the response from a tool or function (also known as a function role message).
*   `UserMessage`: Represents a message from the user.

📝 **Note:** As the generative AI ecosystem evolves, Spring AI may introduce more implementation classes. Always refer to the class documentation to understand its purpose.

📌 **Example:** The `ToolResponseMessage` class represents a message with function content in a chat application.

### Overloaded Prompt Methods ⚙️

The `ChatController` offers overloaded `prompt` methods. We've already seen the one that accepts a string, which creates a `UserMessage` behind the scenes. Let's explore the method that accepts a `Prompt` object.

This method allows sending a list of `Message` objects and `ChatOptions`. We'll delve into `ChatOptions` later.

When creating a `Prompt` object, you can use various constructors:

*   Passing a string creates a `UserMessage`.
*   Passing a `Message` object or a list of `Message` objects allows specifying different message roles.

The framework checks the instance type of each message and assigns the appropriate role (e.g., `SystemMessage`, `UserMessage`, `ToolResponseMessage`).

### Using the ChatClient Fluent API 💻

Alternatively, you can use the `ChatClient` fluent API to send user and system messages separately.

```java
// Example of using the fluent API
ChatResponse response = chatClient.prompt(prompt ->
                prompt.system("You are an internal HR assistant...")
                      .user("How many leaves can I take?"));
```

The `user()` method sends a user role message, while the `system()` method sends a system role message.

📌 **Example:**

```java
prompt.system("You are an internal HR assistant. Your role is to help employees with questions related to HR policies such as new policies, working hours, benefits, and code of conduct. If a user asks for help with anything outside of these topics, kindly inform them that you can only assist with the queries related to air pulses.");
```

This allows you to provide instructions to the model on how to behave, restricting its responses to specific topics.

⚠️ **Warning:**  LM models can respond to anything, but in enterprise applications, it's crucial to restrict their roles and responsibilities.

### Testing the System Role 🧪

By setting a system role, you can control the LM's behavior. For example, if a user asks a question outside the HR policy, the LM will respond accordingly.

When asked "What is your name and which model are you using?", the LM replies: "I'm here to assist you with HR policy related questions. If you have any inquiries about leave policies, working hours, benefits, or code of conduct, feel free to ask."

When asked "How many leaves can I take?", the LM provides a generic response about company leave policies.

In upcoming sections, we'll explore how to train an LM model using company-specific data to provide more accurate and relevant answers.
