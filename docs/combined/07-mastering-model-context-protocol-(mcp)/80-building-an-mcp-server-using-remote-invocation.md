## Creating an MCP Server with Remote Invocation

This note details the process of creating an MCP server that supports remote invocation using the Spring framework. We'll convert an existing MCP server based on Stdio transport to one that can be invoked remotely.

1.  **Copy and Rename the Project:** 
    *   Start by copying the existing MCP server Stdio project within the workspace.
    *   Rename the copied project to "MCP Server Remote".

2.  **Clean the Target Folder:**
    *   Open the "MCP Server Remote" folder.
    *   Delete the contents of the `target` folder to ensure fresh compilation.

3.  **Update `pom.xml`:**
    *   Open the `pom.xml` file.
    *   Modify the `<name>` tag from "MCP server Stdio" to "MCP server Remote".
    *   Update the `<description>` tag to "Demo project for MCP server using remote invocation".
    *   Save the `pom.xml` file.

4.  **Import the Project into IntelliJ:**
    *   In IntelliJ, go to `File` -> `New` -> `Module from Existing Sources`.
    *   Select the "MCP Server Remote" folder and click `Open`.
    *   Confirm that it's a Maven project.

5.  **Modify Dependencies in `pom.xml`:**
    *   Replace the `mcp-server-starter-stdio` dependency with `spring-ai-starter-mcp-server-webmvc`.
        ```xml
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-mcp-server-webmvc</artifactId>
        </dependency>
        ```
    *   📝 **Note:** If you're building a Spring Reactive application, use the `webflux` dependency instead.
    *   Save the `pom.xml` file and sync the Maven changes.

6.  **Update `application.properties`:**
    *   Remove the following three properties related to Stdio transport:
        *   `spring.ai.mcp.server.stdio.enabled`
        *   `spring.ai.mcp.server.stdio.input`
        *   `spring.ai.mcp.server.stdio.output`
    *   Add the following properties:
        *   `logging.pattern.console=%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%-5p) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx`
        *   `server.port=8090`
    *   📝 **Note:**  The `server.port` can be any available port. 8080 is often used by other applications.
    *   Save the `application.properties` file.

7.  **Rename the Main Application Class and Package (Optional but Recommended):**
    *   Rename `MCP Server Stdio Application` to `MCP Server Remote Application`.
    *   Refactor the package name from `mcp.server.stdio` to `mcp.server.remote`.  Select "All in selected module" during refactoring.

8.  **Build the Project:**
    *   Build the project to ensure all changes are compiled.

9.  **Run the Application:**
    *   Run the Spring Boot application in debug mode.
    *   Verify that the MCP server starts on port 8090.

10. **Inspect with MCP Inspector:**
    *   Open the MCP Inspector.
    *   Select either `HTTP` or `SSC` as the transport type.
    *   Enter the URL: `http://localhost:8090/ssc`.
    *   Click `Connect`.
    *   ⚠️ **Warning:** The `Streamable` transport type may not be supported by the Spring framework at the time of this writing.
    *   Go to `Tools` -> `List Tools` to confirm the server is running and exposing tools.

11. **Integrate with MCP Client:**
    *   Create a new file named `mcp-servers-stdio.json` under the `resources` folder and move the Stdio based MCP server configurations to this file.
    *   Remove the Stdio based MCP server configurations from the original `mcp-servers.json` file.
    *   Add the following property to `application.properties`:
        *   `spring.ai.mcp.client.ssc.connections.easybytes.url=http://localhost:8090/ssc`
    *   📝 **Note:** Replace `easybytes` with a suitable connection name.
    *   📝 **Note:** In a real-world application, replace `localhost` with the actual hostname or IP address of the MCP server.

12. **Rebuild and Restart the Client:**
    *   Rebuild the MCP client application.
    *   Restart the MCP client application to load the new configurations.

13. **Test with Postman:**
    *   Send a request to the MCP client using Postman.
    *   📌 **Example:** Ask "What is the status of my ticket?" with a new username.
    *   Verify that the response is as expected.
    *   📌 **Example:** Send a message with issue details like "My account is locked".
    *   Verify that a new ticket is created.
    *   📌 **Example:** Ask "What is the status of my ticket?" again.
    *   Verify that the ticket details are returned in the response.

By following these steps, you can successfully create an MCP server that supports remote invocation and integrate it with an MCP client using the Spring framework. 💡 **Tip:** Remember to adjust the port numbers and URLs according to your specific environment.
