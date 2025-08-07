## Demonstrating File and Directory Management with MCP and LM Model

This section demonstrates how an LM model interacts with an MCP server to perform file and directory management tasks using natural language instructions.

Initially, the LM model is queried to understand its capabilities. The response confirms its ability to assist with:

*   File management
*   Directory management
*   Search functionality
*   Context review

This confirms that the LM model has access to the tools exposed by the MCP server.

### Creating a File

The following instruction is given to the LM model:

> Create a file named `model.txt` in the directory `users/Ezbuy/MCP` with the content "hello world" inside it.

The LM model translates this instruction into actions for the MCP host and client. The MCP client then invokes the appropriate tool on the MCP server, which handles the actual file creation.

The result is verified: a file named `model.txt` is created in the specified directory, containing the text "hello world".

### Creating a Directory and File

Next, the LM model is instructed to create a directory and a file within it:

> Create a folder named `testing` in the directory `users/Ezbuy/MCP` with a file `testing.txt` inside it.

The LM model successfully creates the `testing` folder and the `testing.txt` file.  Notably, the LM model also populates the `testing.txt` file with sample data: "This is a test file."

This showcases the power of the MCP protocol, where natural language instructions are used instead of structured data formats like JSON or XML.

### Listing Files and Directories

The LM model is then asked to list the contents of a directory:

> List the folders and files under `users/Ezbuy/MCP`.

The LM model provides a detailed list of files and directories, including hidden files.

### Deleting a Directory

The following instruction is given to delete a directory:

> Delete the folder `testing` present under `users/Ezbuy/MCP`.

It's observed that a hard delete is not supported. Instead, the `testing` folder is moved to `testing_deleted` within the same directory. The LM model confirms this action.

An attempt to completely delete the `testing_deleted` folder is made with the instruction:

> Please delete the folder `testing_deleted` completely present under `users/Ezbuy/MCP`.

However, this operation fails to complete. The MCP server enters a loop, attempting to move the directory to a backup location repeatedly.

⚠️ **Warning:** This behavior highlights a crucial point: the LM model's capabilities are limited by the underlying MCP server's functionality. Bugs, defects, or limitations in the MCP server cannot be automatically overcome by the LM model.

📝 **Note:** Thorough testing of the MCP server's capabilities is essential to ensure reliable operation when integrated with an LM model.

The operation is manually canceled.

### Conclusion

This demonstration illustrates the potential of using Spring AI and MCP protocols to build intelligent applications. By leveraging these frameworks, developers can create applications that respond to natural language instructions.

The upcoming lectures will focus on building an MCP server.
