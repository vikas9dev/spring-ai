## Customizing Chat Memory Bean Configuration

When integrating our application with the JDBC chat memory repository, the initial bean configurations worked without modification. This is because the framework intelligently avoids creating the in-memory chat memory repository bean if the JDBC chat memory repository class is available in the classpath. The framework injects the JDBC chat memory repository bean when a bean of type chat memory repository is required.

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

Similarly, if you add dependencies for Neo4j or Cassandra, Spring provides corresponding implementation classes: `Neo4jChatMemoryRepository` and `CassandraChatMemoryRepository`.

📝 **Note:** Always check the official Spring documentation first when storing chat messages in a specific database or storage system. If the documentation mentions your database, you can use the default implementation classes provided by the framework. Otherwise, you can create your own implementation.

### Custom Chat Memory Bean Configuration

To define your own chat memory bean with custom configurations like `maxMessages`, you need to create a bean definition and use the builder pattern as shown above.

### H2 Database Configuration

The following properties are used to configure the H2 database to store chat message details:

*   (Properties were not explicitly listed in the original transcript, but would be listed here if they were)
