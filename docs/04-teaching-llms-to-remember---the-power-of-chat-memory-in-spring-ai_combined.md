# 04 Teaching Llms To Remember   The Power Of Chat Memory In Spring Ai


Sections-
1. [Understanding the Stateless Nature of LM Models](#1-understanding-the-stateless-nature-of-lm-models)
2. [Spring Chat Memory](#2-spring-chat-memory)
3. [Chat Memory Strategies and Spring Framework Advisors](#3-chat-memory-strategies-and-spring-framework-advisors)
4. [Configuring Chat Memory for a REST API](#4-configuring-chat-memory-for-a-rest-api)
5. [Configuring Conversation ID for REST API](#5-configuring-conversation-id-for-rest-api)
6. [Storing Chat Messages with JDBC](#6-storing-chat-messages-with-jdbc)
7. [Customizing Chat Memory Bean Configuration](#7-customizing-chat-memory-bean-configuration)
8. [Understanding Max Messages and Context Window Size in LM Models](#8-understanding-max-messages-and-context-window-size-in-lm-models)


---

## 1. Understanding the Stateless Nature of LM Models

This section explores the stateless nature of Large Language Models (LLMs) and how it impacts conversational applications. We'll see a demo illustrating this limitation and set the stage for how Spring AI can help address it.

Imagine the movie "Before I Go to Sleep," where the protagonist wakes up each day with no memory of the past. 🎬 This is analogous to how LLMs function by default.

### The Problem: LLMs Don't Remember

*   By default, LLMs are **stateless**. This means they don't retain information from previous interactions.
*   Each interaction is treated as a brand new conversation. 🗣️
*   This can be a significant issue when building applications that require maintaining context across multiple turns, such as chatbots. 🤖

### Demo: Demonstrating Statelessness with Spring AI

To illustrate this, let's create a simple Spring AI application.

1.  **Create a `ChatClient` Bean:**

    We'll create a basic `ChatClient` bean that interacts with an LLM. This configuration intentionally avoids any memory management.

    ```java
    @Configuration
    public class ChatMemoryChatClientConfig {

        @Bean(name = "chatMemoryChatClient")
        public ChatClient chatMemoryChatClient(ChatClient.Builder chatClientBuilder) {
            return chatClientBuilder.build();
        }
    }
    ```

    📝 **Note:** We name the bean `chatMemoryChatClient` to avoid conflicts with other `ChatClient` beans in the project.

2.  **Inject the `ChatClient` into a Controller:**

    We'll create a REST controller to expose an endpoint that uses the `ChatClient`. Add lombok dependencies to the project.

    ```java
    @RestController
    @RequestMapping("/api")
    @RequiredArgsConstructor
    public class ChatMemoryController {

        private final ChatClient chatMemoryChatClient;

        @GetMapping("/chat-memory")
        public ResponseEntity<String> chatMemory(@RequestParam String message) {
            return ResponseEntity.ok(chatMemoryChatClient.prompt().user(message).call().content());
        }
    }
    ```

    📌 **Example:** The `@Qualifier` annotation is used to specify which `ChatClient` bean to inject, since we have multiple defined. However, we have directly used the @Bean("name") so we can use `private final ChatClient chatMemoryChatClient` along with `@RequiredArgsConstructor`. See more here:- [https://chatgpt.com/share/68ae4ef8-bf68-8005-b393-b22920ea501d](https://chatgpt.com/share/68ae4ef8-bf68-8005-b393-b22920ea501d)

3.  **Test the API:**

    *  Start your Spring AI application. Go to the home directory of the project and run the application:
    ```bash
    mvn clean install
    OPENAI_API_KEY=sk-xxxxxx mvn spring-boot:run
    ```
    *  Use Postman (or a similar tool) to send a GET request to your API endpoint.
    *  Include a `message` query parameter with your prompt. 📌 **Example:** `http://localhost:8080/api/chat?message=My name is Rakesh`
    *   Send a message to the API: "My name is Rakesh."
    *   The LLM responds with a generic greeting.
    *   Then, ask "What is my name?": `{{baseURL}}/api/chat-memory?message=What is my name?`
    *   The LLM responds that it doesn't know your name. 🤯

    This demonstrates that the LLM has no memory of the previous interaction.

### ChatGPT vs. Our Demo: Why the Difference?

You might be wondering why ChatGPT *does* remember previous conversations, even though it's also based on an LLM.

*   ChatGPT is a **wrapper application** around the core LLM. 🎁
*   The developers of ChatGPT have implemented mechanisms to maintain conversation history and feed it back into the LLM with each new interaction.
*   We need to build something similar using Spring AI to achieve conversational memory in our applications.

### Next Steps

In the following sections, we'll explore how Spring AI can help us solve this problem and build applications that can maintain conversation context. 🚀

---

## 2. Spring Chat Memory

Spring uses the concept of **chat memory** to enable Language Models (LLMs) to behave more like humans by remembering past conversations and improving responses. 🧠

Here's how it works:

*   When a user starts a conversation, the details are stored in a storage system. 💾
*   These stored messages are appended to new messages sent by the user. ➕
*   The LLM then receives the entire context of the conversation by reading the previous chats. 📖
*   This technique is similar to what's used by other LLM wrapper applications like ChatGPT. 💬

Spring provides interfaces to manage chat memory:

*   **Chat Memory Interface:** Defines the memory strategy. 📝
    *   Options include:
        *   Keeping the last *n* messages.
        *   Storing messages for a specific time window. ⏳
        *   Retaining messages until a token limit is reached. 🪙
    *   You can define custom memory strategies by implementing this interface.

*   **Chat Memory Repository Interface:** Defines how to store and retrieve messages from a storage system. 💾

### Chat Memory Interface Details

The `ChatMemory` interface defines a contract for storing and managing chat conversation memory. 🤝

*   By default, when storing messages, it assigns a default conversation ID. 🆔
*   In real-world applications, you'll want to use unique identifiers like username, email, or mobile number as the conversation ID. 🔑
*   Methods include `add`, `clear`, and `get` for managing messages. ➕ 🗑️ 📤

📌 **Example:**

```java
public interface ChatMemory {
    void add(String conversationId, List<Message> messages);
    List<Message> get(String conversationId);
    void clear(String conversationId);
}
```

The Spring framework provides a sample implementation class called `MessageWindowChatMemory`.

*   It retains the last 20 messages by default. 🔢
*   If the conversation exceeds 20 messages, older messages are evicted. ⚠️
*   System messages are treated specifically:
    *   If a new system message is added, previous system messages are removed. 🗑️
    *   System messages are preserved during eviction. 🛡️

The `add` method within `MessageWindowChatMemory` uses the `ChatMemoryRepository` to store messages.

1.  It retrieves existing messages using `findByConversationId`. 📤
2.  It processes the messages to check if they are within the limit. ✅
3.  If within the limit, the new message is added; otherwise, eviction occurs. 🗑️
4.  The updated list of messages is saved using the `saveAll` method. 💾

The `get` method simply loads all chat messages based on the conversation ID using `findByConversationId` from the `ChatMemoryRepository`. 📤

💡 **Tip:** While `MessageWindowChatMemory` is suitable for many scenarios, you can create your own implementation for unique requirements, using it as a reference. 🧑‍💻

### Chat Memory Repository Interface Details

The `ChatMemoryRepository` interface defines a contract for storing and retrieving chat messages from a storage system. 💾

*   It's similar to a JPA repository.
*   It includes methods to fetch, save, and delete messages. ➕ 🗑️ 📤

📌 **Example:**

```java
public interface ChatMemoryRepository {
    List<String> findConversationIds();

    List<Message> findByConversationId(String conversationId);

    void saveAll(String conversationId, List<Message> messages);

    void deleteByConversationId(String conversationId);
}
```

Spring provides an `InMemoryChatMemoryRepository` implementation.

*   It uses a `ConcurrentHashMap` as an in-memory storage. 🧠 `Map<String, List<Message>> chatMemoryStore = new ConcurrentHashMap();`
*   It saves conversation messages using the `put` method. ➕
*   It deletes messages using the `remove` method. 🗑️
*   It retrieves messages using the `get` method. 📤

Spring also provides a `JdbcChatMemoryRepository` for storing chat memories in databases like MySQL or PostgreSQL. 🗄️

💡 **Tip:** You can create custom implementations for NoSQL databases like MongoDB, using the provided implementations as a reference. 🧑‍💻

---

## 3. Chat Memory Strategies and Spring Framework Advisors

By implementing the chat memory interface, we can define the strategy for storing and retrieving chat messages.

📌 **Example:** `MessageWindowChatMemory` maintains a maximum of 20 historical chat messages. Older messages are discarded.

- The implementation class includes methods like `add` and `get` that interact with the chat memory repository.
- When the `get` method is invoked, it calls the `findById` method in the in-memory chat memory repository.
- But who invokes these methods? Someone needs to call the `get`, `add`, or `clear` methods exposed by the chat memory implementation class, process the list of messages, and populate the request sent to the LM models.

In Spring Framework, **advisors** handle this responsibility. They intercept requests and responses to and from the LM models. Spring provides three memory advisors for integrating chat memory functionality into the chat client bean:

1.  `MessageWindowChatMemory`
2.  `PromptChatMemoryAdvisor`
3.  `VectorStoreChatMemoryAdvisor`

### 1. MessageWindowChatMemory

When configured, this advisor interacts with the chat memory implementation and repository to fetch messages. It stores messages as structured messages, categorized as user, system, and assistant messages.

The advisor injects past messages directly into the prompt, preserving the message roles.

📌 **Example:** If the chat memory has four user messages, one system message, and three assistant messages, the advisor populates them into their respective message categories.

This advisor is best when the LM needs to see the full chat history with message roles, like a real chat log.

### 2. PromptChatMemoryAdvisor

When configured, this advisor converts the entire chat memory into plain text, regardless of the message role. This text is appended to the system prompt, acting as a summary.

This advisor is suitable for simpler LMs that don't support different role messages.

📌 **Example:** For LMs without separate user role messages, this advisor is recommended.

### 3. VectorStoreChatMemoryAdvisor

_You wont find this class in Spring AI. **Vector related database dependency required for this**._

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

---

## 4. Configuring Chat Memory for a REST API

This section details how to configure chat memory capabilities for a REST API named "chat-memory."

We will integrate necessary advisors and chat memory beans into the chat client bean.

### Chat Memory Bean Configuration ⚙️

1.  **Inject Chat Memory Bean:** Inject a bean of type `chatMemory` into the constructor of the class where the chat client bean is created.

    ```java
    @Bean("chatMemoryChatClient")
    public ChatClient chatMemoryChatClient(
        ChatClient.Builder chatClientBuilder, 
        ChatMemory chatMemory) {
        // ..
    }
    ```

2.  **Spring Framework Handling:** Spring Framework automatically creates a bean of `chatMemory` implementation. Currently, the framework has a single bean of type `MessageWindowChatMemory`.

3.  **Dependency Injection:** The `MessageWindowChatMemory` class depends on a bean of type `ChatMemoryRepository`. If there's only one implementation class in the classpath, a bean of that class will be injected into the `MessageWindowChatMemory` bean.

### Advisor Configuration 🧑‍💻

1.  **Create Advisor Objects:** Create objects of the required advisors. There are three types of advisors available:
    *   `MessageChatMemoryAdvisor`
    *   `PromptChatMemoryAdvisor`
    *   (Vector store advisor - not covered here due to missing dependencies)

2.  **Message Chat Memory Advisor:** Create an object of `MessageChatMemoryAdvisor`.

    ```java
    Advisor memoryAdvisor = MessageChatMemoryAdvisor
            .builder(chatMemory)
            .build();
    ```

3.  **Logger Advisor:** Configure a logger advisor to log requests and responses.

    ```java
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    ```

4.  **Configure Advisors in Chat Client:** Use the `defaultAdvisors` method of the chat client builder to configure the advisors. Pass the advisors as a list.

    ```java
    ChatClient chatClient = ChatClient.builder()
            .defaultAdvisors(
                List.of(loggerAdvisor, memoryAdvisor)
            )
            .build();
    ```
5. Overall it should look like this:
    ```java
    @Bean("chatMemoryChatClient")
    public ChatClient chatMemoryChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor
            .builder(chatMemory)
            .build();
        return chatClientBuilder
        .defaultAdvisors(
            List.of(loggerAdvisor, memoryAdvisor)
        ).build();
    }
    ```

### Testing the REST API 🧪

1.  **Initial Interaction:** Ask "What is my name?". The API should respond with "I'm sorry, but I don't know your name. How can I assist you today?".

2.  **Provide Name:** Provide your name, e.g., "My name is Rakesh". The API should respond with "Nice to meet you Madan. How can I assist you today?".

3.  **Subsequent Interaction:** Ask "What is my name?" again. The API should now respond with "Your name is Rakesh. How can I help you today?".

    This demonstrates that the chat memory is working. The model remembers the previous conversation.

4.  📝 **Note:** The model isn't truly "remembering" in the traditional sense. Instead, relevant past chat messages are sent to the language model (LLM) with each new question.

5.  **Log Verification:** Check the logs to confirm that the entire chat conversation is being sent as input to the LLM. The request will include user messages and assistant messages, structured by their role.

    📌 **Example:**
    *   User message: "What is my name?"
    *   Assistant message: "I'm sorry, but I don't know your name."
    *   User message: "My name is Rakesh."

```log
request: ChatClientRequest[prompt=Prompt{messages=[UserMessage{content='What is my name?', properties={messageType=USER}, messageType=USER}, AssistantMessage [messageType=ASSISTANT, toolCalls=[], textContent=I'm sorry, but I don't know your name. How can I assist you today?, metadata={role=ASSISTANT, messageType=ASSISTANT, finishReason=STOP, refusal=, index=0, annotations=[], id=chatcmpl-C91ah3EbWZ6snv9scJdnGaibZgbbw}], UserMessage{content='My name is rakesh', properties={messageType=USER}, messageType=USER}, AssistantMessage [messageType=ASSISTANT, toolCalls=[], textContent=Nice to meet you, Rakesh! How can I assist you today?, metadata={role=ASSISTANT, messageType=ASSISTANT, finishReason=STOP, refusal=, index=0, annotations=[], id=chatcmpl-C91awiBe3GC2QXQNZROCoUikh8XI6}], UserMessage{content='What is my name', properties={messageType=USER}, messageType=USER}], modelOptions=OpenAiChatOptions: {"streamUsage":false,"model":"gpt-4o-mini","temperature":0.7}}, context={}]
```   


### Current Limitations ⚠️

1.  **Single Conversation ID:** All chat messages are currently being saved with a default conversation ID. This means that if different users interact with the API, the model will mix up their information.

    📌 **Example:** If one user says "My name is Madan" and another says "My name is John Doe," the model might incorrectly tell Madan that his name is John Doe.

2.  **Demonstration:** Setting a breakpoint in the `before` method of the `MemoryChatMemoryAdvisor` reveals that the `conversationId` is always "default."

    ```java
    @Override
    public void before(ChatConversationContext context) {
        String conversationId = context.getConversationId();
        // Breakpoint here to inspect conversationId
        List<ChatMessage> messages = chatMemory.get(conversationId);
    }
    ```

3.  **Real-Time Application Issues:** This default conversation ID setup will cause problems in real-time applications where multiple users are interacting with the API.

### Next Steps 🚀

The next step is to solve the problem of the single, default conversation ID by implementing unique conversation IDs for different users. This will ensure that the chat memory is accurate and personalized for each user.

---

## 5. Pre-User Memory in LLM - Configuring Conversation ID for REST API

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

```java
@GetMapping("/chat-memory")
public ResponseEntity<String> chatMemory(
    @RequestParam String message, 
    @RequestHeader String username) { // use @RequestHeader
        // ...
}
```

Here's how to configure the conversation ID:

1.  Invoke the `advisors` method. We'll use the overloaded version that accepts an advisor specification in the form of a consumer lambda expression.

    ```java
    advisorSpec -> {
        // Configuration here
    }
    ```

2.  Inside the lambda expression, invoke the `param` method (singular, not `params`).

3.  Pass the conversation ID key along with the value. To get the conversation ID key, refer to the `ChatMemory`. There's a constant named `CONVERSATION_ID` that holds the key value.

    📌 **Example:** Instead of directly pasting the value, import the `CONVERSATION_ID` constant into the controller class:- `import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;`

4.  Use the imported `CONVERSATION_ID` constant as the key in the `param` method. The value will be the username received from the client application.

    ```java
    advisorSpec.param(CONVERSATION_ID, username);
    ```

    This is the key configuration step. The framework will handle storing and retrieving messages based on this unique conversation ID.

After building, the framework uses a concurrent hash map in the in-memory chat memory repository to store chat messages based on the conversation ID.

```java
@GetMapping("/chat-memory")
public ResponseEntity<String> chatMemory(@RequestParam String message, @RequestHeader String username) {
    return ResponseEntity.ok(chatMemoryChatClient
        .prompt()
        .user(message)
        .advisors(advisorSpec -> 
            advisorSpec.param(CONVERSATION_ID, username))
        .call().content());
}
```

To test this:

1.  Set a breakpoint inside the `findByConversationID` method in the repository. This allows you to observe the behavior of the framework.
2.  When invoking the REST API, ensure you send a header with the key `username` and the corresponding username as the value.

    ⚠️ **Warning:** Ensure the username is in the **headers**, not the parameters. `curl --location 'http://localhost:8080/api/chat-memory?message=What%20is%20my%20name%3F' \
--header 'username: madan03'`

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

---

## 6. Storing Chat Messages with JDBC

The chat memory configured so far has a critical drawback: it stores all chat messages in the application memory using a concurrent hash map. This means that whenever the application restarts, all chat conversations are lost.

📌 **Example:** Restarting the application resets the memory. Sending a message like "What is my name?" with username "Madden zero two" after a restart will result in the model not knowing the name, even if it knew it before.

This in-memory chat memory repository is suitable for demos or less critical applications. However, for real enterprise applications, you should store chat messages in a proper SQL or NoSQL database.

This section demonstrates how to store messages using JDBC. The same steps can be adapted for other database types.

### Setting up JDBC

To get started with JDBC, you need to add the following dependencies to your `pom.xml` file:

```xml
<!-- Spring AI Starter Chat Memory Repository JDBC -->
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-chat-memory-repository-jdbc</artifactId>
    <version>1.0.1</version>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```
The first dependency is for the Spring AI JDBC chat memory repository, and the second is for the H2 database. H2 is used to avoid the setup complexity of MySQL or PostgreSQL, but the steps are similar regardless of the database used.

### Configuring Application Properties

Next, you need to configure the `application.properties` file with properties related to the H2 database:

```properties
spring.datasource.url=jdbc:h2:file:~/chatmemory;AUTO_SERVER=true
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=madan
spring.datasource.password=12345
```

These properties define the URL, driver class name, username, and password for the database.

⚠️ **Warning:** In real applications, avoid hardcoding usernames and passwords. Instead, inject them using environment variables or integrate with a secret vault.

The `spring.datasource.url` property specifies the location where the H2 database will store its data. The `file:~/chatmemory` part tells H2 to create a file named `chatmemory` in the user's home directory (`~`) to store the data. This ensures that the data is retained even after application restarts.

📝 **Note:** Without this configuration, H2 resets the data on every restart.

On Unix-based systems (like macOS), `~` represents the home directory of the current user. On Windows, you need to provide the complete path, 📌 **Example:** `C:/Users/your_username/chatmemory`.

### Understanding JDBC Chat Memory Repository

After building the project, you should see a class named `JdbcChatMemoryRepository` that implements the `ChatMemoryRepository` interface. This class uses the configured database to store and retrieve chat messages. It uses `JdbcTemplate` to query the database.

💡 **Tip:** Use this class as a reference if you need to create your own `ChatMemoryRepository` implementation, especially for NoSQL databases like MongoDB, where Spring AI might not provide a default implementation.

### Troubleshooting Schema Initialization

After restarting the application, you might encounter an error indicating that the framework is unable to find a schema file. This is because the framework tries to create the chat message table during startup, and it looks for a specific schema file.

The framework looks for an SQL file specific to the H2 database within the Spring AI library. It also includes SQL files for MariaDB, PostgreSQL, SQL Server, and SQL databases. If you use one of these supported databases, the framework automatically creates the schema using these files.

To resolve this for H2, you need to create the schema file manually.

### Creating a Custom Schema File

1.  Open the `schema-hsqldb.sql` file from the Spring AI library (you can find it within the `JDBC` package).
2.  Copy the SQL scripts from this file. These scripts create a table named `spring_underscore_chart_underscore_memory` with columns for conversation ID, content, type, and timestamp. They also create an index and add a constraint on the type column.
3.  Create a new directory named `schema` under the `resources` folder in your project.

```sql
CREATE TABLE SPRING_AI_CHAT_MEMORY (
    conversation_id VARCHAR(36) NOT NULL,
    content LONGVARCHAR NOT NULL,
    type VARCHAR(10) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_IDX ON SPRING_AI_CHAT_MEMORY(conversation_id, timestamp DESC);

ALTER TABLE SPRING_AI_CHAT_MEMORY ADD CONSTRAINT TYPE_CHECK CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));
```

4.  Inside the `schema` directory, create a new file named `schema-h2db.sql`.
5.  Paste the copied SQL scripts into `schema-h2db.sql`.

### Configuring Schema Initialization Properties

To tell the framework to use your custom schema file, add the following properties to your `application.properties` file:

```properties
spring.ai.chat.memory.repository.jdbc.initialize-schema=always
spring.ai.chat.memory.repository.jdbc.schema=classpath:/schema/schema-h2db.sql
```

*   `spring.ai.chat.memory.repository.jdbc.schema`: Specifies the location of the schema file. In this case, it's `classpath:/schema/schema-h2.sql`, which means the file is located in the `schema` directory under the classpath (resources folder).

The `spring.ai.chat.memory` properties control how the schema is initialized. The default value for schema initialization is `embedded`, which means the schema is initialized automatically for embedded databases like H2. For other databases like MySQL or PostgreSQL, you can either create the tables manually or set the initialization to `always` to have the framework execute the schema scripts.

### Accessing the H2 Console

You can access the H2 database console to validate the schema and data. The console is available at the path `http://localhost:8080/h2-console/`.

To connect to the database, provide the following details:

*   Driver Class: `org.h2.Driver`
*   JDBC URL: `jdbc:h2:file:~/chatmemory`
*   User Name: `madan`
*   Password: `12345`

After connecting, you can execute SQL queries to inspect the database.

### Fixing Timestamp Column Issue

You might encounter an error related to the `timestamp` column. This is because `timestamp` is a reserved keyword in some SQL dialects. To fix this, surround the `timestamp` column name with double quotes in your `schema-h2.sql` file:

```sql
CREATE TABLE SPRING_AI_CHAT_MEMORY (
    conversation_id VARCHAR(36) NOT NULL,
    content LONGVARCHAR NOT NULL,
    type VARCHAR(10) NOT NULL,
    "timestamp" TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE INDEX SPRING_AI_CHAT_MEMORY_CONVERSATION_ID_TIMESTAMP_IDX ON SPRING_AI_CHAT_MEMORY(conversation_id, "timestamp" DESC);

ALTER TABLE SPRING_AI_CHAT_MEMORY ADD CONSTRAINT TYPE_CHECK CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));
```

💡 **Tip:** This is a good example of how you can contribute to open-source projects. You can propose these kinds of changes to the framework developers.

### Testing the API

After making these changes, rebuild the project, delete the existing H2 database files (so the schema is recreated), and restart the application.

You can then test your REST API using Postman. Send messages with different usernames and verify that the messages are stored in the database. You can also ask questions and see if the model remembers the context from previous messages.

Each time you initiate a new request, the chat memory functionality loads all messages based on the conversation ID and sends them as input to the language model, providing enough context for the model to generate relevant responses.

---

## 7. Customizing Chat Memory Bean Configuration - Using maxMessages to Limit Chat History in Spring AI

When integrating our application with the JDBC chat memory repository, the initial bean configurations worked without modification. This is because the framework intelligently avoids creating the `InMemoryChatMemoryRepository` bean if the `JdbcChatMemoryRepository` class is available in the classpath. The framework injects the JDBC chat memory repository bean when a bean of type chat memory repository is required.

However, you might need to customize the default behavior of the Spring framework. 📌 **Example:** You might want to change the number of messages used by the `MessageWindowChatMemory` implementation when populating the chat messages for the LLM. By default, it uses a maximum of 20 messages.

In such cases, it's best to create your own beans instead of relying on the default beans created by the framework. Here's how:

1.  Go to the `ChatMemoryChatClientConfig` class.
2.  Create a bean method that creates a bean of type `ChatMemory`.
3.  Inject the bean of type `JdbcChatMemoryRepository` into this method. This bean will be created automatically by the Spring framework.

    ```java
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        // Customization logic here
    }
    ```

4.  Customize the `MessageWindowChatMemory` bean. Use the builder pattern to configure the maximum number of messages.

    ```java
    @Bean
    public ChatMemory chatMemory(JdbcChatMemoryRepository jdbcChatMemoryRepository) {
        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryRepository(jdbcChatMemoryRepository)
                .build();
    }
    ```

    In this 📌 **Example**, we're telling the framework to consider a maximum of the last 10 messages.

5.  Return the created bean from the method. This will create a bean of type `ChatMemory`. This bean is then injected where the chat client bean is created.

After saving the changes, build the application and restart it. You can then test the changes using Postman.

### Default Memory Setup

The default memory setup uses the in-memory implementation.

### Processing Chat Messages into a Database (JDBC)

To process chat messages into a database using JDBC:

1.  Add the required Maven dependencies.
2.  Leverage the built-in implementation class named `JdbcChatMemoryRepository`.

Similarly, if you add dependencies for Neo4j and Cassandra, Spring provides corresponding implementation classes: `Neo4jChatMemoryRepository` and `CassandraChatMemoryRepository`.

📝 **Note:** Always check the official Spring documentation first when storing chat messages in a specific database or storage system. If the documentation mentions your database, you can use the default implementation classes provided by the framework. Otherwise, you can create your own implementation.

---

## 8. Understanding Max Messages and Context Window Size in LM Models - Avoiding Token Overload

As a developer, you might be tempted to configure a large number for max messages (e.g., 1000) to enhance the user experience. However, this decision requires careful consideration due to its implications on context window size and cost.

### Context Window Size Explained 🧠

Every Large Language Model (LLM) has a context window size, which represents its "memory span."

*   It determines how much text or tokens the model can process at a time while generating a response.
*   Think of it like a whiteboard with limited space. Once full, older parts must be erased to make room for new information.
*   If the total token count exceeds the context window size, older messages are dropped, and the LLM forgets them.

We send various message types from our Spring application: system messages, user messages, assistant messages, and tool messages.

📌 **Example:** Imagine a user who has been using your application for years. If max messages is set to 1000, all their past messages will be sent to the LLM. All these messages are converted into tokens. If the total token size exceeds the LLM's context window, some messages will be lost because the LLM can only process a limited number of tokens at once.

### Cost Considerations 💰

The length of input messages directly impacts the number of tokens sent to the LLM.

*   LLM providers charge based on token usage.
*   While $1 per million tokens might seem cheap, consider the scale.
*   If 100 users use your application and 10,000 have long chat histories, you could incur significant costs.

It's crucial to discuss maximum message limits with business stakeholders to find a balance between user experience and cost. Good research and brainstorming are essential.

### Best Practices for Chat Memory Integration ✅

When integrating chat memory functionality in a Spring application, follow these guidelines:

1.  **Explore Models:** Limit and choose models based on expected conversation length. Different models have varying context window sizes. Smaller, open-source models have smaller context windows compared to commercial models.
2.  **Vector Store Chat Memory Advisor:** For longer chat histories, prefer using vector store chat memory advisors. This allows selecting only relevant past messages instead of the entire history. We'll discuss how vector stores and databases work in future lectures.
3.  **Limit Stored Messages:** By default, Spring keeps the last 20 messages. Adjust this number based on your use case.
4.  **Log and Monitor Token Usage:** Create alerts and notifications to track token usage and prevent exceeding your budget.
5.  **Configure Max Tokens:** As a developer, you can configure the maximum tokens an LLM request can consume using chat options.

    ```java
    // Example of setting max tokens (hypothetical)
    ChatOptions options = ChatOptions.builder().model("gpt-4.1-mini").maxTokens(1000).build();
    ```

    This ensures requests and responses don't exceed the configured token limit.
6.  **Explore Model Details:** When you want to know about the context window size that is supported by a given LM model, you can explore the same in these website. For example, you can go to models and select these oh four Hyphen mini. In this detail page, you'll be able to see that there is a note that this model is going to have a context window size of 200 tokens.

### Understanding Model Knowledge Cutoff 🗓️

Most models have a knowledge cutoff date, often a year or more in the past. If a user asks a question about recent events, the LLM might not be able to answer. Advanced concepts like RAG (Retrieval-Augmented Generation), MCP (Memory Context Provider) client, and MCP server can help overcome these limitations, and we'll discuss them later.

### Official Spring AI Documentation 📖

The official Spring AI documentation provides comprehensive details on chat memory.

*   You can find it in the references section under "Chat Memory."
*   It covers topics like memory storage (in-memory, JDBC), supported databases, and configuration properties.
*   It also details the three types of advisors supported by Spring AI and how to use conversation IDs.

📝 **Note:** While the documentation might seem complex initially, it will become more understandable after completing this course. Explore concepts not covered in the course and make a habit of reading the official documentation.

Spring AI supports these databases to store the chart memory details:

*   Cassandra
*   Neo4j

⚠️ **Warning:** Always be cautious of the cost implications when configuring max messages and consider the context window size of the LLM you are using.

💡 **Tip:** Regularly review and adjust your chat memory configurations based on user behavior, cost analysis, and the evolving capabilities of LLMs.

---