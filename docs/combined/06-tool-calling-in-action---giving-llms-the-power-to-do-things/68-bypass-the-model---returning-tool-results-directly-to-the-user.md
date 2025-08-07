## Using Return Direct for Tool Execution

When a tool is executed, the default behavior is to send the tool's response back to the Language Model (LM). However, there are scenarios where you might want to skip this step and send the output directly to the client application or end-user. This can be achieved using the `Return Direct` configuration.

To enable this, you need to use the `tools` annotation and set the `Return Direct` flag to `true`. By default, it is set to `false`.

📌 **Example:** Tool annotation with `Return Direct` set to `true`:

```java
@Tool(name = "MyTool", description = "A sample tool", returnDirect = true)
public String myToolMethod(String input) {
    // Tool logic here
    return "This is the tool's direct output.";
}
```

When `Return Direct` is set to `true`, the output from the tool will be sent directly to the client application or end-user, bypassing the LM.

### When to Use `Return Direct` 🤔

Use this configuration in the following scenarios:

*   When the tool generates a final and human-readable message that you don't want the model to alter, summarize, or continue reasoning after the tool execution.
*   When you want to reduce latency by skipping the post-processing steps on the LM model.
*   When the tool's output is already suitable for direct presentation to the end-user.

### How to Configure `Return Direct` ⚙️

Similar to how you populate the `name` and `description` properties in the tool annotation, you can also set the `Return Direct` flag.

```java
@Tool(name = "Create Ticket", description = "Creates a support ticket", returnDirect = true)
public String createTicket(String issue) {
    // Logic to create a ticket
    String ticketId = "TKT-12345"; // Example ticket ID
    return "A new ticket has been created with ID: " + ticketId;
}
```

Once the application is built and restarted with this configuration, any output from the `Create Ticket` tool will be sent directly to the client application.

### Behind the Scenes 🕵️‍♀️

When the LM model is invoked, the framework provides instructions to the LM about the available tools and how to use them. This information is shared with the LM in the form of a JSON schema.

For example, the schema for the `Create Ticket` tool might look like this:

```json
{
  "type": "object",
  "properties": {
    "ticketRequest": {
      "type": "object",
      "properties": {
        "issue": {
          "type": "string",
          "description": "Description of the issue"
        }
      },
      "required": [
        "issue"
      ]
    }
  }
}
```

The LM model uses this schema to send requests to the tool in a JSON format. The framework then converts this JSON request into a Java object before invoking the tool's method.

### Demonstration 🎬

When a user submits a request that triggers the `Create Ticket` tool with `Return Direct` set to `true`, the tool's output is directly sent back to the user's application (e.g., Postman) without any modification by the LM.

📝 **Note:** This configuration is useful when you want the tool's result to be sent directly to the client application and bypass the LM model.
