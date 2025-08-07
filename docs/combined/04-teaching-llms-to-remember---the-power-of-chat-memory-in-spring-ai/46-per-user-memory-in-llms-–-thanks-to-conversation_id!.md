## Configuring Conversation ID for REST API

This section explains how to configure a conversation ID for a REST API, ensuring that interactions are unique to each end user. This is crucial for maintaining context and providing personalized experiences.

The REST API will be invoked by:

*   UI applications
*   Client applications
*   Backend applications

Regardless of the invoker, we need to ensure that each user has a unique identifier.

We'll request clients to pass a unique identifier specific to the end user. This could be:

*   Username
*   Mobile number
*   Email
*   Any other unique value

For this demo, we'll use the **username** as the conversation ID.

The client applications will send this username as an input in two ways:

1.  Request parameter
2.  Request header

Specifically, the header name will also be **username**. The value associated with this header will be the unique identifier for the user. This ensures that each end user has a unique conversation ID.

Here's how to configure the conversation ID:

1.  Invoke the `advisors` method. We'll use the overloaded version that accepts an advisor specification in the form of a consumer lambda expression.

    ```java
    advisorSpec -> {
        // Configuration here
    }
    ```

2.  Inside the lambda expression, invoke the `param` method (singular, not `params`).

3.  Pass the conversation ID key along with the value. To get the conversation ID key, refer to the `chat memory`. There's a constant named `conversation ID` that holds the key value.

    📌 **Example:** Instead of directly pasting the value, import the `conversation ID` constant into the controller class.

    ```java
    import com.example.chat.ChatMemoryConstants.conversationID;
    ```

4.  Use the imported `conversationID` constant as the key in the `param` method. The value will be the username received from the client application.

    ```java
    advisorSpec.param(conversationID, username);
    ```

    This is the key configuration step. The framework will handle storing and retrieving messages based on this unique conversation ID.

After building, the framework uses a concurrent hash map in the in-memory chat memory repository to store chat messages based on the conversation ID.

To test this:

1.  Set a breakpoint inside the `findByConversationID` method in the repository. This allows you to observe the behavior of the framework.
2.  When invoking the REST API, ensure you send a header with the key `username` and the corresponding username as the value.

    ⚠️ **Warning:** Ensure the username is in the **headers**, not the parameters.

    📌 **Example:**

    *   Username: `madan01`
    *   Message: `My name is Madan`

    Then, send another message with a different username:

    *   Username: `madan02`
    *   Message: `My name is John Doe`

    Finally, send another message with the first username, but a different question:

    *   Username: `madan01`
    *   Message: `What is my name?`

3.  Observe the breakpoint. You'll see a HashMap called `chatMemoryStore`. Under `madan01`, you'll find messages related to that user, and similarly for `madan02`.

This demonstrates how the framework stores messages based on the unique conversation ID.

💡 **Tip:** In enterprise applications, always configure a unique conversation ID for each end user. This can be any unique identifier.

📝 **Note:** ChatGPT uses a similar approach, creating a session ID specific to each user to remember the context of previous chat messages.
