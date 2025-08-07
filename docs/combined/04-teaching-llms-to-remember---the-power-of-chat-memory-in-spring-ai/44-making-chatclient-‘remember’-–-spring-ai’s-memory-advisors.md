## Chat Memory Strategies and Spring Framework Advisors

By implementing the chat memory interface, we can define the strategy for storing and retrieving chat messages.

📌 **Example:** Message Window Chat Memory maintains a maximum of 20 historical chat messages. Older messages are discarded.

The implementation class includes methods like `add` and `get` that interact with the chat memory repository.

📌 **Example:** When the `get` method is invoked, it calls the `findById` method in the in-memory chat memory repository.

But who invokes these methods? Someone needs to call the `get`, `add`, or `clear` methods exposed by the chat memory implementation class, process the list of messages, and populate the request sent to the LM models.

In Spring Framework, **advisors** handle this responsibility. They intercept requests and responses to and from the LM models. Spring provides three memory advisors for integrating chat memory functionality into the chat client bean:

1.  Message Chat Memory Advisor
2.  Prompt Chat Memory Advisor
3.  Vector Store Chat Memory Advisor

### 1. Message Chat Memory Advisor

When configured, this advisor interacts with the chat memory implementation and repository to fetch messages. It stores messages as structured messages, categorized as user, system, and assistant messages.

The advisor injects past messages directly into the prompt, preserving the message roles.

📌 **Example:** If the chat memory has four user messages, one system message, and three assistant messages, the advisor populates them into their respective message categories.

This advisor is best when the LM needs to see the full chat history with message roles, like a real chat log.

### 2. Prompt Chat Memory Advisor

When configured, this advisor converts the entire chat memory into plain text, regardless of the message role. This text is appended to the system prompt, acting as a summary.

This advisor is suitable for simpler LMs that don't support different role messages.

📌 **Example:** For LMs without separate user role messages, this advisor is recommended.

### 3. Vector Store Chat Memory Advisor

Consider a user who has interacted with the system for years, resulting in thousands of chat messages. Sending only the last 20 messages might not provide enough context, especially if the user's question relates to an older message.

This advisor addresses this scenario.

⚠️ **Warning:** This advisor requires a vector database, not a traditional SQL or NoSQL database.

Vector databases are designed for storing and querying large amounts of data using natural language.

📌 **Example:** Store thousands of chat messages in a vector database. When a user asks a question, the same question is used to query the database. The database returns the most relevant messages (e.g., ten messages).

These relevant messages are then sent to the LM model. This allows you to maintain an unlimited number of chat messages while sending only the most relevant ones to the LM for accurate responses.

Vector databases use embeddings, similar to LM models.

This advisor is ideal for long or knowledge-based conversations.

📝 **Note:** A demo of this advisor will be provided later when discussing Retrieval Augmented Generation (RAG). This will include details on configuring vector databases, storing data, and retrieving data.

The Message Chat Memory Advisor, which sends historical messages in a structured format, is sufficient for most enterprise scenarios and will be used in upcoming demos.

The next step is to implement chat memory functionality in a Spring Boot application.
