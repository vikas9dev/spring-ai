## Getting Started with MCP Demos

Let's dive into building an MCP demo application. The goal is to create a Spring Boot application that acts as an MCP host and includes an MCP client component. This client will connect to an existing MCP server.

For the initial phase, we will focus on connecting to existing MCP servers developed by various companies. This is a common real-world scenario where you might need to integrate with an MCP server provided by a third party. Later, we'll explore building our own MCP server.

Here's the plan:

1.  Build a Spring Boot application to act as an MCP client.
2.  Connect to an existing MCP server.
3.  Explore MCP servers developed by other companies.
4.  Eventually, build our own MCP server.

To start, we'll create a Spring Boot project and add the necessary dependencies.

First, create a new Spring Boot project using a tool like Spring Initializr.  Set the group as "Combat Bytes" and the artifact and name as "MCP client".

Next, add the following dependencies:

*   Spring Web: To expose REST APIs.
*   Spring AI OpenAI: To use OpenAI LLM models.
*   Spring Boot DevTools: For quick restarts during development.
*   Model Context Protocol Client: The MCP client dependency.  Make sure to select the "Model context protocol client" starter project.

Now, let's add the dependencies to the `pom.xml` file.

```xml
<dependencies>
    <!-- Spring Web -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- Spring AI OpenAI -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-ai-openai</artifactId>
        <version>0.8.0</version>
    </dependency>
    <!-- Spring Boot DevTools -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <!-- MCP Client -->
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-mcp-client-starter</artifactId>
        <version>0.8.0</version>
    </dependency>
</dependencies>
```

Generate the project and open it in your IDE (e.g., IntelliJ).

Next, add some important properties to the `application.properties` file:

```properties
logging.pattern.console=%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level %logger{36} - %msg%n
logging.level.org.springframework.ai.advisor=DEBUG
spring.ai.openai.api-key=${OPENAI_API_KEY}
```

*   `logging.pattern.console`: Configures the console logging pattern.
*   `logging.level.org.springframework.ai.advisor`: Sets the debug log level for the advisor package.
*   `spring.ai.openai.api-key`: Injects the OpenAI API key as an environment variable.

📝 **Note:** You can find these properties in the GitHub repository.

Since the `spring.ai.openai.api-key` property expects an environment variable, you need to configure it in your IDE. In IntelliJ, you can edit the run configuration and add the `OPENAI_API_KEY` environment variable with your OpenAI API key as the value.

Now, create a new controller package (e.g., `com.example.mcpclient.controller`) and a new Java class named `MCPClientController`.

Annotate the class with `@RestController` and `@RequestMapping("/api")`.

```java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class MCPClientController {

}
```

Next, inject a `ChatClient` bean into the controller. Use a `ChatClientBuilder` to configure the client with a `SimpleLoggingAdvisor`.

```java
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.autoconfigure.openai.OpenAiChatProperties;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.advisor.SimpleLoggingAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.ChatClientBuilder;

@RestController
@RequestMapping("/api")
public class MCPClientController {

    private ChatClient chatClient;

    @Autowired
    public MCPClientController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.call(message);
    }

    @Bean
    public ChatClient chatClient(OpenAiChatProperties openAiChatProperties) {
        return new ChatClientBuilder()
                .withDefaultAdvisors()
                .withAdvisor(new SimpleLoggingAdvisor())
                .build();
    }
}
```

This code defines a simple REST API endpoint `/chat` that takes a message as input and forwards it to the LLM model. The response from the LLM model is then returned to the end user.

Build the project and start the application.

Once the application is started, you can invoke the REST API with a message (e.g., "Who are you and how can you help me?"). You should receive a response from the OpenAI model.

Now that we have a basic Spring AI application working, let's implement the MCP client capabilities.

To get started with the MCP client, we need to choose an MCP server to interact with.

Visit the official MCP protocol website and navigate to the GitHub repository.

On the GitHub repo, you'll find official documentation and SDKs for various languages.  The Java SDK is designed and donated by the Spring AI team.

Scroll down to find the "Servers" section. Here, you'll find a list of reference servers and third-party servers.

Reference servers are simple MCP servers developed officially by the MCP team. Third-party servers are developed by various companies.

📌 **Example:**

*   Atlassian provides an MCP server for interacting with JIRA and Confluence.
*   AWS, Azure, and Cloudflare also offer MCP servers.

For this demo, we'll use the "File System" MCP server, which is a simple NodeJS application capable of performing file system operations.

With this server, you can instruct the LLM model to read files, write files, create directories, search files, and get file metadata.

To set up the File System MCP server, you can use Docker or the `npx` command. We'll use `npx` for this example.

Inside your `application.properties` file, add the following property:

```properties
spring.ai.mcp.client.stdio.servers-configuration=classpath:mcp-servers.json
```

This property specifies the transport type as `stdio` and points to a configuration file named `mcp-servers.json`.

Create a new file named `mcp-servers.json` under the `src/main/resources` folder.

Copy the configurations for the File System MCP server from the official documentation and paste them into the `mcp-servers.json` file.

```json
[
  {
    "name": "File System",
    "command": "npx",
    "args": [
      "-y",
      "@model-context-protocol/server-file-system",
      "--directory",
      "/Users/yourusername/Website/MCP"
    ]
  }
]
```

Replace `/Users/yourusername/Website/MCP` with the actual path to a directory on your local system. Make sure this directory exists.

⚠️ **Warning:**  If you are using Windows, you need to provide your Windows-specific path (e.g., `C:\\Users\\yourusername\\MCP`).

This configuration tells the Spring AI application to set up the MCP server using `npx` and specifies the command and arguments to use.

Now, we need to inject the tools details from the MCP server into our `ChatClient` bean. To do this, we'll use a bean of type `ToolCallbackProvider`.

Add the following code to your `MCPClientController`:

```java
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.reader.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.ai.chat.ChatClientBuilder;

@RestController
@RequestMapping("/api")
public class MCPClientController {

    private ChatClient chatClient;

    @Autowired
    public MCPClientController(ChatClient chatClient) {
        this.chatClient = chatClient;
