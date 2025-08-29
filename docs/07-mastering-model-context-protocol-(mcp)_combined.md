# 07 Mastering Model Context Protocol (Mcp)


Sections-
1. [MCP: Model Context Protocol Explained](#1-mcp-model-context-protocol-explained)
2. [MCP: Standardizing Context for LLMs](#2-mcp-standardizing-context-for-llms)
3. [MCP Transport Types: STDIO, Streamable HTTP, and SSE](#3-mcp-transport-types-stdio-streamable-http-and-sse)
4. [Getting Started with MCP Demos](#4-getting-started-with-mcp-demos)
5. [Demonstrating File and Directory Management with MCP and LM Model](#5-demonstrating-file-and-directory-management-with-mcp-and-lm-model)
6. [Understanding and Using the MCP Inspector](#6-understanding-and-using-the-mcp-inspector)
7. [Integrating Spring AI with GitHub MCP Server](#7-integrating-spring-ai-with-github-mcp-server)
8. [Building an MCP Server with Stdio Transport](#8-building-an-mcp-server-with-stdio-transport)
9. [Building an MCP Server with STDIO Transport](#9-building-an-mcp-server-with-stdio-transport)
10. [Creating an MCP Server with Remote Invocation](#10-creating-an-mcp-server-with-remote-invocation)


---

## 1. MCP: Model Context Protocol Explained

MCP is a recent buzzword in the AI industry. It stands for **Model Context Protocol**, and you can find more information on its official website.

MCP was introduced by Anthropic, the company behind Claude, a product similar to ChatGPT. Let's explore why this new protocol is needed in the Generative AI space.

To understand the need for MCP, let's first consider a familiar scenario: web application development.

Imagine a monolithic application where both the UI and backend code are deployed together. Within the backend logic, there's a piece of code that performs a complex addition operation.

In a monolithic architecture, this logic can be invoked directly using method or function calls.

However, what if a third-party application needs to use this complex addition logic? Or if you want to expose this logic for external use?

Traditionally, developers would move this complex logic into a separate **REST API**. This API can then be invoked by the backend application or any third-party application.

This approach provides flexibility in exposing business logic.  The emergence of REST APIs in web development mirrors a similar need in the GenAI world.

In GenAI, we use Large Language Models (LLMs). To enhance these LLMs, we employ techniques like Retrieval-Augmented Generation (RAG) and tool calling.  Previously, this logic was often embedded within a single Spring application.

This Spring AI application handles LM model invocation and includes the tools logic. The tools details are also provided to the LM using the Spring AI framework.

The drawback of this approach is that the tools logic resides within the Spring AI application.

What if we want to expose this tools logic so that any third-party application can leverage it and provide it as input to the LM models?

The solution is to move the tools logic into a separate server called an **MCP server**. This server contains all the logic and tools information.

Since the tools information is exposed externally, a protocol is needed to retrieve this information and provide it to the LM model. This is where MCP comes in.

MCP establishes the communication between the LM and the external tools logic.

In summary:

*   Old Approach: 🧱 All logic (including tools) within a single application.
*   New Approach: ⚙️ Tools logic separated into an MCP server.
*   MCP: 🤝 Protocol for communication between the LM and the MCP server.

I hope this provides a brief introduction to why the MCP protocol is required in GenAI applications.  More details will be provided to answer all your questions.

---

## 2. MCP: Standardizing Context for LLMs

MCP (Model Context Protocol) is an open protocol designed to standardize how applications provide context to Large Language Models (LLMs). This context can include:

*   Tools 🧰
*   Information ℹ️
*   Prompts 💬
*   Any other resource information 📚

The goal of MCP is to centralize all these elements in a single location.

### MCP Server

This centralized location is called the **MCP server**. Client applications can read context details from the MCP server and feed them to LLMs. Think of MCP as a **USB-C port** 🔌 for AI applications, providing a standard way to connect AI models to various data sources and tools. Just like USB-C simplified device connectivity, MCP aims to simplify AI model integration.

### Client-Server Architecture

MCP follows a client-server architecture. MCP clients send requests to MCP servers, which respond with details about the tools, resources, and prompts they expose.

### Why Not Just Use HTTP?

A common question is why introduce MCP when HTTP already exists.

*   HTTP is a general web protocol for client-server communication (e.g., loading web pages, API communication, file transfers).
*   HTTP is stateless and doesn't inherently understand AI-specific concepts like tools or prompt context.

MCP is built on top of protocols like HTTP, acting as a specialized layer tailored for AI use cases. Similar to how GraphQL adds a query layer to HTTP, MCP focuses on AI integrations.

While AI integrations can be built with raw HTTP, MCP provides consistency and standardization. It's aware of AI jargon like tools, prompts, resources, and context, making communication more efficient and reliable.

### MCP Architecture

The MCP architecture consists of three key components:

1.  MCP Host
2.  MCP Client
3.  MCP Server

#### MCP Server

Developers use the **MCP server** to expose their application's capabilities to LLMs in the form of tools.

📌 **Example:** Exposing a "create a task" capability.

Behind the scenes, developers write the business logic (using annotations like `@tool`) to create a task, connecting to local or remote systems like databases, file storage, or third-party APIs. The MCP client and host are agnostic to this underlying logic.

#### MCP Host

The **MCP host** acts as a central coordinator. It's typically a chatbot, IDE, or application that manages permissions and session context. It decides when to invoke a specific tool, either based on user input or automatically.

#### MCP Client

The **MCP client** is launched by the host and handles communication between the host and a specific MCP server. It fetches tools, information, and other useful data from the MCP server. The MCP client is responsible for sending tool requests and receiving responses.

### MCP Architecture: A Spring AI Example

Let's illustrate these components with a Spring AI application example:

1.  **MCP Host:** The Spring AI application itself, receiving prompts from the end user.
2.  **MCP Client:** A component within the Spring AI application that connects to the MCP server.
3.  **MCP Server:** A server like the one built by GitHub, exposing capabilities like creating a GitHub repository, creating a file, performing commits, and looking for issues.

During the Spring AI application's startup:

1.  The MCP client connects to the MCP server and requests a list of exposed tools using the MCP protocol.
2.  The MCP client fetches tool information and provides it to the MCP host.
3.  The MCP host provides the tool definitions to the LLM.
4.  Based on the user's prompt, the LLM instructs the MCP host to invoke a specific tool exposed by the MCP server.

📌 **Example:** An end user prompts the MCP host to create a new GitHub repo. The LLM instructs the MCP host to invoke the tool responsible for creating a GitHub repo, and the Spring AI app instructs the MCP server to create the repository.

The beauty of MCP is that it separates tool logic into a dedicated MCP server, which can be leveraged by other developers or third-party applications. Security measures, such as access keys, are in place to control access to the MCP server.

### Evolution of LLM Integrations

*   Initially, integrating with LLMs via REST APIs was straightforward, but application capabilities were limited. LLMs couldn't access real-time or private information.
*   Techniques like RAG (Retrieval-Augmented Generation) and tool crawling were introduced to overcome these limitations.
*   MCP enables communication with multiple MCP servers, each exposing capabilities as tools. LLMs can invoke these tools to perform powerful actions and handle complex operations.

This is the theory behind the MCP architecture.

---

## 3. MCP Transport Types: STDIO, Streamable HTTP, and SSE

The choice of **MCP transport type** depends on where the **MCP server** is deployed. Let's explore the available options:

*   **STDIO (Standard Input/Output):** 💻
    *   Used when the **MCP server** and the **host application** (e.g., Spring application) are deployed on the **same system**.
    *   Think of it as direct communication, like using a walkie-talkie.
    *   No internet or browser is needed; it's purely local communication.
    *   Suitable for command-line tools, terminal-based apps, local setups, and simple scripts.
    *   Communication is quick and easy.
*   **Streamable HTTP:** 🌐
    *   Used when the **MCP server** is deployed remotely on a different system.
    *   Internet-friendly transport type that uses HTTP POST for sending data to the server.
    *   Optionally uses Server-Sent Events (SSE) for streaming responses from the server to the client.
    *   Best for web apps, browser-based tools, and scenarios requiring two-way, ongoing communication.
    *   Supports multiple concurrent users and keeps conversations alive across many requests using sessions.
*   **SSE (Server-Sent Events):** ⚠️ **Deprecated**
    *   An older transport type for streaming data from the **server to the client**.
    *   Now part of Streamable HTTP for backward compatibility.
    *   May be completely removed in future versions of MCP.
    *   Useful when **only one-way streaming** was needed (server to client) and network bandwidth was limited.
    *   📌 **Example:** Sending live stock prices.

### STDIO vs. Streamable HTTP

Here's a comparison of SSE and Streamable HTTP:

| Feature                       | SSE                               | Streamable HTTP                      |
| ----------------------------- | --------------------------------- | ------------------------------------ |
| Server to Client Communication | ✅ Supported                         | ✅ Supported (optionally uses SSE)      |
| Client to Server Communication | ❌ Not Supported                     | ✅ Supported (using HTTP POST)          |
| Session Management            | ❌ Not Supported                     | ✅ Supported (using MCP session ID headers) |
| Two-Way Communication         | ❌ Not Supported                     | ✅ Supported                            |
| Multiple Concurrent Clients   | ❌ Limited Support                   | ✅ Better Support                       |
| Better Compatibility                 | ❌ Limited                           | ✅ Improved                             |

Due to the limitations of SSE, **Streamable HTTP is the recommended transport type** for establishing communication between the MCP client and server.

![MCP Transport Types](/docs/img/mcp-transport-types.png)

### Visual Representation

*   **Local Deployment:** If both MCP servers are deployed locally, **STDIO** is used.
*   **Remote Deployment:** If MCP servers are deployed remotely, **Streamable HTTP** is used.

### Additional Resources

*   **[Official MCP Website](https://modelcontextprotocol.io/docs/getting-started/intro):** This is the go-to resource for the latest information on MCP.
    *   Concepts -> Transports: Details on all transport types.
    *   Core Architecture: How MCP works with hosts, clients, and servers.

### MCP Components

MCP servers can expose various kinds of information:

1.  **Resources:** 🗂️ Any data the server wants to make available to clients (e.g., file contents, database records, API responses).
2.  **Prompts:** 💬 Reusable prompt templates and workflows for better interaction with LLMs.
3.  **Tools:** 🛠️ Executable functionality that allows LLMs to interact with external systems and perform actions in the real world.

    *   Tools enable communication with applications using natural language, which LLMs can understand.
    *   The LLM handles the preparation of requests required by the MCP server tool.

### Conclusion

📝 **Note:** MCP is a rapidly evolving protocol, so staying updated with the official documentation is crucial. Vendors like OpenAI, Google (Gemini), and DeepSeq are adopting this protocol.

---

## 4. Getting Started with MCP Demos

Let's dive into building an MCP demo application. The goal is to create a Spring Boot application that acts as an MCP host and includes an MCP client component. This client will connect to an existing MCP server.

For the initial phase, we will focus on connecting to existing MCP servers developed by various companies. This is a common real-world scenario where you might need to integrate with an MCP server provided by a third party. Later, we'll explore building our own MCP server.

Here's the plan:

1.  Build a Spring Boot application to act as an MCP client.
2.  Connect to an existing MCP server.
3.  Explore MCP servers developed by other companies.
4.  Eventually, build our own MCP server.

To start, we'll create a Spring Boot project and add the necessary dependencies.

First, create a new Spring Boot project using a tool like Spring Initializr.  Set the group as "knowprogram" and the artifact and name as "mcpclient".

Next, add the following dependencies:

*   Spring Web: To expose REST APIs.
*   Spring AI OpenAI: To use OpenAI LLM models.
*   Spring Boot DevTools: For quick restarts during development.
*   Model Context Protocol Client (`spring-ai-starter-mcp-client`): The MCP client dependency.  Make sure to select the "Model context protocol client" starter project (not the "Model context protocol server" starter).

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

Next, inject a `ChatClient` bean into the controller. Use a `ChatClientBuilder` to configure the client with a `SimpleLoggingAdvisor`.

```java
@RestController
@RequestMapping("/api")
public class MCPClientController {
    private final ChatClient chatClient;

    public MCPClientController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultAdvisors(new SimpleLoggerAdvisor()).build();
    }

    @GetMapping("/chat")
    public String chat(@RequestParam String message) {
        return chatClient.prompt().user(message).call().content();
    }
}
```

This code defines a simple REST API endpoint `/chat` that takes a message as input and forwards it to the LLM model. The response from the LLM model is then returned to the end user.

Build the project and start the application.

Once the application is started, you can invoke the REST API with a message (e.g., "Who are you and how can you help me?"). `curl --location 'http://localhost:8080/api/chat?message=Who%20are%20you%20and%20how%20can%20you%20help%20me%3F' \
--header 'username: testUser99'` You should receive a response from the OpenAI model.

Now that we have a basic Spring AI application working, let's implement the MCP client capabilities.

To get started with the MCP client, we need to choose an MCP server to interact with.

Visit the official MCP protocol website and navigate to the [GitHub repository](https://github.com/modelcontextprotocol).

On the GitHub repo, you'll find official documentation and SDKs for various languages.  The Java SDK is designed and donated by the Spring AI team.

Scroll down to find the "Servers" section. Here, you'll find a list of reference servers and third-party servers.

Reference servers are simple MCP servers developed officially by the MCP team. Third-party servers are developed by various companies.

📌 **Example:**

*   Atlassian provides an MCP server for interacting with JIRA and Confluence.
*   AWS, Azure, and Cloudflare also offer MCP servers.

For this demo, we'll use the "**[File System](https://github.com/modelcontextprotocol/servers/tree/main/src/filesystem)**" MCP server, which is a simple NodeJS application capable of performing file system operations.

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
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/Users/yourusername/Website/MCP"
      ]
    }
  }
}
```

Replace `/Users/yourusername/Website/MCP` with the actual path to a directory on your local system. Make sure this directory exists (like `/home/vikas/workspace/spring-ai/playground/mcp`).

⚠️ **Warning:**  If you are using Windows, you need to provide your Windows-specific path (e.g., `C:\\Users\\yourusername\\MCP`).

This configuration tells the Spring AI application to set up the MCP server using `npx` and specifies the command and arguments to use.

**Note**: For the MCP server to get started successfully, please make sure to install **Node JS** in your system.

Now, we need to inject the tools details from the MCP server into our `ChatClient` bean. To do this, we'll use a bean of type `ToolCallbackProvider`.

Add the following code to your `MCPClientController`:

```java
public MCPClientController(ChatClient.Builder chatClientBuilder, ToolCallbackProvider toolCallbackProvider) { // add the ToolCallbackProvider
    this.chatClient = chatClientBuilder
            .defaultToolCallbacks(toolCallbackProvider)
            .defaultAdvisors(new SimpleLoggerAdvisor())
            .build();
}
```

Build the project and run the application.

Now, you can use the `/chat` endpoint to interact with the MCP server using the LLM model.

---

## 5. Demonstrating File and Directory Management with MCP and LM Model

**Note: Somehow the npx was not working, will try this later**

This section demonstrates how an LM model interacts with an MCP server to perform file and directory management tasks using natural language instructions.

Initially, the LM model is queried to understand its capabilities. The response confirms its ability to assist with:

*   File management
*   Directory management
*   Search functionality
*   Context review

This confirms that the LM model has access to the tools exposed by the MCP server.

### Creating a File

The following instruction is given to the LM model:

> Create a file named `model.txt` in the directory `users/Ezbuy/MCP` with the content "hello world" inside it.

The LM model translates this instruction into actions for the MCP host and client. The MCP client then invokes the appropriate tool on the MCP server, which handles the actual file creation.

The result is verified: a file named `model.txt` is created in the specified directory, containing the text "hello world".

### Creating a Directory and File

Next, the LM model is instructed to create a directory and a file within it:

> Create a folder named `testing` in the directory `users/Ezbuy/MCP` with a file `testing.txt` inside it.

The LM model successfully creates the `testing` folder and the `testing.txt` file.  Notably, the LM model also populates the `testing.txt` file with sample data: "This is a test file."

This showcases the power of the MCP protocol, where natural language instructions are used instead of structured data formats like JSON or XML.

### Listing Files and Directories

The LM model is then asked to list the contents of a directory:

> List the folders and files under `users/Ezbuy/MCP`.

The LM model provides a detailed list of files and directories, including hidden files.

### Deleting a Directory

The following instruction is given to delete a directory:

> Delete the folder `testing` present under `users/Ezbuy/MCP`.

It's observed that a hard delete is not supported. Instead, the `testing` folder is moved to `testing_deleted` within the same directory. The LM model confirms this action.

An attempt to completely delete the `testing_deleted` folder is made with the instruction:

> Please delete the folder `testing_deleted` completely present under `users/Ezbuy/MCP`.

However, this operation fails to complete. The MCP server enters a loop, attempting to move the directory to a backup location repeatedly.

⚠️ **Warning:** This behavior highlights a crucial point: the LM model's capabilities are limited by the underlying MCP server's functionality. Bugs, defects, or limitations in the MCP server cannot be automatically overcome by the LM model.

📝 **Note:** Thorough testing of the MCP server's capabilities is essential to ensure reliable operation when integrated with an LM model.

The operation is manually canceled.

### Conclusion

This demonstration illustrates the potential of using Spring AI and MCP protocols to build intelligent applications. By leveraging these frameworks, developers can create applications that respond to natural language instructions.

The upcoming lectures will focus on building an MCP server.

---

## 6. Understanding and Using the MCP Inspector

The MCP team has developed a utility app called the **[MCP Inspector](https://github.com/modelcontextprotocol/inspector)** to help you understand and test MCP servers. This tool is highly recommended for exploring the capabilities of MCP servers, especially those developed by other organizations. Instead of relying solely on documentation, the Inspector allows you to quickly discover the tools, resources, and prompts exposed by an MCP server.

### Getting Started with the MCP Inspector

To start using the MCP Inspector, follow these steps:

1.  **Navigate to the Model Context Protocol GitHub page.** Look for the pinned repository named "Inspector."
2.  **Execute the following command:**

    ```bash
    npx @modelcontextprotocol/inspector
    ```

    ⚠️ **Warning:** Make sure you have Node.js installed before running this command. Otherwise, it will not work.

### Installing Node.js

If you don't have Node.js installed, follow these steps:

1.  Go to the [Node.js website](https://nodejs.org/).
2.  Download and install Node.js using the instructions provided on the download page.
3.  Verify the installation by running the following command in your terminal:

    ```bash
    node --version
    ```

    You should see an output similar to this:

    ```
    v16.13.0
    ```

    This confirms that Node.js is installed correctly.

### Running the MCP Inspector

After installing Node.js, execute the `npx` command mentioned earlier:

```bash
npx @modelcontextprotocol/inspector
```

This will start the MCP Inspector and automatically open it in your browser. If it doesn't open automatically, use the URL provided in the terminal output, and by default UI will be accessible at [http://localhost:6274](http://localhost:6274).

### Connecting to an MCP Server

The MCP Inspector UI will appear, allowing you to connect to an MCP server. Here's how to connect using Stdio:

1.  Select "Stdio" as the connection type.
2.  Enter the command used to start the MCP server, along with any necessary arguments.  These details can also be found in the official documentation of the MCP server. 📌 **Example:**

    ```bash
    npx -y @modelcontextprotocol/server ./path/to/your/server
    ```

3.  In the "Arguments" section, provide the arguments, separating them with whitespace. 📌 **Example:** `-y @modelcontextprotocol/server-filesystem /home/vikas/workspace/spring-ai/playground/mcp`
4.  If your MCP server requires environment variables, you can add them in the "Environment Variables" section.
```log
Transport Type: STDIO
Command: npx
Arguments: -y @modelcontextprotocol/server-filesystem /home/vikas/workspace/spring-ai/playground/mcp
```
5.  Click the "Connect" button.

You might encounter a warning about securing your MCP server. For demo purposes, you can usually ignore this.

Once the connection is established, you'll see a green dot indicating a successful connection. The right-hand side of the UI will display the details exposed by the MCP server, such as tools, resources, and prompts.

### Exploring MCP Server Capabilities

The MCP Inspector allows you to explore the tools, resources, and prompts exposed by the MCP server.

*   If the MCP server exposes only tools, the other sections (resources, prompts) will be disabled.
*   Click on "List Tools" to see a list of available tools. 📌 **Example:** `readFile`, `writeFile`, `createDirectory`, etc.

💡 **Tip:** Pay close attention to the tools and their capabilities. This will help you understand what the MCP server can do.

### Testing Tools

To understand the purpose of a tool, click on it. This will display a description and the input details it expects.

📌 **Example-1:**

1.  Select the `write_file` tool. It create a new file or completely overwrite an existing file with new content. 
2.  Path: `/home/vikas/workspace/spring-ai/playground/mcp/test.txt`
3.  Content: `Hello, world!`
4.  Click "Run Tool."

The Inspector will execute the tool and display the result, which in this case would be a success message.

Request:-
```json
{
  "method": "tools/call",
  "params": {
    "name": "write_file",
    "arguments": {
      "path": "/home/vikas/workspace/spring-ai/playground/mcp/test.txt",
      "content": "Hello"
    },
    "_meta": {
      "progressToken": 1
    }
  }
}
```
Response:-
```json
{
  "content": [
    {
      "type": "text",
      "text": "Successfully wrote to /home/vikas/workspace/spring-ai/playground/mcp/test.txt"
    }
  ]
}
```
We can see the newly created file with the given content.

📌 **Example-2:**

1.  Select the `readFile` tool.
2.  Provide the path to a text file in the "path" field. 📌 **Example:** `/user/mcp/test.txt`
3.  Click "Run Tool."

The Inspector will execute the tool and display the result, which in this case would be the content of the file.

⚠️ **Warning:** When testing tools, make sure you provide the exact details expected by the tool.

📝 **Note:** When integrating the MCP server with a Spring AI application, the LM model will handle populating the tool's input based on its expectations. The LM model will also process the tool's output and send the final result to the end user.

### Conclusion

The MCP Inspector is a valuable tool for developers who want to explore MCP servers developed by others. It provides a quick and easy way to understand the capabilities of an MCP server and test its tools.

Once you're done inspecting the MCP server, you can stop the session in the terminal by pressing `Ctrl+C`. Refreshing the MCP Inspector page will then display an error page.

### Bonus: Terminal Recommendation

The terminal used in this lecture is Warp. You can download it for free from [Warp.dev](https://www.warp.dev/). Warp offers advanced features, including customizable themes. The theme used in the lecture is "Pink." You can change the appearance of the terminal in the settings.

---

## 7. Integrating Spring AI with GitHub MCP Server

This section explains how to integrate a Spring AI application with a GitHub MCP server. The GitHub repository containing the server details will be used.

### Finding the GitHub MCP Server

1.  Search for "GitHub" on the [list of MCP servers](https://github.com/modelcontextprotocol/servers).
2.  Locate the [official GitHub MCP server repository](https://github.com/github/github-mcp-server).
3.  The repository provides instructions on setting up the server, either remotely or locally.

### Prerequisites

*   An MCP host that supports the latest MCP specification and remote servers. The Spring AI application serves as this host.
*   Docker Desktop installed and running locally.

### Configuration

1.  Copy the GitHub MCP server configurations from the official documentation.
```json
{
  "github": {
    "command": "docker",
    "args": [
      "run",
      "-i",
      "--rm",
      "-e",
      "GITHUB_PERSONAL_ACCESS_TOKEN",
      "ghcr.io/github/github-mcp-server"
    ],
    "env": {
      "GITHUB_PERSONAL_ACCESS_TOKEN": "${input:github_token}"
    }
  }
}
```
2.  Paste the configurations into the `mcp-servers.json` file in your Spring AI project.
    *   If you already have an MCP server configured (e.g., "file system"), add a comma and then paste the GitHub MCP server configuration.

📌 **Example:** `mcp-servers.json`
```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "/home/vikas/workspace/spring-ai/playground/mcp"
      ]
    },
    "github": {
      "command": "docker",
      "args": [
        "run",
        "-i",
        "--rm",
        "-e",
        "GITHUB_PERSONAL_ACCESS_TOKEN",
        "ghcr.io/github/github-mcp-server"
      ],
      "env": {
        "GITHUB_PERSONAL_ACCESS_TOKEN": "${input:github_token}"
      }
    }
  }
}
```

### Running the GitHub MCP Server with Docker

The GitHub MCP server can be run locally using Docker.

1.  It uses the following Docker command:

```bash
docker run -i --rm -e GITHUB_PERSONAL_ACCESS_TOKEN=<YOUR_GITHUB_TOKEN> <DOCKER_IMAGE_NAME>
```

*   `-i`: Runs the container in interactive mode.
*   `--rm`: Removes the container once the process is stopped.
*   `-e GITHUB_PERSONAL_ACCESS_TOKEN`: Sets an environment variable for the GitHub personal access token.
*   `<DOCKER_IMAGE_NAME>`: Specifies the Docker image for the GitHub MCP server.

2.  ⚠️ **Warning:** You need to provide your GitHub personal access token as an environment variable.

### Generating a GitHub Personal Access Token

1.  Log in to your GitHub account.
2.  Go to **Settings** -> **Developer settings** -> **Personal access tokens** -> **Fine-grained tokens**.
3.  Click on **Generate new token**.
4.  Give the token a descriptive name (e.g., "demo").
5.  Set the **Resource owner** to your account.
6.  Configure the necessary permissions:
    *   Under **Repository permissions**, grant **Read and write** access for all required permissions.
    *   Grant necessary **Account permissions** as well.
7.  Click on **Generate token**.
8.  ⚠️ **Warning:** Copy the generated token immediately, as you won't be able to see it again.
9.  Provide this token as the value for the `GITHUB_PERSONAL_ACCESS_TOKEN` environment variable in the Docker command.

### Restarting the Application

1.  Build the Spring AI application.
2.  Restart the application.
3.  Verify that a container created using the GitHub MCP server Docker image is running in Docker Desktop.

### Testing the Integration

1.  Use a tool like Postman to send a message to the Spring AI application.
2.  The LLM model should now consider tools exported by the GitHub MCP server.

📌 **Example:** Sending the message "Who are you and how can you help me?" might result in a response indicating capabilities related to GitHub repository management, code review, and collaboration.

### Example Usage

1.  List GitHub repositories: "List down the GitHub repositories of `<YOUR_GITHUB_USERNAME>`".
    *   Replace `<YOUR_GITHUB_USERNAME>` with your actual GitHub username.
    *   The application should return a list of your repositories with their stats and metrics.
2.  Create a new repository: "Create a new repository. The name of the repository is spring-ai-demo-github-mcp-test."
    *   The application should create a new repository with the specified name under your account.
3.  Create a README file: "Create a readme file in `<YOUR_GITHUB_USERNAME>`/spring-ai-demo-github-mcp-test repository."
    *   Replace `<YOUR_GITHUB_USERNAME>` with your actual GitHub username.
    *   The application should create a README file in the specified repository.

### Inspecting Tools with MCP Inspector

1.  Execute the following command to start the MCP inspector:

```bash
npx @modelcontextprotocol/inspector
```

2.  Configure the inspector:
    *   **Transport type:** STDIO
    *   **Command:** docker
    *   **Arguments:** `run -i --rm -e GITHUB_PERSONAL_ACCESS_TOKEN ghcr.io/github/github-mcp-server`
    *   **Environment Variables:** Add an environment variable named `GITHUB_PERSONAL_ACCESS_TOKEN` with your GitHub access token as the value.
3.  Click **Connect**.
4.  Go to **Tools** and click **List tools** to see the tools exposed by the GitHub repository.

💡 **Tip:** Exploring these tools helps you build better prompts by understanding the available functionalities and parameters.

### Conclusion

This process allows you to integrate any MCP server into your Spring AI application. There are numerous MCP servers developed by various companies, and this approach enables you to leverage their tools within your application.

The next step is to learn how to build your own MCP servers to expose your own tools.

---

## 8. Building an MCP Server with Stdio Transport

When building an MCP server, the first decision is whether to support Stdio or Streamable HTTP transport. This section focuses on building an MCP server using the Stdio transport type. Other options will be explored later.

Creating an MCP server involves the following steps:

1.  **Project Setup:**
    *   Go to [Start.spring.io](https://start.spring.io).
    *   Set the artifact ID to `MCP server Stdio`.
    *   Add the following dependencies:
        *   `spring-web`
        *   `spring-cloud-starter-model-context-protocol-server` (MCP Server)
        *   `spring-boot-devtools`
        *   `com.h2database:h2` (H2 Database)
        *   `spring-boot-starter-data-jpa` (JPA)
        *   `org.projectlombok:lombok` (Lombok)

2.  **Generate and Import Project:**
    *   Click "Generate" to create a Spring Boot project.
    *   Extract the project to a suitable location (e.g., a section seven folder).
    *   Open the project in IntelliJ IDEA: `File -> New -> Module from Existing Sources`.
    *   Select the `MCP server Stdio` folder and click "Open".
    *   Confirm the build type is Maven and click "Create".

3.  **Maven Configuration:**
    *   Verify the project is recognized as a Maven project. If not, manually add it using the "+" symbol in the Maven tool window.
    *   Open `pom.xml`.
    *   Remove the `spring-boot-starter-webmvc` dependency. This dependency is for Streamable HTTP, not Stdio.

        ```xml
        <!-- Remove this dependency -->
        <!-- <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc</artifactId>
        </dependency> -->
        ```

    *   Sync Maven changes and build the project.

4.  **Application Properties Configuration:**
    *   Open `application.properties`.
    *   Configure H2 database properties:

        ```properties
        spring.datasource.url=jdbc:h2:mem:testdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=
        spring.h2.console.enabled=true
        spring.jpa.hibernate.ddl-auto=create-drop
        ```

    *   Set the web application type to none:

        ```properties
        spring.main.web-application-type=none
        ```
        📝 **Note:** This is crucial for Stdio transport, preventing the MCP server from acting as a web server.

    *   Disable logging:

        ```properties
        logging.level.root=error
        ```
        ⚠️ **Warning:** Disabling logs is important to avoid interference with Stdio communication. The MCP server should not write unnecessary information to standard input or output.

    *   Disable the Spring Boot banner:

        ```properties
        spring.main.banner-mode=off
        ```

5.  **Java Code Implementation:**
    *   Create the following packages: `entity`, `repository`, `service`, and `model`.
    *   **Entity Package:**
        *   Create `HelpDeskTicket.java`:

            ```java
            // Example:
            @Entity
            @Table(name = "help_desk_tickets")
            public class HelpDeskTicket {
                // ... fields like id, username, issue, status, createdAt, eta ...
            }
            ```

    *   **Repository Package:**
        *   Create `HelpDeskTicketRepository.java`:

            ```java
            // Example:
            public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {
                List<HelpDeskTicket> findByUsername(String username);
            }
            ```

    *   **Service Package:**
        *   Create `HelpDeskTicketService.java`: This class will contain methods to create new tickets and query ticket details.

            ```java
            // Example:
            @Service
            public class HelpDeskTicketService {
                // ... methods to create and query tickets ...
            }
            ```

    *   **Model Package:**
        *   Create `TicketRequest.java`: A record class to accept issue details and username.

            ```java
            // Example:
            public record TicketRequest(String issue, String username) {}
            ```
            📝 **Note:** Unlike previous examples, the username is now part of the request because the tool context is not available in a separate MCP server.

    *   **Tool Package:**
        *   Create `HelpDeskTools.java`: This class defines the tools for creating tickets and getting ticket status.

            ```java
            // Example:
            @Component
            public class HelpDeskTools {
                // ... tools for creating and getting ticket status ...
            }
            ```

6.  **Configuration Package:**
    *   Create `config` package.
    *   Create `MCPserverConfig.java`: This class exposes the tools from the MCP server.

        ```java
        // Example:
        @Configuration
        public class MCPserverConfig {

            @Bean
            public List<ToolCallback> toolCallbacks(HelpDeskTools helpDeskTools) {
                return List.of(ToolCallbacks.from(helpDeskTools));
            }
        }
        ```
        💡 **Tip:** Use `ToolCallback` to expose tools from the MCP server. `ToolCallbackProvider` is used on the client side.

---

## 9. Building an MCP Server with STDIO Transport

This section outlines the steps to build an MCP server and integrate it with a client application using STDIO transport.

### Generating the JAR File 📦

1.  Ensure all necessary changes are implemented in the MCP software project.
2.  Open your terminal and navigate to the project directory. ⚠️ **Warning:** Make sure you are in the directory containing the `pom.xml` file. You can verify this by running `ls` (or `dir` on Windows) and confirming the presence of `pom.xml`.

    ```bash
    cd /path/to/your/mcp-server-project
    ls # or dir on Windows
    ```
3.  Execute the Maven command to generate the JAR file, skipping unit tests:

    ```bash
    mvn clean install -Dmaven.test.skip=true
    ```

    This command cleans the project, compiles the code, and packages it into a JAR file, skipping the unit tests.
4.  The generated JAR file will be located in the `target` folder within your project directory. The JAR name will be similar to `MCP-server-stdio-0.0.1-SNAPSHOT.jar`.

### Inspecting the MCP Server with MCP Inspector 🔎

1.  Start a new instance of MCP Inspector.
2.  Select the transport type as "Stdio".
3.  Set the command to `Java`.
4.  In the arguments, specify `-jar` followed by the complete path to your JAR file:

    ```
    -jar /path/to/your/mcp-server-project/target/MCP-server-stdio-0.0.1-SNAPSHOT.jar
    ```

5.  Click "Connect" to establish a connection between the MCP Inspector and your MCP server.
6.  Use the Inspector's tools to test the exposed functionalities. 📌 **Example:** List available tools and execute them by providing the required input parameters.

### Integrating with the MCP Client Application 🤝

1.  Open the JSON configuration file in your MCP client application (usually located in the `resources` folder).
2.  Add a new configuration for your MCP server. You can copy and paste the following snippet, adjusting the values to match your environment:

    ```json
    {
      "name": "spring-mcp",
      "transport": "stdio",
      "command": "/path/to/your/java/installation/java",
      "args": [
        "-jar",
        "/path/to/your/mcp-server-project/target/MCP-server-stdio-0.0.1-SNAPSHOT.jar"
      ]
    }
    ```

    *   `name`: A descriptive name for your MCP server configuration.
    *   `transport`: Set to `stdio`.
    *   `command`: The full path to your Java executable. Use `where java` in your terminal to find this path. ⚠️ **Warning:** This must be the full path, not just `java`.
    *   `args`: An array containing `-jar` and the full path to your JAR file.
3.  Save the changes to the JSON configuration file.
4.  Rebuild the MCP client application.

### Modifying the Controller Class ✍️

1.  Open the controller class in your MCP client application where requests are sent to the LM model.
2.  Add a `@RequestHeader` annotation to accept the `username` from the request header. This allows passing the username to the MCP server for tool invocation.

    ```java
    @RequestHeader(value = "username", required = false) String username
    ```

    📝 **Note:** Setting `required = false` makes the username optional for testing other integrations.
3.  Append the username to the message sent to the LM model:

    ```java
    message = message + " My username is " + username;
    ```
4.  Save the changes and rebuild the MCP client application.

### Testing the Integration ✅

1.  Restart the MCP client application. The application will automatically start the MCP server based on the configuration.
2.  Send a request to the LM model with a message that triggers a tool exposed by your MCP server.
3.  Include a `username` header in the request.
4.  Verify that the MCP server successfully invokes the tool and returns the expected response. 📌 **Example:** Send a message like "I am not able to access my cabin. The access card is not working." with the username "modern25".

### Key Considerations 🔑

*   Ensure Java is installed correctly and the `JAVA_HOME` environment variable is set.
*   Double-check all file paths in the configuration to avoid errors.
*   The MCP client and server communicate via STDIO, meaning they run on the same local system.
*   💡 **Tip:** Use a tool like Postman to easily send requests with custom headers.
*   ⌨️ **Shortcut:** `Ctrl + C` to terminate a running process in the terminal.

---

## 10. Creating an MCP Server with Remote Invocation

This note details the process of creating an MCP server that supports remote invocation using the Spring framework. We'll convert an existing MCP server based on Stdio transport to one that can be invoked remotely.

1.  **Copy and Rename the Project:** 
    *   Start by copying the existing MCP server Stdio project within the workspace.
    *   Rename the copied project to "MCP Server Remote".

2.  **Clean the Target Folder:**
    *   Open the "MCP Server Remote" folder.
    *   Delete the contents of the `target` folder to ensure fresh compilation.

3.  **Update `pom.xml`:**
    *   Open the `pom.xml` file.
    *   Modify the `<name>` tag from "MCP server Stdio" to "MCP server Remote".
    *   Update the `<description>` tag to "Demo project for MCP server using remote invocation".
    *   Save the `pom.xml` file.

4.  **Import the Project into IntelliJ:**
    *   In IntelliJ, go to `File` -> `New` -> `Module from Existing Sources`.
    *   Select the "MCP Server Remote" folder and click `Open`.
    *   Confirm that it's a Maven project.

5.  **Modify Dependencies in `pom.xml`:**
    *   Replace the `mcp-server-starter-stdio` dependency with `spring-ai-starter-mcp-server-webmvc`.
        ```xml
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
        </dependency>
        ```
    *   📝 **Note:** If you're building a Spring Reactive application, use the `webflux` dependency instead.
    *   Save the `pom.xml` file and sync the Maven changes.

6.  **Update `application.properties`:**
    *   Remove the following three properties related to Stdio transport:
        *   `spring.ai.mcp.server.stdio.enabled`
        *   `spring.ai.mcp.server.stdio.input`
        *   `spring.ai.mcp.server.stdio.output`
    *   Add the following properties:
        *   `logging.pattern.console=%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%-5p) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx`
        *   `server.port=8090`
    *   📝 **Note:**  The `server.port` can be any available port. 8080 is often used by other applications.
    *   Save the `application.properties` file.

7.  **Rename the Main Application Class and Package (Optional but Recommended):**
    *   Rename `MCP Server Stdio Application` to `MCP Server Remote Application`.
    *   Refactor the package name from `mcp.server.stdio` to `mcp.server.remote`.  Select "All in selected module" during refactoring.

8.  **Build the Project:**
    *   Build the project to ensure all changes are compiled.

9.  **Run the Application:**
    *   Run the Spring Boot application in debug mode.
    *   Verify that the MCP server starts on port 8090.

10. **Inspect with MCP Inspector:**
    *   Open the MCP Inspector.
    *   Select either `HTTP` or `SSC` as the transport type.
    *   Enter the URL: `http://localhost:8090/ssc`.
    *   Click `Connect`.
    *   ⚠️ **Warning:** The `Streamable` transport type may not be supported by the Spring framework at the time of this writing.
    *   Go to `Tools` -> `List Tools` to confirm the server is running and exposing tools.

11. **Integrate with MCP Client:**
    *   Create a new file named `mcp-servers-stdio.json` under the `resources` folder and move the Stdio based MCP server configurations to this file.
    *   Remove the Stdio based MCP server configurations from the original `mcp-servers.json` file.
    *   Add the following property to `application.properties`:
        *   `spring.ai.mcp.client.ssc.connections.easybytes.url=http://localhost:8090/ssc`
    *   📝 **Note:** Replace `easybytes` with a suitable connection name.
    *   📝 **Note:** In a real-world application, replace `localhost` with the actual hostname or IP address of the MCP server.

12. **Rebuild and Restart the Client:**
    *   Rebuild the MCP client application.
    *   Restart the MCP client application to load the new configurations.

13. **Test with Postman:**
    *   Send a request to the MCP client using Postman.
    *   📌 **Example:** Ask "What is the status of my ticket?" with a new username.
    *   Verify that the response is as expected.
    *   📌 **Example:** Send a message with issue details like "My account is locked".
    *   Verify that a new ticket is created.
    *   📌 **Example:** Ask "What is the status of my ticket?" again.
    *   Verify that the ticket details are returned in the response.

By following these steps, you can successfully create an MCP server that supports remote invocation and integrate it with an MCP client using the Spring framework. 💡 **Tip:** Remember to adjust the port numbers and URLs according to your specific environment.

---