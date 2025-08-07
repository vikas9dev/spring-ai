## Understanding Message Roles in LLMs

So far, we've built a simple Spring AI application that sends prompts and receives responses from LLM models like OpenAI and Ollama. This is a good start, but it only scratches the surface of what Spring AI can do.

Spring AI offers many advanced concepts that can help us make our enterprise Spring Boot applications more intelligent by integrating with LLM models. In the coming lectures, we'll explore these concepts, including:

- Message roles
- Advisors
- Prompt templates
- Chat history management
- Function calling
- Building MCP clients and servers

By the end of this course, we'll unlock the full potential of generative AI with Spring AI.

Let's focus on message roles in this section.

### What are Message Roles?

When interacting with LLMs used by applications like ChatGPT, we send prompts made up of messages. Each message has a **role** that helps the LLM understand the context and how to respond. By using these roles, we provide more meaningful information to the LLM, leading to better responses. 🤖

These are the most common roles supported by most LLMs:

- User
- System
- Assistant
- Function

Let's explore each of these in more detail.

### Common Message Roles

1.  **User Role:** This is the default role for messages sent by the user. 📌 **Example:** "Tell me a joke."
2.  **System Role:** This role allows us to provide instructions to the LLM on how it should behave. 📌 **Example:** "Be formal and professional when responding."
3.  **Assistant Role:** This role is assigned to the LLM's responses to user questions and system instructions. These models are assistants to humans, hence the name.
4.  **Function Role:** This role allows us to give special instructions on how to run a function or fetch data from our storage systems. This is related to the advanced concept of **function calling**. With function calling, we can expose functions or tools to the LLM models, which they can invoke to read data from our storage systems. We'll explore function calling in more detail later.

📌 **Example:** Before starting a chat with an LLM:

-   **System Role:** "You are a friendly tour guide."
-   **User Role:** "What are the top three places to visit in Rome?"
-   **Assistant Role:** (The LLM's response listing the top three places).

### Analogy: Message Roles as a Stage Play

To better understand message roles, consider a stage play:

-   **System Role:** The director giving stage instructions (e.g., "Act like a professional chef").
-   **User Role:** The audience asking a question (e.g., "How do I cook pasta?").
-   **Assistant Role:** The actor replying to the question (e.g., "Boil water and add pasta for eight minutes").
-   **Function Role:** A backstage helper fetching the required ingredients or recipes.

### LLM Support for Message Roles

⚠️ **Warning:** Not all LLM models support all message roles.

-   **OpenAI:** Supports all roles (user, system, assistant, and function).
-   **Anthropic:** Supports user, system, and assistant roles, but not function.
-   **Google Gemini:** Supports only user and assistant roles (the assistant role is sometimes called the "model" role).

Based on the LLM you're using, you need to understand which message roles are supported and build your business logic accordingly.

💡 **Tip:** Spring AI can help you manage these differences. For example, if you send a system role message to Google Gemini (which doesn't support it), Spring AI will combine the system message into the user message behind the scenes.

This is all the theory for now. In the next lecture, we'll write some code to send various message roles to an LLM. 🚀
