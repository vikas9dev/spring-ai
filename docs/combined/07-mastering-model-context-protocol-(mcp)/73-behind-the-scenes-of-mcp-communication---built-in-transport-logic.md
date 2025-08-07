## MCP Transport Types: STDIO, Streamable HTTP, and SSE

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
    *   An older transport type for streaming data from the server to the client.
    *   Now part of Streamable HTTP for backward compatibility.
    *   May be completely removed in future versions of MCP.
    *   Useful when only one-way streaming was needed (server to client) and network bandwidth was limited.
    *   📌 **Example:** Sending live stock prices.

### STDIO vs. Streamable HTTP

Here's a comparison of SSE and Streamable HTTP:

| Feature                       | SSE                               | Streamable HTTP                      |
| ----------------------------- | --------------------------------- | ------------------------------------ |
| Server to Client Communication | Supported                         | Supported (optionally uses SSE)      |
| Client to Server Communication | Not Supported                     | Supported (using HTTP POST)          |
| Session Management            | Not Supported                     | Supported (using MCP session ID headers) |
| Two-Way Communication         | Not Supported                     | Supported                            |
| Multiple Concurrent Clients   | Limited Support                   | Better Support                       |
| Compatibility                 | Limited                           | Improved                             |

Due to the limitations of SSE, **Streamable HTTP is the recommended transport type** for establishing communication between the MCP client and server.

### Visual Representation

*   **Local Deployment:** If both MCP servers are deployed locally, STDIO is used.
*   **Remote Deployment:** If MCP servers are deployed remotely, Streamable HTTP is used.

### Additional Resources

*   **Official MCP Website:** This is the go-to resource for the latest information on MCP.
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
