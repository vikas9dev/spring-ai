## Exploring Tool Calling with Language Models

After restarting the application, a new table should have been created in the H2 database. Let's verify this.

Navigating to the H2 console, we can see a new table named `helpdesk_tickets` has been created.

Querying this table currently returns no records, but the columns are based on the fields defined in our entity class.

Now, let's test our new REST API endpoint, `help_desk`, using Postman.

First, we'll invoke the API by asking the LLM a question: "What is the status of my ticket?"

Even though no tickets have been created yet, the LLM should invoke our tool to query the database for existing issues for the user.

To better understand the process, we'll add breakpoints and logger statements to the relevant methods.

```java
// Example logger statement
logger.info("Creating support ticket for user with details: {}", toolContext.getUserName());
logger.info("Ticket created successfully with ID: {} for user: {}", ticket.getId(), toolContext.getUserName());
```

We'll add a logger statement to the second method as well:

```java
// Example logger statement
logger.info("Fetching tickets for user: {}", userName);
logger.info("Found {} tickets for user.", tickets.size());
```

After adding the logger statements and breakpoints, we restart the application.

Let's populate the username as "more than 22". This is a new user, so there should be no existing records.

Asking "What is the status of my ticket?" should trigger the LLM to request the Spring application to invoke one of our exposed tools. The breakpoint should hit.

Debugging shows the username fetched from the context is indeed "more than 22". The database query returns empty results.

The LLM responds: "It appears that you don't have any existing tickets. If you need assistance with an issue, please let me know. I can create a new support ticket on your behalf." This is the expected behavior.

Next, let's send the message: "I am not able to log in into my account. Seems the account is locked."

The LLM first checks for open tickets for the user. Since none exist, it responds: "It seems that you don't have any existing tickets related to your account login issue. I can create a new support ticket for you to address the logged account. Would you like me to proceed with that?"

Responding with "yes" results in: "I have successfully created a new support ticket for your issue, unable to log in into account. Account seems to be logged. Your ticket number is three. You will receive updates regarding your ticket shortly. If you have any other questions or need further assistance, feel free to ask."

Querying the `helpdesk_tickets` table now shows a new record with the logged issue and a status of "open" for the username "more than 22".

This demonstrates the power of tool calling! 🚀 The logic can be anything: sending emails, triggering APIs, or starting jobs. The possibilities are endless.

Let's ask the same question again: "I am not able to login into my account since the account is locked."

The response is: "Your existing ticket regarding the locked account is still open. The estimated time for a resolution is July 18th. If you need further assistance or have any other questions, feel free to ask."

The LLM doesn't create a duplicate ticket because the prompt template instructs it not to.

Asking "What is the status of my ticket?" returns: "Your ticket regarding the locked account is currently open. This is the so and so estimated time. If you have any further questions or need assistance, feel free to ask."

Using the tools we've developed, the LLM can perform actions that are far more powerful than simple RAG applications.

RAG applications are useful for answering questions based on private documents or information. However, tool calling allows LLMs to execute actions autonomously.

A common question is: How are Java objects converted to a format that the LLM can understand (JSON) when sending responses? And how is the JSON object converted into a Java object?

The Spring Framework handles this conversion. ⚙️

There are interfaces like `tool call result converter`. The primary responsibility of this interface is to convert tool call results to a string that can be sent back to the AI model.

```java
// Example of a tool call result converter interface
public interface ToolCallResultConverter {
    String convert(Object result);
}
```

📌 **Example:** There's an implementation class for this interface that uses a JSON parser to convert a given result object into JSON. You can set a breakpoint inside this class to understand the process.

I hope this demo was helpful. We'll discuss more advanced topics around tool calling in the next lecture.
