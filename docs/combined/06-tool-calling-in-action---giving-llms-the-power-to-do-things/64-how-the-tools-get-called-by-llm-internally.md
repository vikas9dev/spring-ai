## Understanding How Language Models Call Tools

Let's explore how Language Models (LMs) interact with tools. It might seem like Spring is directly exposing our tool logic as an endpoint for OpenAI, but that's not the case. Instead, a conversation takes place between our Spring application and the LM.

Here's a breakdown of the flow when tools are involved:

1.  **User Prompt:** 🧑‍💻 The end user sends a prompt message. The message type is "user." If there's a system message, it's sent along as well.
2.  **Tool Details to LM:** ⚙️ The Spring AI framework sends tool details and definitions to the LM, providing context. This helps the LM understand when to request tool invocation.
3.  **LM Decision:** 🤔 The LM analyzes the prompt and determines if a tool is needed. If not, it provides a direct answer. If a tool is required, it proceeds to the next step.
4.  **Assistant Message with Tool Instructions:** 🤖 The LM sends a chat message to the Spring AI application (client) using the "assistant" message type. This message contains clear instructions on which tool to execute.
    *   The `finish_reason` is set to `tool_calls`. This indicates that the LM is waiting for a response from the tool execution.
    *   ⚠️ **Warning:** Usually, `finish_reason` is "stop," meaning the LM is done. "tool_calls" signifies an ongoing conversation.
5.  **Tool Execution:** 🛠️ The Spring AI application takes the instructions and executes the required tool logic.
6.  **Tool Message with Outcome:** 📤 The outcome of the tool execution is populated in a "tool" message.
    *   The message role is "tool."
    *   The client application sends the question details along with the tool's response.
7.  **LM Response Preparation:** 🧠 Based on the tool's response, the LM prepares the final response.
8.  **Final Assistant Message:** 💬 The LM sends the response back to Spring AI as an "assistant" message. The `finish_reason` is "stop."
9.  **Response to User:** 🚀 The Spring AI application forwards the response to the end user.

This entire process happens behind the scenes.

Here's another way to visualize the tool execution:

*   **AI Model:** The Language Model itself.
*   **Spring AI Framework:** Our Spring AI code.
*   **Tool Calling Manager:** An interface within the Spring AI framework responsible for invoking tools based on LM instructions.

The flow is as follows:

1.  The chat request, including the prompt and tool definitions, is sent to the LM. The framework extracts tool details (definitions, names) from the classes where the tool methods are defined.
2.  The LM decides whether to invoke a tool.
3.  If a tool is needed, the LM forwards instructions to Spring AI.
4.  The Tool Calling Manager implementation intercepts these instructions.
5.  The Tool Calling Manager invokes the actual tool and receives the response.
6.  The response is sent back to the AI model.
7.  The AI model prepares the final response and sends it to the end user.

### Demo and Code Walkthrough

Let's look at a demo to clarify the process.

First, we'll invoke a REST API *without* tool calling.

📌 **Example:** Asking "Tell me about notice period" returns "The minimum notice period for resignation is 36 days."

In the console logs, the response from the LM has `finish_reason: stop`. This is because the LM could answer the question directly using the provided document information, without needing any tools.

Now, let's examine the tool calling scenario.

We'll use the `DefaultToolCallingManager` class, which implements the `ToolCallingManager` interface. This class manages tool execution by default. You can customize this behavior by creating your own implementation.

Inside the `executeToolCalls` method, we'll set a breakpoint to observe the process.

📌 **Example:** Asking "What is the local time in London?"

The breakpoint hits inside `DefaultToolCallingManager`. The `chatResponse` object contains:

*   A `generations` object.
*   An "assistant" message with tool call information. This includes instructions to invoke the `getCurrentTime` function with the timezone "Europe/London".
*   The `type` is "function" because we're using a Java method.
*   The `finish_reason` is `tool_calls`.

Debugging the method reveals:

1.  The code identifies the tool the LM wants to execute.
2.  It retrieves the assistant message and extracts the tool context.
3.  It invokes the `executeToolCall` method.

Inside `executeToolCall`:

1.  The code fetches tool callback details. The Spring framework maintains definitions for tools like `getCurrentTime` and `getCurrentLocalTime`.
2.  It iterates through the tool calls received from the assistant message. In this case, there's only one.
3.  The tool name is `getCurrentTime`, and the input argument is the timezone.
4.  The framework executes the Java method with these details.
5.  The method returns a `ToolResponse` message.

The `ToolResponse` contains:

*   A message type of "tool".
*   The response data: the current time in London.

Using this response data, the LM prepares a more detailed answer.

Back in Postman, the final response is: "The current time in London is 10:41 a.m."

📝 **Note:** The conversation between the LM and the client application regarding tool execution is *not* logged or saved in the conversation history. This is because these are internal conversations, and logging them could lead to performance issues, especially with multiple tool executions and dependencies.

💡 **Tip:** If you have questions, debug the `DefaultToolCallingManager` class to understand the flow in detail.
