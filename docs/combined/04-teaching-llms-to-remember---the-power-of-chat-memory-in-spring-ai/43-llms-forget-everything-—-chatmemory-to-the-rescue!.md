## Spring Chat Memory

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
    void add(ChatMessage message);
    List<ChatMessage> get(String conversationId);
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
    List<ChatMessage> findByConversationId(String conversationId);
    void saveAll(String conversationId, List<ChatMessage> messages);
    void delete(String conversationId);
}
```

Spring provides an `InMemoryChatMemoryRepository` implementation.

*   It uses a `ConcurrentHashMap` as an in-memory storage. 🧠
*   It saves conversation messages using the `put` method. ➕
*   It deletes messages using the `remove` method. 🗑️
*   It retrieves messages using the `get` method. 📤

Spring also provides a `JdbcChatMemoryRepository` for storing chat memories in databases like MySQL or PostgreSQL. 🗄️

💡 **Tip:** You can create custom implementations for NoSQL databases like MongoDB, using the provided implementations as a reference. 🧑‍💻
