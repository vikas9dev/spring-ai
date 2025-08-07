## Building an MCP Server with Stdio Transport

When building an MCP server, the first decision is whether to support Stdio or Streamable HTTP transport. This section focuses on building an MCP server using the Stdio transport type. Other options will be explored later.

Creating an MCP server involves the following steps:

1.  **Project Setup:**
    *   Go to [Start.spring.io](https://start.spring.io).
    *   Set the artifact ID to `MCP server Stdio`.
    *   Add the following dependencies:
        *   `spring-web`
        *   `spring-cloud-starter-model-context-protocol-server` (MCP Server)
        *   `spring-boot-devtools`
        *   `com.h2database:h2` (H2 Database)
        *   `spring-boot-starter-data-jpa` (JPA)
        *   `org.projectlombok:lombok` (Lombok)

2.  **Generate and Import Project:**
    *   Click "Generate" to create a Spring Boot project.
    *   Extract the project to a suitable location (e.g., a section seven folder).
    *   Open the project in IntelliJ IDEA: `File -> New -> Module from Existing Sources`.
    *   Select the `MCP server Stdio` folder and click "Open".
    *   Confirm the build type is Maven and click "Create".

3.  **Maven Configuration:**
    *   Verify the project is recognized as a Maven project. If not, manually add it using the "+" symbol in the Maven tool window.
    *   Open `pom.xml`.
    *   Remove the `spring-boot-starter-webmvc` dependency. This dependency is for Streamable HTTP, not Stdio.

        ```xml
        <!-- Remove this dependency -->
        <!-- <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webmvc</artifactId>
        </dependency> -->
        ```

    *   Sync Maven changes and build the project.

4.  **Application Properties Configuration:**
    *   Open `application.properties`.
    *   Configure H2 database properties:

        ```properties
        spring.datasource.url=jdbc:h2:mem:testdb
        spring.datasource.driverClassName=org.h2.Driver
        spring.datasource.username=sa
        spring.datasource.password=
        spring.h2.console.enabled=true
        spring.jpa.hibernate.ddl-auto=create-drop
        ```

    *   Set the web application type to none:

        ```properties
        spring.main.web-application-type=none
        ```
        📝 **Note:** This is crucial for Stdio transport, preventing the MCP server from acting as a web server.

    *   Disable logging:

        ```properties
        logging.level.root=error
        ```
        ⚠️ **Warning:** Disabling logs is important to avoid interference with Stdio communication. The MCP server should not write unnecessary information to standard input or output.

    *   Disable the Spring Boot banner:

        ```properties
        spring.main.banner-mode=off
        ```

5.  **Java Code Implementation:**
    *   Create the following packages: `entity`, `repository`, `service`, and `model`.
    *   **Entity Package:**
        *   Create `HelpDeskTicket.java`:

            ```java
            // Example:
            @Entity
            @Table(name = "help_desk_tickets")
            public class HelpDeskTicket {
                // ... fields like id, username, issue, status, createdAt, eta ...
            }
            ```

    *   **Repository Package:**
        *   Create `HelpDeskTicketRepository.java`:

            ```java
            // Example:
            public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {
                List<HelpDeskTicket> findByUsername(String username);
            }
            ```

    *   **Service Package:**
        *   Create `HelpDeskTicketService.java`: This class will contain methods to create new tickets and query ticket details.

            ```java
            // Example:
            @Service
            public class HelpDeskTicketService {
                // ... methods to create and query tickets ...
            }
            ```

    *   **Model Package:**
        *   Create `TicketRequest.java`: A record class to accept issue details and username.

            ```java
            // Example:
            public record TicketRequest(String issue, String username) {}
            ```
            📝 **Note:** Unlike previous examples, the username is now part of the request because the tool context is not available in a separate MCP server.

    *   **Tool Package:**
        *   Create `HelpDeskTools.java`: This class defines the tools for creating tickets and getting ticket status.

            ```java
            // Example:
            @Component
            public class HelpDeskTools {
                // ... tools for creating and getting ticket status ...
            }
            ```

6.  **Configuration Package:**
    *   Create `config` package.
    *   Create `MCPserverConfig.java`: This class exposes the tools from the MCP server.

        ```java
        // Example:
        @Configuration
        public class MCPserverConfig {

            @Bean
            public List<ToolCallback> toolCallbacks(HelpDeskTools helpDeskTools) {
                return List.of(ToolCallbacks.from(helpDeskTools));
            }
        }
        ```
        💡 **Tip:** Use `ToolCallback` to expose tools from the MCP server. `ToolCallbackProvider` is used on the client side.
