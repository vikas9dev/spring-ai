## Storing Chat Messages with JDBC

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
    <artifactId>spring-ai-spring-boot-starter-chat-memory-repository-jdbc</artifactId>
</dependency>

<!-- H2 Database -->
<dependency>
    <groupId>com.h2database</groupId>
    <artifactId>h2</artifactId>
    <scope>runtime</scope>
</dependency>
```

These dependencies can also be found in the GitHub repository. The first dependency is for the Spring AI JDBC chat memory repository, and the second is for the H2 database. H2 is used to avoid the setup complexity of MySQL or PostgreSQL, but the steps are similar regardless of the database used.

### Configuring Application Properties

Next, you need to configure the `application.properties` file with properties related to the H2 database:

```properties
spring.datasource.url=jdbc:h2:file:~/chatmemory
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=modern
spring.datasource.password=12345
spring.jpa.hibernate.ddl-auto=none
```

These properties define the URL, driver class name, username, and password for the database.

⚠️ **Warning:** In real applications, avoid hardcoding usernames and passwords. Instead, inject them using environment variables or integrate with a secret vault.

The `spring.datasource.url` property specifies the location where the H2 database will store its data. The `file:~/chatmemory` part tells H2 to create a file named `chatmemory` in the user's home directory (`~`) to store the data. This ensures that the data is retained even after application restarts.

📝 **Note:** Without this configuration, H2 resets the data on every restart.

On Unix-based systems (like macOS), `~` represents the home directory of the current user. On Windows, you need to provide the complete path, 📌 **Example:** `C:/Users/your_username/chatmemory`.

### Understanding JDBC Chat Memory Repository

After building the project, you should see a class named `JDBCChartMemoryRepository` that implements the `ChartMemoryRepository` interface. This class uses the configured database to store and retrieve chat messages. It uses `JdbcTemplate` to query the database.

💡 **Tip:** Use this class as a reference if you need to create your own `ChartMemoryRepository` implementation, especially for NoSQL databases like MongoDB, where Spring AI might not provide a default implementation.

### Troubleshooting Schema Initialization

After restarting the application, you might encounter an error indicating that the framework is unable to find a schema file. This is because the framework tries to create the chat message table during startup, and it looks for a specific schema file.

The framework looks for an SQL file specific to the H2 database within the Spring AI library. It also includes SQL files for MariaDB, PostgreSQL, SQL Server, and SQL databases. If you use one of these supported databases, the framework automatically creates the schema using these files.

To resolve this for H2, you need to create the schema file manually.

### Creating a Custom Schema File

1.  Open the `SQL db dot SQL` file from the Spring AI library (you can find it within the `JDBC` package).
2.  Copy the SQL scripts from this file. These scripts create a table named `spring_underscore_chart_underscore_memory` with columns for conversation ID, content, type, and timestamp. They also create an index and add a constraint on the type column.
3.  Create a new directory named `schema` under the `resources` folder in your project.
4.  Inside the `schema` directory, create a new file named `schema-h2.sql`.
5.  Paste the copied SQL scripts into `schema-h2.sql`.

### Configuring Schema Initialization Properties

To tell the framework to use your custom schema file, add the following properties to your `application.properties` file:

```properties
spring.ai.chat.memory.repository.jdbc.schema-location=classpath:schema/schema-h2.sql
spring.jpa.hibernate.ddl-auto=none
```

*   `spring.ai.chat.memory.repository.jdbc.schema-location`: Specifies the location of the schema file. In this case, it's `classpath:schema/schema-h2.sql`, which means the file is located in the `schema` directory under the classpath (resources folder).
*   `spring.jpa.hibernate.ddl-auto=none`: Disables automatic schema generation by JPA/Hibernate, as we are providing our own schema.

The `spring.ai.chat.memory` properties control how the schema is initialized. The default value for schema initialization is `embedded`, which means the schema is initialized automatically for embedded databases like H2. For other databases like MySQL or PostgreSQL, you can either create the tables manually or set the initialization to `always` to have the framework execute the schema scripts.

### Accessing the H2 Console

You can access the H2 database console to validate the schema and data. The console is available at the path `H2 hyphen console`.

To connect to the database, provide the following details:

*   Driver Class: `org.h2.Driver`
*   JDBC URL: (The URL from your `application.properties` file)
*   User Name: `modern`
*   Password: `12345`

After connecting, you can execute SQL queries to inspect the database.

### Fixing Timestamp Column Issue

You might encounter an error related to the `timestamp` column. This is because `timestamp` is a reserved keyword in some SQL dialects. To fix this, surround the `timestamp` column name with double quotes in your `schema-h2.sql` file:

```sql
CREATE TABLE spring_underscore_chart_underscore_memory (
    conversation_id VARCHAR(255) NOT NULL,
    content TEXT,
    type VARCHAR(50),
    "timestamp" BIGINT NOT NULL,
    PRIMARY KEY (conversation_id, "timestamp")
);

CREATE INDEX idx_spring_chart_memory ON spring_underscore_chart_underscore_memory (conversation_id, "timestamp");

ALTER TABLE spring_underscore_chart_underscore_memory ADD CONSTRAINT CHK_TYPE CHECK (type IN ('USER', 'ASSISTANT', 'SYSTEM', 'TOOL'));
```

💡 **Tip:** This is a good example of how you can contribute to open-source projects. You can propose these kinds of changes to the framework developers.

### Testing the API

After making these changes, rebuild the project, delete the existing H2 database files (so the schema is recreated), and restart the application.

You can then test your REST API using Postman. Send messages with different usernames and verify that the messages are stored in the database. You can also ask questions and see if the model remembers the context from previous messages.

Each time you initiate a new request, the chat memory functionality loads all messages based on the conversation ID and sends them as input to the language model, providing enough context for the model to generate relevant responses.
