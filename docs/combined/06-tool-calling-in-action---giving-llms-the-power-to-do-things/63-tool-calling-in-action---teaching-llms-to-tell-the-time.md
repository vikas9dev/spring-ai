## Implementing Tools Calling in Spring Boot

If you are a Java and Spring Boot developer, implementing tools calling will be straightforward. This section will guide you through the process.

Let's start by creating a new package named `tools`. Inside this package, create a new Java class called `TimeTools`. The class name can be anything.

Annotate the `TimeTools` class with `@Component` to create a Spring bean. This allows the bean to be injected into the Spring framework.

Add a logger statement for debugging purposes.

Next, define a Java method that provides the current time based on the code's execution location. For example, if the code is running from Hyderabad, it should return the local current time. The method should return a string.

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class TimeTools {

    private static final Logger logger = LoggerFactory.getLogger(TimeTools.class);

    // Method implementation will be added below
}
```

Let's name the method `getCurrentLocalTime`. It won't accept any input. Inside the method, add a log statement indicating that the current time in the user's time zone is being returned.

Now, write the logic to return the current local time. Use the `LocalTime` class in Java to get the local time.

```java
    @org.springframework.ai.tool.Tool(name = "getCurrentLocalTime", description = "Get the current time in the user's time zone")
    public String getCurrentLocalTime() {
        logger.info("Returning the current time in the user's time zone.");
        LocalTime currentTime = LocalTime.now();
        return currentTime.toString();
    }
```

The output from this statement will be returned from the method. Now you have a Java method ready to return the current local time.

Annotate the method with `@Tool`. This is a special annotation from the Spring AI team. Use this annotation when you want to expose the logic of a Java method as a tool.

Simply mentioning the annotation is not enough. Provide as much detail as possible using this annotation so the LM model has enough context on when to invoke this tool. The LM model should not invoke this method if a question related to Paris is asked.

The first piece of information to provide is the `name` of the tool. This name can be anything, but make sure it's relevant to the functionality exposed by the tool. Here, the method name itself is used as the tool name. If you have multiple tools, ensure each tool has a unique name.

After the `name`, provide a `description` of the tool. This description is very important because the LM model uses it to understand when to invoke the tool.

📌 **Example:**
```java
@org.springframework.ai.tool.Tool(name = "getCurrentLocalTime", description = "Get the current time in the user's time zone")
public String getCurrentLocalTime() {
    // ... implementation ...
}
```

With this, you have successfully created a tool.

Now, configure this tool into your chat client bean. Go to the `ChatClientConfig` class.

Similar to how advisors are injected, there's a default method to inject tools. The method name is `defaultTools`. Provide the bean where you defined all your tools to this `defaultTools` method.

First, inject the bean into this method. You know you just have to type `timeTools`. This `timeTools` bean needs to be provided as an input to these `defaultTools`.

```java
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.autoconfigure.AiProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.vectorstore.PgVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class ChatClientConfig {

    @Bean
    public VectorStore vectorStore(EmbeddingClient embeddingClient, JdbcTemplate jdbcTemplate) {
        return new PgVectorStore(jdbcTemplate, embeddingClient);
    }

    @Bean
    public ChatClient chatClient(AiProperties aiProperties) {
        return new org.springframework.ai.openai.OpenAiChatClient(aiProperties.getOpenai().getApiKey());
    }

    @Bean
    public TimeTools timeTools() {
        return new TimeTools();
    }

    @Bean
    public org.springframework.ai.chat.prompt.PromptTemplate defaultPromptTemplate(@Value("classpath:/prompts/default-prompt.st") org.springframework.core.io.Resource defaultPromptTemplateResource) {
        return new org.springframework.ai.chat.prompt.PromptTemplate(defaultPromptTemplateResource);
    }

    @Bean
    public org.springframework.ai.chat.prompt.PromptTemplate qaPromptTemplate(@Value("classpath:/prompts/qa-prompt.st") org.springframework.core.io.Resource qaPromptTemplateResource) {
        return new org.springframework.ai.chat.prompt.PromptTemplate(qaPromptTemplateResource);
    }

    @Bean
    public org.springframework.ai.chat.prompt.PromptTemplate systemPromptTemplate(@Value("classpath:/prompts/system-prompt.st") org.springframework.core.io.Resource systemPromptTemplateResource) {
        return new org.springframework.ai.chat.prompt.PromptTemplate(systemPromptTemplateResource);
    }

    @Bean
    public org.springframework.ai.chat.prompt.PromptTemplate userPromptTemplate(@Value("classpath:/prompts/user-prompt.st") org.springframework.core.io.Resource userPromptTemplateResource) {
        return new org.springframework.ai.chat.prompt.PromptTemplate(userPromptTemplateResource);
    }

    @Bean
    public org.springframework.ai.chat.prompt.PromptTemplate assistantPromptTemplate(@Value("classpath:/prompts/assistant-prompt.st") org.springframework.core.io.Resource assistantPromptTemplateResource) {
        return new org.springframework.ai.chat.prompt.PromptTemplate(assistantPromptTemplateResource);
    }

    @Bean
    public org.springframework.ai.chat.prompt.PromptTemplate conversationPromptTemplate(@Value("classpath:/prompts/conversation-prompt.st") org.springframework.core.io.Resource conversationPromptTemplateResource) {
        return new org.springframework.ai.chat.prompt.PromptTemplate(conversationPromptTemplateResource);
    }

    @Bean
    public java.util.List<org.springframework.ai.advisor.AiAdvisor> defaultAiAdvisors(ChatClient chatClient, EmbeddingClient embeddingClient, VectorStore vectorStore) {
        return java.util.Arrays.asList(new org.springframework.ai.advisor.vectorstore.VectorStoreAdvisor(chatClient, embeddingClient, vectorStore));
    }

    @Bean
    public java.util.List<org.springframework.ai.tool.Tool> defaultTools(TimeTools timeTools) {
        return java.util.Arrays.asList(timeTools);
    }
}
```

In case you want to provide tools specific to your REST API, you also have the option of providing the tools directly when prompting the LM. Just like how you have advisors, there's a method called `callTools`. You can provide the bean of the class where you defined the tools to this method. This method will be explored in future sections.

Save the changes and build the project.

Once the build is completed, restart the application because the dependency of dev tools has been removed.

Invoke the same REST API, but ask the question: "What is the current time in my location?". Don't give any city name.

Another tool will be built in a few minutes that is capable of providing the time of any time zone. For now, the developed tool can only provide the time from the location where the code is being executed.

The LM should respond with the current time in your location.

The LM responded saying that the current time in your location is 13:17:33, which means 1:17 p.m. Inside the log statements, you should also see a statement with the message saying that returning the time in the user's time zone. This logger was mentioned inside the method defined in the `TimeTools` class.

This confirms that the LM models are capable of giving instructions to the Spring AI application to invoke a specific method and provide the response.

How this works internally will be discussed in detail later.

It is assumed that you are clear on how tool calling provides real-time information to the LM models.

Let's create one more tool that is capable of providing the time of any location.

Paste a method here. You can get this method from the GitHub repo. This is very similar to the top method that was discussed.

For the second method, the name `getCurrentTime` has been given, and the same name has been provided for the tool as well under the tool description. This time, the description is: "Get the current time in the specified time zone."

Inside the