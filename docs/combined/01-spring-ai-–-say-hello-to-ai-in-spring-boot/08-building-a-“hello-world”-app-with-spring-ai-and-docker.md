## Setting Up Local LM Models Using Docker

We can set up local LM models using Docker, similar to how we use it for databases, servers, and application deployments. Everything needs to be converted into a Docker container for deployment.

To get started:

1.  Install Docker Desktop. It's free for local development. Download it from the official Docker website based on your operating system.
2.  Visit `docs.docker.com/model-runner`. 📝 **Note:** This feature is currently in beta.

### System Requirements

To use Docker Model Runner, ensure your system meets these requirements:

*   **Docker Engine/Docker Desktop:**
    *   Windows: At least version 4.41
    *   Mac: At least version 4.40
*   **Hardware:**
    *   Windows: Nvidia GPU
    *   macOS: Apple Silicon
    *   Linux: Check the Docker documentation for specific requirements.

If your system doesn't meet these requirements, you can still follow along with the demo. This knowledge will be useful if you encounter projects where setting up LM models on local servers or systems is required.

Alternatives include Olama and Docker Model Runner.

### Configuring Docker Desktop

1.  Open Docker Desktop.
2.  Go to **Settings** and click on **Beta Features**.
3.  Enable **Docker Model Runner**.
4.  Also, select **Enable host side TCP support** and set the port number to **12434**. This is the default port for open-source LM models.
    💡 **Tip:** This feature may become stable in the future.
5.  Apply and restart Docker Desktop.

### Running a Model

1.  In the Docker Desktop dashboard, navigate to **Models**.
2.  Go to **Docker Hub** to see available models.
3.  📌 **Example:** Let's use the `Gam3` model from Google, a lightweight model for local testing. It has 3.88 billion parameters and a size of 2.3 GB.
4.  Make sure Docker Model is installed. Run the following command in your terminal:

    ```bash
    docker model version
    ```

    A successful output confirms the setup.
5.  Run the model:

    ```bash
    docker model run a/gemma-3
    ```

    If you don't specify a tag (like `latest`), Docker will default to the `latest` tag. The model will be downloaded from the remote server and then started.
6.  Once the download is complete, the interactive chat mode will start. You can interact with the model directly in your terminal.
    📌 **Example:**

    ```
    User: Who are you and how can you help me?
    Gemma: Hello there, I'm Gemma, a large language model created by Gemma Team at Google DeepMind.
    ```

7.  In Docker Desktop, under **Models** -> **Local**, you'll see the downloaded model.

### Integrating with Spring AI

1.  Create a Spring AI project (or use an existing one).
2.  Update the `pom.xml` to use the OpenAI starter project instead of Olama. The Spring team reuses the OpenAI library to interact with Docker-based LM models.
3.  Sync the Maven changes.
4.  Modify the `application.properties` file:

    *   Remove Olama-related properties.
    *   Set the following properties:

        ```properties
        spring.ai.openai.chat.options.model=a/gemma-3
        spring.ai.openai.api-key=dummy_key  # Dummy key is required
        spring.ai.openai.chat.base-url=http://localhost:12434/engines
        ```

        📝 **Note:**  A dummy API key is needed because the Spring AI framework uses the OpenAI library and expects an API key, even though the model is running locally. The `base-url` property tells Spring AI to communicate with the local LM model.
5.  Build the project.
6.  Run the application in debug mode.
7.  Test the API using Postman.

    📌 **Example:**

    *   **Request:** What is your name and which model are you using for this?
    *   **Response:** I'm a large language model created by Google. I'm an open weight model, which means I'm widely available for public use.

This confirms that Spring AI is communicating with the locally running Docker-based LM model.

📌 **Example:** You can also ask for a joke about Spring Boot to further test the integration.

To stop the LM model, type `/bye` in the conversation.
