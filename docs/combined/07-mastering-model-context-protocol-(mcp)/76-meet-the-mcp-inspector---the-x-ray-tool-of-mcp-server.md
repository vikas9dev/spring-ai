## Understanding and Using the MCP Inspector

The MCP team has developed a utility app called the **MCP Inspector** to help you understand and test MCP servers. This tool is highly recommended for exploring the capabilities of MCP servers, especially those developed by other organizations. Instead of relying solely on documentation, the Inspector allows you to quickly discover the tools, resources, and prompts exposed by an MCP server.

### Getting Started with the MCP Inspector

To start using the MCP Inspector, follow these steps:

1.  **Navigate to the Model Context Protocol GitHub page.** Look for the pinned repository named "Inspector."
2.  **Execute the following command:**

    ```bash
    npx @model-context-protocol/inspector
    ```

    ⚠️ **Warning:** Make sure you have Node.js installed before running this command. Otherwise, it will not work.

### Installing Node.js

If you don't have Node.js installed, follow these steps:

1.  Go to the [Node.js website](https://nodejs.org/).
2.  Download and install Node.js using the instructions provided on the download page.
3.  Verify the installation by running the following command in your terminal:

    ```bash
    node --version
    ```

    You should see an output similar to this:

    ```
    v16.13.0
    ```

    This confirms that Node.js is installed correctly.

### Running the MCP Inspector

After installing Node.js, execute the `npx` command mentioned earlier:

```bash
npx @model-context-protocol/inspector
```

This will start the MCP Inspector and automatically open it in your browser. If it doesn't open automatically, use the URL provided in the terminal output.

### Connecting to an MCP Server

The MCP Inspector UI will appear, allowing you to connect to an MCP server. Here's how to connect using Stdio:

1.  Select "Stdio" as the connection type.
2.  Enter the command used to start the MCP server, along with any necessary arguments.  These details can also be found in the official documentation of the MCP server. 📌 **Example:**

    ```bash
    npx -y @model-context-protocol/server ./path/to/your/server
    ```

3.  In the "Arguments" section, provide the arguments, separating them with whitespace. 📌 **Example:** `-y @model-context-protocol/server ./path/to/your/server`
4.  If your MCP server requires environment variables, you can add them in the "Environment Variables" section.
5.  Click the "Connect" button.

You might encounter a warning about securing your MCP server. For demo purposes, you can usually ignore this.

Once the connection is established, you'll see a green dot indicating a successful connection. The right-hand side of the UI will display the details exposed by the MCP server, such as tools, resources, and prompts.

### Exploring MCP Server Capabilities

The MCP Inspector allows you to explore the tools, resources, and prompts exposed by the MCP server.

*   If the MCP server exposes only tools, the other sections (resources, prompts) will be disabled.
*   Click on "List Tools" to see a list of available tools. 📌 **Example:** `readFile`, `writeFile`, `createDirectory`, etc.

💡 **Tip:** Pay close attention to the tools and their capabilities. This will help you understand what the MCP server can do.

### Testing Tools

To understand the purpose of a tool, click on it. This will display a description and the input details it expects.

📌 **Example:**

1.  Select the `readFile` tool.
2.  Provide the path to a text file in the "path" field. 📌 **Example:** `/user/mcp/test.txt`
3.  Click "Run Tool."

The Inspector will execute the tool and display the result, which in this case would be the content of the file.

⚠️ **Warning:** When testing tools, make sure you provide the exact details expected by the tool.

📝 **Note:** When integrating the MCP server with a Spring AI application, the LM model will handle populating the tool's input based on its expectations. The LM model will also process the tool's output and send the final result to the end user.

### Conclusion

The MCP Inspector is a valuable tool for developers who want to explore MCP servers developed by others. It provides a quick and easy way to understand the capabilities of an MCP server and test its tools.

Once you're done inspecting the MCP server, you can stop the session in the terminal by pressing `Ctrl+C`. Refreshing the MCP Inspector page will then display an error page.

### Bonus: Terminal Recommendation

The terminal used in this lecture is Warp. You can download it for free from [Warp.dev](https://www.warp.dev/). Warp offers advanced features, including customizable themes. The theme used in the lecture is "Pink." You can change the appearance of the terminal in the settings.
