## The Beauty of Spring: Advisors in Spring AI

Spring AI provides a complete ecosystem for building intelligent applications by integrating with Large Language Models (LLMs). A key component of this ecosystem is **advisors**. Let's explore what advisors are and how to configure them within a Spring AI application.

Advisors function as interceptors or middleware for your prompt flow, similar to filters in traditional web applications. 

By using advisors, you can:

*   Intercept requests and responses traveling to and from the LLM. 
*   Apply pre-processing or post-processing logic to prompt data.
*   Add custom logging or auditing.
*   Inject additional behavior without modifying core logic.
*   Chain multiple behaviors cleanly.

Essentially, advisors enable you to handle cross-cutting concerns and housekeeping activities.

### How Advisors Work in a Spring AI Application

When an end-user invokes a REST API, the request reaches the `ChatClient` bean. From there, the request is typically forwarded to the LLM. However, with advisors configured, the flow changes:

1.  The request is intercepted by the configured advisors.
2.  Each advisor executes its defined logic in a chain.
3.  The request is then forwarded to the LLM.
4.  The LLM processes the request and returns a response.
5.  The response is intercepted by the advisors again.
6.  Each advisor executes its logic on the response.
7.  Finally, the response reaches the end-user.

### Best Practices for Configuring Advisors

When configuring advisors, keep these best practices in mind:

*   Keep advisors stateless and request-scoped. Avoid storing data across multiple requests or sessions.
*   Chain multiple advisors as needed, focusing each advisor on a specific cross-cutting concern. For example, one advisor for logging and another for security. Avoid merging everything into a single advisor.
*   ⚠️ **Warning:** Avoid altering the meaning of prompts unless it is intentional and required by the business. Advisors provide the power to intercept requests, but this doesn't mean you should fundamentally change the user's intended prompt.
*   Use advisors for cross-cutting concerns and housekeeping activities only.
*   Don't write core business logic inside advisors. Core logic should reside in your controllers or REST APIs.

### Configuring Advisors in Spring AI

Spring AI provides built-in advisors, and you can also create your own custom advisors.

Some examples of built-in advisors include:

*   `SimpleLoggerAdvisor`
*   `SafeguardAdvisor`
*   `PromptChartMemoryAdvisor`

You can configure advisors in two ways:

1.  **At the `ChatClient` bean level (default advisor):** This applies the advisor to all requests made through that `ChatClient`.
2.  **Specific to a single request:** This allows you to apply an advisor only to a particular request initiated in a REST API.

📌 **Example:** Configuring a default advisor using `ChatClientBuilder`:

```java
ChatClient chatClient = ChatClientBuilder.builder()
    .baseUrl("your_llm_url")
    .apiKey("your_api_key")
    .defaultAdvisor(new MyCustomAdvisor())
    .build();
```

📌 **Example:** Configuring an advisor for a specific request:

```java
ChatResponse response = chatClient.call(prompt, advisors -> advisors.add(new AnotherCustomAdvisor()));
```

To implement custom advisors, you need to implement interfaces like `CallAdvisor` and `StreamAdvisor`.

📝 **Note:** The `advisors` method is similar to the `defaultSystemPrompt` and `systemPrompt` methods discussed previously.

We will explore these advisors in more detail and start implementing them in our Spring AI application in the next lecture. 🚀
