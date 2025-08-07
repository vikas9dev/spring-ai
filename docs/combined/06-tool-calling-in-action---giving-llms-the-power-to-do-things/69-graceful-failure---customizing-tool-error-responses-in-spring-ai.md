## Handling Runtime Exceptions in Tools

Let's explore what happens when a runtime exception occurs during the execution of a tool.

*   If your tool throws an error, Spring AA wraps it in a **Tool Execution Exception**.
*   By default, these exception details are sent as part of the message to the Language Model (LM).
*   The LM uses these details to provide a meaningful response to the client application.

However, you might want to throw the exception directly to the client application or end-user. In such cases, you need to create a bean of `ToolExecutionExceptionProcessor`.

*   This is an interface with an implementation class.
*   When creating the object of this implementation class, pass the Boolean value `true` to the constructor.
*   This ensures that any exception details inside the tool are sent directly to the client application.

Currently, we rely on the default bean configurations provided by the framework.

*   The Spring Boot framework and the Spring framework create these beans behind the scenes.
*   They create them with the Boolean value set to `false`.
*   With this `false` setting, the exception details are sent to the LM, which then sends a different message to the client application.

In most scenarios, the default behavior is sufficient.

⚠️ **Warning:** Avoid sending exception details directly to the client application unless absolutely necessary.

*   If the client is more interested in the actual exception details and has implemented efficient handling logic on their side, you can configure the `true` Boolean value.

Let's look at a demo of this in tool execution.

First, we'll examine the default behavior inside the `getTicketStatus` method.

📌 **Example:** Throwing a runtime exception:

```java
throw new RuntimeException("Unable to fetch ticket status");
```

When invoking the REST API with a message like "What is the status of my ticket for this username?", and a ticket already exists, the default response might be: "I'm currently unable to fetch the status of your ticket. Would you like me to try again or assist you with something else?"

This is because the LM received the runtime exception details from the tool execution.

Now, let's configure the application to send these exception details directly to the client.

Instead of sending to the LM, we need to create a new bean in the class where the chat client bean is created.

📌 **Example:** Creating a `ToolExecutionExceptionProcessor` bean:

```java
@Bean
public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
    return new DefaultToolExecutionExceptionProcessor(true);
}
```

Here, the Boolean value `true` indicates that we want to throw the exception back to the client.

After building and restarting the application, sending the same message should now result in the exception details being sent directly.

The response will be a 500 status with an "Internal Server Error" and the path that was invoked.

⚠️ **Warning:** In most scenarios, it's not recommended to throw exception details directly to client applications. Unless they handle them gracefully, it can lead to a bad user experience.

📝 **Note:** The default behavior is generally preferred.

Since we are done with the demo, let's comment out the code changes.

📌 **Example:** Commenting out the exception throwing and bean configuration:

```java
// throw new RuntimeException("Unable to fetch ticket status");
```

```java
// @Bean
// public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
//     return new DefaultToolExecutionExceptionProcessor(true);
// }
```

After building and restarting again, sending the same message should now result in a proper response, such as: "Your ticket regarding the issue is currently open and this is the estimated time."
