## Configuring Chart Options in Spring AI

This section covers how to configure chart options in Spring AI, including setting default options and overriding them for specific REST APIs. We'll explore both Java-based configuration and property-based configuration.

### Setting Default Chart Options

You can set default chart options that apply to all REST APIs. This is done using the `ChatClient` builder object.

1.  Create a `ChartOptions` object using the builder pattern:

    ```java
    ChartOptions chartOptions = ChartOptions.builder()
        .model("gpt-4.1-mini")
        .maxTokens(100)
        .temperature(0.8)
        .build();
    ```

2.  Pass the `ChartOptions` object to the `defaultOptions` method of the `ChatClient` builder.

    📝 **Note:** It's generally recommended to stick with the default values provided by the Spring AI framework unless you have specific requirements.

3.  Build the application.

4.  Test the REST API. You can verify the configured options in the console logs.

    📌 **Example:** The following log entry confirms that the specified model was used:

    ```
    LM: gpt-4.1-mini
    ```

    📌 **Example:** The following log entry confirms that the token usage was within the configured limit:

    ```
    Usage for the response to complete: 60 tokens
    ```

⚠️ **Warning:** Setting a very low `maxTokens` value can lead to abruptly terminated responses.

### Overriding Chart Options for Specific REST APIs

You can override the default chart options for specific REST APIs using the `options` method.

1.  Create a `ChartOptions` object, for example, using the `OpenAIChatOptions` class.

    ```java
    OpenAIChatOptions chartOptions = OpenAIChatOptions.builder()
        .model(ChatModel.GPT_4_0_LATEST)
        .frequencyPenalty(0.5)
        // Add other OpenAI-specific options as needed
        .build();
    ```

    📝 **Note:** OpenAI offers a wide range of options, including web search, user HTTP headers, logit bias, and more. Refer to the OpenAI documentation for details on each option.

2.  Pass the `ChartOptions` object to the `options` method in your REST API controller.

    ```java
    @GetMapping("/promptstuffing")
    public String promptStuffing() {
        return chatClient.call(prompt, OpenAIChatOptions.builder().model(ChatModel.GPT_4_0_LATEST).build());
    }
    ```

3.  Build the application.

4.  Test the REST API and verify the configured options in the console logs.

    📌 **Example:** The following log entry confirms that the specified model was used:

    ```
    Model: gpt-4.0-latest
    ```

💡 **Tip:** If you have questions about the options supported by a specific LLM provider, consult the documentation for the corresponding class in Spring AI.

### Configuring Chart Options Using Properties

Spring AI also allows you to configure chart options using properties in the `application.properties` or `application.yml` file.

1.  Use the `spring.ai.chat.options` prefix followed by the property name.

    📌 **Example:**

    ```properties
    spring.ai.chat.options.model=gpt-4.1-mini
    spring.ai.chat.options.temperature=0.7
    ```

    📝 **Note:** All properties available in the `OpenAIChatOptions` class can be configured using this approach.

💡 **Tip:** Configuring chart options in `application.properties` is recommended if you need to dynamically change option values across different environments using Spring Boot Profiles.
