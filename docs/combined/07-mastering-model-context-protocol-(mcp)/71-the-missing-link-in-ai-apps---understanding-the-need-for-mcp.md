## MCP: Model Context Protocol Explained

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
