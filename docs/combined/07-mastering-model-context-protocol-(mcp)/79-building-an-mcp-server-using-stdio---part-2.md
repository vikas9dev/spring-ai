## Building an MCP Server with STDIO Transport

This section outlines the steps to build an MCP server and integrate it with a client application using STDIO transport.

### Generating the JAR File 📦

1.  Ensure all necessary changes are implemented in the MCP software project.
2.  Open your terminal and navigate to the project directory. ⚠️ **Warning:** Make sure you are in the directory containing the `pom.xml` file. You can verify this by running `ls` (or `dir` on Windows) and confirming the presence of `pom.xml`.

    ```bash
    cd /path/to/your/mcp-server-project
    ls # or dir on Windows
    ```
3.  Execute the Maven command to generate the JAR file, skipping unit tests:

    ```bash
    mvn clean install -Dmaven.test.skip=true
    ```

    This command cleans the project, compiles the code, and packages it into a JAR file, skipping the unit tests.
4.  The generated JAR file will be located in the `target` folder within your project directory. The JAR name will be similar to `MCP-server-stdio-0.0.1-SNAPSHOT.jar`.

### Inspecting the MCP Server with MCP Inspector 🔎

1.  Start a new instance of MCP Inspector.
2.  Select the transport type as "Stdio".
3.  Set the command to `Java`.
4.  In the arguments, specify `-jar` followed by the complete path to your JAR file:

    ```
    -jar /path/to/your/mcp-server-project/target/MCP-server-stdio-0.0.1-SNAPSHOT.jar
    ```

5.  Click "Connect" to establish a connection between the MCP Inspector and your MCP server.
6.  Use the Inspector's tools to test the exposed functionalities. 📌 **Example:** List available tools and execute them by providing the required input parameters.

### Integrating with the MCP Client Application 🤝

1.  Open the JSON configuration file in your MCP client application (usually located in the `resources` folder).
2.  Add a new configuration for your MCP server. You can copy and paste the following snippet, adjusting the values to match your environment:

    ```json
    {
      "name": "spring-mcp",
      "transport": "stdio",
      "command": "/path/to/your/java/installation/java",
      "args": [
        "-jar",
        "/path/to/your/mcp-server-project/target/MCP-server-stdio-0.0.1-SNAPSHOT.jar"
      ]
    }
    ```

    *   `name`: A descriptive name for your MCP server configuration.
    *   `transport`: Set to `stdio`.
    *   `command`: The full path to your Java executable. Use `where java` in your terminal to find this path. ⚠️ **Warning:** This must be the full path, not just `java`.
    *   `args`: An array containing `-jar` and the full path to your JAR file.
3.  Save the changes to the JSON configuration file.
4.  Rebuild the MCP client application.

### Modifying the Controller Class ✍️

1.  Open the controller class in your MCP client application where requests are sent to the LM model.
2.  Add a `@RequestHeader` annotation to accept the `username` from the request header. This allows passing the username to the MCP server for tool invocation.

    ```java
    @RequestHeader(value = "username", required = false) String username
    ```

    📝 **Note:** Setting `required = false` makes the username optional for testing other integrations.
3.  Append the username to the message sent to the LM model:

    ```java
    message = message + " My username is " + username;
    ```
4.  Save the changes and rebuild the MCP client application.

### Testing the Integration ✅

1.  Restart the MCP client application. The application will automatically start the MCP server based on the configuration.
2.  Send a request to the LM model with a message that triggers a tool exposed by your MCP server.
3.  Include a `username` header in the request.
4.  Verify that the MCP server successfully invokes the tool and returns the expected response. 📌 **Example:** Send a message like "I am not able to access my cabin. The access card is not working." with the username "modern25".

### Key Considerations 🔑

*   Ensure Java is installed correctly and the `JAVA_HOME` environment variable is set.
*   Double-check all file paths in the configuration to avoid errors.
*   The MCP client and server communicate via STDIO, meaning they run on the same local system.
*   💡 **Tip:** Use a tool like Postman to easily send requests with custom headers.
*   ⌨️ **Shortcut:** `Ctrl + C` to terminate a running process in the terminal.
