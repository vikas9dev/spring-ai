## Integrating Spring AI with GitHub MCP Server

This section explains how to integrate a Spring AI application with a GitHub MCP server. The GitHub repository containing the server details will be used.

### Finding the GitHub MCP Server

1.  Search for "GitHub MCP server" on GitHub.
2.  Locate the official GitHub MCP server repository.
3.  The repository provides instructions on setting up the server, either remotely or locally.

### Prerequisites

*   An MCP host that supports the latest MCP specification and remote servers. The Spring AI application serves as this host.
*   Docker Desktop installed and running locally.

### Configuration

1.  Copy the GitHub MCP server configurations from the official documentation.
2.  Paste the configurations into the `mcp-servers.json` file in your Spring AI project.
    *   If you already have an MCP server configured (e.g., "file system"), add a comma and then paste the GitHub MCP server configuration.

📌 **Example:** `mcp-servers.json`
```json
[
  {
    "name": "file-system",
    "url": "http://localhost:8080/file-system"
  },
  {
    "name": "github",
    "url": "http://localhost:8080/github"
  }
]
```

### Running the GitHub MCP Server with Docker

The GitHub MCP server can be run locally using Docker.

1.  Use the following Docker command:

```bash
docker run -i --rm -e GITHUB_PERSONAL_ACCESS_TOKEN=<YOUR_GITHUB_TOKEN> <DOCKER_IMAGE_NAME>
```

*   `-i`: Runs the container in interactive mode.
*   `--rm`: Removes the container once the process is stopped.
*   `-e GITHUB_PERSONAL_ACCESS_TOKEN`: Sets an environment variable for the GitHub personal access token.
*   `<DOCKER_IMAGE_NAME>`: Specifies the Docker image for the GitHub MCP server.

2.  ⚠️ **Warning:** You need to provide your GitHub personal access token as an environment variable.

### Generating a GitHub Personal Access Token

1.  Log in to your GitHub account.
2.  Go to **Settings** -> **Developer settings** -> **Personal access tokens** -> **Fine-grained tokens**.
3.  Click on **Generate new token**.
4.  Give the token a descriptive name (e.g., "demo").
5.  Set the **Resource owner** to your account.
6.  Configure the necessary permissions:
    *   Under **Repository permissions**, grant **Read and write** access for all required permissions.
    *   Grant necessary **Account permissions** as well.
7.  Click on **Generate token**.
8.  ⚠️ **Warning:** Copy the generated token immediately, as you won't be able to see it again.
9.  Provide this token as the value for the `GITHUB_PERSONAL_ACCESS_TOKEN` environment variable in the Docker command.

### Restarting the Application

1.  Build the Spring AI application.
2.  Restart the application.
3.  Verify that a container created using the GitHub MCP server Docker image is running in Docker Desktop.

### Testing the Integration

1.  Use a tool like Postman to send a message to the Spring AI application.
2.  The LLM model should now consider tools exported by the GitHub MCP server.

📌 **Example:** Sending the message "Who are you and how can you help me?" might result in a response indicating capabilities related to GitHub repository management, code review, and collaboration.

### Example Usage

1.  List GitHub repositories: "List down the GitHub repositories of `<YOUR_GITHUB_USERNAME>`".
    *   Replace `<YOUR_GITHUB_USERNAME>` with your actual GitHub username.
    *   The application should return a list of your repositories with their stats and metrics.
2.  Create a new repository: "Create a new repository. The name of the repository is spring a demo."
    *   The application should create a new repository with the specified name under your account.
3.  Create a README file: "Create a readme file in `<YOUR_GITHUB_USERNAME>`/spring-ai-demo repository."
    *   Replace `<YOUR_GITHUB_USERNAME>` with your actual GitHub username.
    *   The application should create a README file in the specified repository.

### Inspecting Tools with MCP Inspector

1.  Execute the following command to start the MCP inspector:

```bash
npx model-context-protocol/inspector
```

2.  Configure the inspector:
    *   **Transport type:** Stdio
    *   **Command:** Docker
    *   **Arguments:** `run -i --rm -e GITHUB_PERSONAL_ACCESS_TOKEN=<YOUR_GITHUB_TOKEN> <DOCKER_IMAGE_NAME>`
    *   **Environment Variables:** Add an environment variable named `GITHUB_PERSONAL_ACCESS_TOKEN` with your GitHub access token as the value.
3.  Click **Connect**.
4.  Go to **Tools** and click **List tools** to see the tools exposed by the GitHub repository.

💡 **Tip:** Exploring these tools helps you build better prompts by understanding the available functionalities and parameters.

### Conclusion

This process allows you to integrate any MCP server into your Spring AI application. There are numerous MCP servers developed by various companies, and this approach enables you to leverage their tools within your application.

The next step is to learn how to build your own MCP servers to expose your own tools.
