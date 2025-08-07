## MCP: Standardizing Context for LLMs

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
