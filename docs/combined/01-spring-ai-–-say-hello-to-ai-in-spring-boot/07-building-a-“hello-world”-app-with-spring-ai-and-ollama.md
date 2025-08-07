## Integrating Llama with Spring AI

This section details how to integrate Llama, an open-source large language model (LLM), with a Spring AI application. This approach allows you to run LLMs locally or within your own infrastructure, offering an alternative to cloud-based LLM providers like OpenAI.

Many organizations may prefer to keep their data private and avoid sending it to cloud-based LLM providers. Llama provides a solution by allowing you to deploy an open-source LLM model within your own company infrastructure.

### Setting Up Llama

Llama, backed by Meta, is designed for developers and organizations interested in open-source LLMs.

1.  **Download Llama:**
    *   Visit the Llama download page.
    *   Download the software compatible with your operating system.
2.  **Explore Available Models:**
    *   After installation, browse the available open-source models.
    *   Models vary in size and capabilities. 📌 **Example:** A model might be 5.2 GB with a 128K context, while another could be 404 GB with a 160K context.
3.  **Model Parameters and Context:**
    *   The number of parameters generally indicates the model's power. More parameters usually mean better performance.
    *   Larger models require more powerful servers (e.g., on AWS, Azure, or GCP).
4.  **Choosing a Model:**
    *   For local setup, smaller models are more practical. 📌 **Example:** Llama 3.2 version offers a 1.3 GB model with 1 billion parameters.
5.  **Using Olama:**
    *   Olama simplifies running Llama models locally.
    *   Click the hyperlink on the model's detail page to get the Olama command.
6.  **Running the Olama Command:**
    *   Ensure Olama is installed.
    *   Open a terminal and run the copied Olama command.

        ```bash
        # Example Olama command (replace with the actual command from the Llama website)
        olama run llama3:8b
        ```

    *   The model will be downloaded and set up. 📝 **Note:** This may take a few minutes on the first run.
7.  **Interacting with the Model:**
    *   Once setup is complete, you can interact with the LLM directly from the terminal.

        ```bash
        # Example interaction
        What is the capital of India?
        ```

### Integrating Llama into a Spring AI Application

1.  **Project Setup:**
    *   Create a new Spring Boot project (e.g., named "LlamaApplication").
    *   This can be a copy of a previous OpenAI project.
2.  **Update Dependencies:**
    *   Replace the `spring-ai-starter-openai` dependency with `spring-ai-starter-model-llama`.

        ```xml
        <!-- Example Maven dependency -->
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-model-llama</artifactId>
        </dependency>
        ```

    *   Sync Maven changes.
3.  **Configure `application.properties`:**
    *   Remove any OpenAI API key properties.
    *   Add the following properties:

        ```properties
        spring.ai.model.chat.provider=olama
        spring.ai.model.chat.options.model=llama3:8b
        ```

    *   Replace `llama3:8b` with the appropriate model name and tag.
4.  **Controller Class:**
    *   No changes are typically needed in the controller class.
    *   Rename the application class to reflect the use of Llama (e.g., `LlamaApplication`).
5.  **Build and Run:**
    *   Build the project.
    *   Run the `LlamaApplication`.
6.  **Base URL:**
    *   By default, the application connects to `localhost:11434`.
    *   If Llama is deployed on a different server, configure the `spring.ai.model.chat.base-url` property.

        ```properties
        spring.ai.model.chat.base-url=http://your-server-ip:11434
        ```

7.  **Testing with Postman:**
    *   Use Postman or a similar tool to invoke the chat API.
    *   Send a message to the API endpoint. 📌 **Example:** "What is your name and which model are you using?"

### Key Considerations

*   **Model Size and Performance:** Larger models (more parameters) generally provide better responses but require more resources. Smaller models are suitable for unit testing or quick demos.
*   **Local vs. Remote Deployment:** Llama can be run locally or on a remote server.
*   **Data Privacy:** Running Llama locally ensures that your data remains private and within your organization.
*   **Olama simplifies the process:** Olama makes it easier to run models locally.

### Summary of Steps

1.  Run the Olama command to download and set up the model:

    ```bash
    olama run llama3:8b
    ```

2.  Add the Llama dependency to your Spring AI application.
3.  Define the following properties in `application.properties`:

    ```properties
    spring.ai.model.chat.provider=olama
    spring.ai.model.chat.options.model=llama3:8b
    ```

4.  Use the same chat controller code as before.
