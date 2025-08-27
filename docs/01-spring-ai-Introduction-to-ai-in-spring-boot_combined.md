# Spring AI – Introduction To AI In Spring Boot

Sections-
- [Spring AI – Introduction To AI In Spring Boot](#spring-ai--introduction-to-ai-in-spring-boot)
  - [1. Spring AI: From Hello World to Hello LMS](#1-spring-ai-from-hello-world-to-hello-lms)
    - [Why Spring AI is a Game Changer 🚀](#why-spring-ai-is-a-game-changer-)
    - [Course Agenda 🗓️](#course-agenda-️)
    - [Prerequisites 📝](#prerequisites-)
  - [2. What is Spring AI?](#2-what-is-spring-ai)
    - [Why Spring AI?](#why-spring-ai)
    - [Vector Store Integration](#vector-store-integration)
    - [Advanced Patterns and Features](#advanced-patterns-and-features)
    - [DevOps Perspective](#devops-perspective)
    - [Key Benefits](#key-benefits)
    - [Use Cases](#use-cases)
  - [3. Building a Hello World Spring AI Application with OpenAI](#3-building-a-hello-world-spring-ai-application-with-openai)
    - [Project Setup](#project-setup)
    - [Project Structure and Dependencies](#project-structure-and-dependencies)
    - [Creating the REST Controller](#creating-the-rest-controller)
    - [Integrating with OpenAI](#integrating-with-openai)
    - [Configuring Logging](#configuring-logging)
    - [Running the Application and Handling Errors](#running-the-application-and-handling-errors)
  - [4. Setting Up OpenAI API Key and Testing the Spring AI Application](#4-setting-up-openai-api-key-and-testing-the-spring-ai-application)
    - [Understanding OpenAI and ChatGPT 🤖](#understanding-openai-and-chatgpt-)
    - [Obtaining Your OpenAI API Key 🔑](#obtaining-your-openai-api-key-)
    - [Alternatives to Recharging 💰](#alternatives-to-recharging-)
    - [OpenAI Pricing 💲](#openai-pricing-)
    - [Configuring the API Key in Your Spring AI Application ⚙️](#configuring-the-api-key-in-your-spring-ai-application-️)
    - [Testing Your Spring AI Application with Postman 🧪](#testing-your-spring-ai-application-with-postman-)
    - [Importing the Postman Collection 📦](#importing-the-postman-collection-)
    - [Understanding Non-Deterministic Responses 🤔](#understanding-non-deterministic-responses-)
    - [Spring AI's Abstraction 🏗️](#spring-ais-abstraction-️)
  - [5. Chart Model and Chart Client Deep Dive](#5-chart-model-and-chart-client-deep-dive)
    - [Chart Model: The Engine ⚙️](#chart-model-the-engine-️)
    - [Chart Client: The Steering Wheel 🚗](#chart-client-the-steering-wheel-)
    - [Chart Model vs. Chart Client: Analogy 💡](#chart-model-vs-chart-client-analogy-)
    - [How They Work Together 🤝](#how-they-work-together-)
    - [Custom Chart Model Implementation 🛠️](#custom-chart-model-implementation-️)
    - [Conclusion ✅](#conclusion-)
  - [6. Integrating Llama with Spring AI](#6-integrating-llama-with-spring-ai)
    - [Setting Up Llama](#setting-up-llama)
    - [Integrating Llama into a Spring AI Application](#integrating-llama-into-a-spring-ai-application)
    - [Key Considerations](#key-considerations)
    - [Summary of Steps](#summary-of-steps)
  - [7. Setting Up Local LM Models Using Docker](#7-setting-up-local-lm-models-using-docker)
    - [System Requirements](#system-requirements)
    - [Configuring Docker Desktop](#configuring-docker-desktop)
    - [Running a Model](#running-a-model)
    - [Integrating with Spring AI](#integrating-with-spring-ai)
  - [8. Setting Up LM Models: Cloud Provider Approach with AWS Bedrock](#8-setting-up-lm-models-cloud-provider-approach-with-aws-bedrock)
    - [Cloud Providers: A Third Approach](#cloud-providers-a-third-approach)
      - [Key Differences from OpenAI](#key-differences-from-openai)
      - [Benefits of Using Cloud Providers](#benefits-of-using-cloud-providers)
    - [AWS Bedrock Demo](#aws-bedrock-demo)
      - [What is AWS Bedrock?](#what-is-aws-bedrock)
      - [Equivalent Services on Other Cloud Providers](#equivalent-services-on-other-cloud-providers)
      - [Logging into the AWS Console](#logging-into-the-aws-console)
      - [Accessing Bedrock](#accessing-bedrock)
      - [Exploring the Model Catalog](#exploring-the-model-catalog)
      - [Requesting Model Access](#requesting-model-access)
      - [Setting Up Credentials for Spring AI Integration](#setting-up-credentials-for-spring-ai-integration)
  - [9. Integrating Spring AI with AWS Bedrock](#9-integrating-spring-ai-with-aws-bedrock)
  - [10. Leveraging Multiple Chat Models in Spring AI Applications](#10-leveraging-multiple-chat-models-in-spring-ai-applications)
    - [Project Setup](#project-setup-1)
    - [Disabling Auto-Configuration](#disabling-auto-configuration)
    - [Creating Chat Client Beans](#creating-chat-client-beans)
    - [Creating a Controller](#creating-a-controller)
    - [Testing the Application](#testing-the-application)
    - [Key Takeaways](#key-takeaways)


---

## 1. Spring AI: From Hello World to Hello LMS

This course will guide you from basic "Hello World" applications to intelligent apps with memory, logic, reasoning, and creativity using Spring AI. Whether you're a backend developer, a Spring Boot enthusiast, or someone exploring AI with tools like ChatGPT, this course is for you.

### Why Spring AI is a Game Changer 🚀

Spring Boot allows us to build powerful REST APIs, microservices, and full-stack backend applications effortlessly. However, integrating AI and Large Language Models (LLMs) into Spring applications has traditionally been challenging. Spring AI bridges this gap, connecting Java applications to LLMs like OpenAI and Ollama.

Think of Spring AI as giving your backend services a brain 🧠. This brain can:

*   Write poetry ✍️
*   Generate images 🖼️
*   Answer support tickets 🎫
*   Perform intelligent actions autonomously

If you're already a Spring developer, you're 80% of the way to mastering Spring AI. This framework simplifies AI integration, making it feel like configuring a bean rather than battling a black box. Get ready to infuse your Spring Boot apps with AI magic! It's like giving your application a PhD in AI without the tuition fees or caffeine addiction.

### Course Agenda 🗓️

Here's what we'll cover in this course:

1.  **Basics:**
    *   Build your first Spring AI application with OpenAI models.
    *   Set up LLM models locally using Ollama and Docker.
    *   Deploy LLM models in the cloud with AWS Bedrock.
2.  **Spring AI Essentials:**
    *   Understand prompts and prompt templates.
    *   Stream responses to client applications.
    *   Customize chat interactions with chat options.
    *   Utilize built-in and custom advisors for housekeeping activities.
    *   Explore prompt stuffing to provide context to the LLM.
    *   Convert AI output into structured POJO or JSON formats.
3.  **Generative AI and LLM Foundations:**
    *   Dive into the inner workings of AI and LLM models.
    *   Decode buzzwords like tokens, embeddings, vectors, and attention mechanisms.
    *   Gain a clear understanding of how LLMs work behind the scenes.
4.  **Chat Memory:**
    *   Teach LLMs to remember conversations using Spring AI's chat memory.
    *   Enable LLMs to answer questions effectively using conversation history.
5.  **Retrieval Augmented Generation (RAG):**
    *   Make LLMs answer questions based on provided documents or PDFs.
    *   Learn to chunk, embed, vectorize, and query documents.
    *   Build apps that can search PDFs like a pro.
6.  **Tool Calling:**
    *   Overcome the limitation of LLMs not having real-time data access.
    *   Teach LLMs to check real-time data, query databases, and perform Java code actions.
7.  **Model Context Protocol (MCP):**
    *   Understand MCP, the "USB-C port for AI tools."
    *   Build your own MCP server and interact with other organizations' MCP servers.
    *   Explore both client-side and server-side implementations.
8.  **Unit Testing:**
    *   Implement unit testing for Spring AI applications.
    *   Use evaluators to validate AI responses and catch bad answers at runtime.
    *   Retry sending questions to LLMs if needed.
9.  **Observability:**
    *   Monitor your AI app using Spring Boot Actuator, Prometheus, and Grafana dashboards.
    *   Trace operations using OpenTelemetry (OTLP) and distributed tracing.
10. **Audio and Image Generation:**
    *   Explore scenarios around transcription (audio to text).
    *   Explore scenarios around text to speech (text to audio).
    *   Explore image generation using AI models.

### Prerequisites 📝

*   Strong understanding of Java and Spring Boot.
*   Basic familiarity with Docker and Postman.
    *   📝 **Note:** Don't worry if you're new to Docker and Postman; guidance will be provided.
*   Curiosity to learn AI concepts.
*   A laptop for practicing demos.
*   Openness to spend up to $5 for OpenAI demos.

    *   ⚠️ **Warning:** OpenAI requires a minimum $5 deposit.
    *   💡 **Tip:** Even if you're not comfortable spending, follow the concepts and demos. You can try them later.

---

## 2. What is Spring AI?

Imagine your Spring Boot application suddenly gets smarter! 🧠 It's not just fetching data anymore; it's giving answers, writing emails, and automating redundant tasks. Welcome to the world of Spring AI! While it might not do your laundry yet, it might tell you how to write a poem about it. 😉

Spring AI is a new project within the Spring ecosystem that brings AI capabilities, especially from Large Language Models (LLMs) like ChatGPT or DeepInfra, directly into your Spring Boot applications. Think of it as a bridge between AI models and Java developers. 🌉

In a world where AI is transforming every app and industry, Spring AI puts that power right into your Java hands with the elegance of Spring Boot. You don't have to switch languages or learn complicated SDKs provided by LLMs, or even make complex HTTP requests. If you know Spring Boot, you're already halfway there! 🎉

### Why Spring AI?

You might ask: "I can invoke LLM models using Spring Boot's REST API capabilities. Why do I need a separate framework like Spring AI?" 🤔

Here's why:

*   **Multi-Provider Support:** The GenAI market has numerous vendors offering LLM models. These vendors have different request/response styles, endpoint URLs, and security standards. Spring AI simplifies this by abstracting these complexities. You focus on business logic, and switching between models or vendors becomes super simple. 🔄
*   **Abstraction of Complexity:** As a developer, you don't want to handle all that boilerplate logic. Spring AI does the heavy lifting for you.
*   **MCP Protocol Support:** In the GenAI world, MCP (Model Communication Protocol) is emerging as a standard, similar to HTTP in the web world. Spring AI allows developers to easily build MCP client applications and MCP servers. We'll discuss MCP in detail later. 🗣️

### Vector Store Integration

Not always do we want to simply invoke an LLM and get a response. Sometimes, we want the LLM to read our organization's documents or structured data. In such scenarios, we need to store our data in a vector store database.

There's a strong reason why we should store our data in a vector store database, which we'll discuss in later lectures. For now, understand that Spring AI makes it easy to store your data in vector store databases like Pinecone, Redis, Postgres, and MongoDB. 🗄️

### Advanced Patterns and Features

Spring AI also supports advanced patterns like:

*   Chat 💬
*   Memory 🧠
*   Tool or Function Calling 🛠️
*   Building advisor APIs for reusable AI logic 🧑‍💻

### DevOps Perspective

From a DevOps perspective, Spring AI provides observability and guardrails. This allows you to monitor operations and evaluate output to ensure reliability. 🛡️

### Key Benefits

Spring AI doesn't just allow you to invoke LLM models. It provides a surrounding ecosystem by handling common problems and patterns.

*   **Strong Integration:** It has strong integration into the Spring ecosystem.
*   **Auto-Configuration and Starter Projects:** Leverages auto-configuration and starter projects for quick setup with AI models and vector databases.
*   **Familiar Concepts:** If you're a Spring Boot developer, you'll recognize familiar concepts like Spring Dependency Injection. ⚙️

### Use Cases

Here are typical use cases you can implement with Spring AI:

1.  AI-powered customer support (automating activities) 🤖
2.  Searching documents using natural language 🔎
3.  Personalized recommendations 🎁
4.  Data processing 📊
5.  Automating redundant tasks ⚙️

We'll explore exciting stuff throughout this course. By now, you should have a basic understanding of Spring AI. As we progress, you'll love this framework! ❤️

---

## 3. Building a Hello World Spring AI Application with OpenAI

Let's create a "Hello World" Spring AI application integrated with an LLM model from OpenAI (ChatGPT).

### Project Setup

1.  Navigate to [start.spring.io](https://start.spring.io). This is the standard website for creating Spring Boot applications.
2.  Configure the project details:
    *   Language: Java
    *   Build Tool: Maven (default)
    *   Spring Boot Version: Accept the default.
    *   Group: `com.knowprogram`
    *   Artifact: `openai`
    *   Description: (Optional)
    *   Package Name: (Default based on Group and Artifact)
    *   Packaging: Jar
    *   Java Version: Accept the default.
3.  Add the necessary dependencies:
    *   **Spring Web**: 🌐 To expose REST APIs.
    *   **Spring Boot DevTools**: 🛠️ For fast application restarts, live reload, and enhanced development experience.
    *   **Spring AI OpenAI Starter**: 🤖 To integrate with OpenAI's LLM models. Search for "AI" and select the OpenAI starter.

    📝 **Note:** Spring AI supports various vendors like Anthropic, Mistral AI, and Azure Stability AI. This abstraction simplifies development.
4.  Click "Generate" to create the Maven project.
5.  Extract the generated project to a folder (e.g., `section01` under a `spring` folder).
    📌 **Example:** `spring/section01/openAI.zip`
6.  Open the extracted folder in your IDE (IntelliJ IDEA is used in the lecture, but any IDE is fine).

### Project Structure and Dependencies

Open the `pom.xml` file to verify the dependencies:

```xml
<dependencies>
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-web</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.ai</groupId>
		<artifactId>spring-ai-starter-model-openai</artifactId>
	</dependency>

	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-devtools</artifactId>
		<scope>runtime</scope>
		<optional>true</optional>
	</dependency>
</dependencies>
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework.ai</groupId>
			<artifactId>spring-ai-bom</artifactId>
			<version>${spring-ai.version}</version>
			<type>pom</type>
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
```

### Creating the REST Controller

1.  Create a new package named `controller` under `com.knowprogram.openai`.
2.  Create a new Java class named `ChatController` inside the `controller` package.
3.  Annotate the class:

    ```java
    @RestController
    @RequestMapping("/api")
    public class ChatController {
        // ...
    }
    ```

4.  Create a `chat` method to handle incoming messages:

    ```java
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return message; // Placeholder for now
    }
    ```

    This simple REST API will receive a message from the end user and, for now, just return the same message.

### Integrating with OpenAI

1.  Inject a `ChatClient` bean into the `ChatController`:

    ```java
    private final ChatClient chatClient;

    public ChatController(ChatClientBuilder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }
    ```

    The `ChatClient` interface is implemented by `DefaultChatClient`.
2.  Explain the Bean Creation Process:
    *   When the OpenAI dependency (`spring-ai-starter-model-openai`) is added, Spring Boot creates a bean of type `ChatModel` interface (specifically, `OpenAIChatModel` class).
    *   The framework interacts with the OpenAI chat model using this bean.
    *   Spring Boot also creates a bean of type `ChatClientBuilder` (specifically, `DefaultChatClientBuilder`).
    *   The `ChatClientBuilder` is injected into the `ChatController`.
    *   The `build()` method of `ChatClientBuilder` creates a `DefaultChatClient` instance.
3.  Use the `ChatClient` to send the prompt to the LLM model:

    ```java
    @GetMapping("/chat")
    public String chat(@RequestParam("message") String message) {
        return chatClient.prompt(message).call().getContent();
    }
    ```

    This code uses the `ChatClient`'s fluent API:
    *   `prompt(message)`: Sets the user's message as the prompt.
    *   `call()`: Invokes the LLM model.
    *   `getContent()`: Retrieves the response from the LLM model.

### Configuring Logging

1.  Open the `application.properties` file.
2.  Add the following property to customize the console log pattern:

```properties
logging.pattern.console=%green(%d{HH:mm:ss.SSS}) %blue(%-5level) %red([%thread]) %yellow(%logger{15}) - %msg%n
```

    This pattern displays:
    *   Time (green)
    *   Log Level (blue)
    *   Thread Name (red)
    *   Logger Message (yellow)

    💡 **Tip:** Copy this value from the GitHub repository to avoid typing errors.

### Running the Application and Handling Errors

1.  Build the project using Maven.
2.  Run the Spring Boot application.
3.  You will likely encounter an exception: "OpenAI API key must be set."

    ⚠️ **Warning:** OpenAI's LLM models are not free. You need to:
    *   Create an account with OpenAI.
    *   Generate an API key in their platform.
    *   Provide the API key to the Spring AI framework.

    The next step is to set up the OpenAI API key.

**💡 Workaround for the OpenAI API Key: Generate the test API key via [https://openai.com/global-affairs/openai-academy](https://openai.com/global-affairs/openai-academy) and signup as a Developer. Create a new Organization, and generate the [test key](https://platform.openai.com/api-keys) without pay.**

---

## 4. Setting Up OpenAI API Key and Testing the Spring AI Application

This section will guide you through setting up your OpenAI API key and testing your Spring AI application.

### Understanding OpenAI and ChatGPT 🤖

It's important to understand the relationship between OpenAI and ChatGPT. Think of it this way:

*   OpenAI is like **Apple**, the company.
*   ChatGPT is like **iPhone**, one of Apple's products.

OpenAI develops various AI models, and ChatGPT is a chatbot platform that utilizes these models behind the scenes. ChatGPT is an intelligent chatbot or chat platform. Behind the scenes, it uses LLM models developed by OpenAI.

### Obtaining Your OpenAI API Key 🔑

1.  Navigate to [platform.openai.com](https://platform.openai.com). This is **NOT** the ChatGPT website.
2.  Sign up or log in. If you already use ChatGPT, log in with the same account.
3.  If signing up for the first time, provide the requested details, such as your organization name.
4.  Go to **Settings** and then **Billing**.
5.  Add at least $5 to your account. This is sufficient for completing this course. You can use your credit card regardless of your location.
6.  Once the balance is added, click on **API Keys**.
7.  Click on **Create new secret key**.
8.  Give your key a name and select a project (the default project is fine).
9.  Click **Create Secret key**.
10. Copy the generated API key (starts with `sk-`) and store it securely. This is your **secret key**.

### Alternatives to Recharging 💰

If you don't want to recharge $5, you have a couple of options:

*   Set up LMS locally (covered in later lectures). 📝 **Note:** These are not as powerful as OpenAI models.
*   Watch the demos and test them later when you can recharge.
*   Share an API key with a group of friends. ⚠️ **Warning:** Ensure responsible usage.

### OpenAI Pricing 💲

*   Click on **Pricing** in the OpenAI platform to see the current rates.
*   Prices are typically per 1 million **tokens**.
*   **Tokens** are used to calculate the cost of both input messages and model responses.
*   Smaller models like `GPT 4.1 nano` are cheaper than more powerful models.
*   Spring AI allows you to select the model during chat operations.

### Configuring the API Key in Your Spring AI Application ⚙️

1.  Open your `application.properties` file.
2.  Add the following property:

    ```properties
    spring.ai.openai.api-key=${OPENAI_API_KEY}
    ```

3.  Set the `OPENAI_API_KEY` environment variable.

    *   In IntelliJ, click the three dots next to your run configuration, select "Modify options," and then "Environment Variables."
    *   Add a new variable named `OPENAI_API_KEY` and set its value to your API key.
    *   Click "Apply" and then "OK."

### Testing Your Spring AI Application with Postman 🧪

1.  Start your Spring AI application. Go to the home directory of the project and run the application:
    ```bash
    mvn clean install
    OPENAI_API_KEY=sk-xxxxxx mvn spring-boot:run
    ```
2.  Use Postman (or a similar tool) to send a GET request to your API endpoint.
3.  Include a `message` query parameter with your prompt. 📌 **Example:**

    ```
    http://localhost:8080/api/chat?message=What is your name and which model are you using?
    ```

4.  Verify that you receive a meaningful response from the AI model.

### Importing the Postman Collection 📦

To simplify testing, a Postman collection will be provided in the GitHub repository.

1.  Download the collection JSON file from the repository.
2.  In Postman, click **Import**.
3.  Select the downloaded JSON file.
4.  The collection will be imported, allowing you to easily test various API endpoints.

### Understanding Non-Deterministic Responses 🤔

LLM models are **non-deterministic**, meaning they can provide different responses for the same prompt at different times. This is due to the probabilistic nature of these models and contributes to a more human-like interaction.

### Spring AI's Abstraction 🏗️

Spring AI simplifies the process of interacting with AI models by handling complex logic and best practices behind the scenes. You can focus on your business logic without worrying about the underlying API calls.

*   The `OpenAIChatModel` class contains the `internalCall` method, which handles the API interaction.
*   The `OpenAIApiConstants` class defines the `defaultBaseUrl`, which is the OpenAI API endpoint URL.

---

## 5. Chart Model and Chart Client Deep Dive

Let's delve deeper into the **Chart Model** and **Chart Client** within the Spring AI framework. These are fundamental components for interacting with AI models.

### Chart Model: The Engine ⚙️

The **Chart Model** is an interface that provides a low-level abstraction representing the actual AI model interface.

*   It's the core component within the Spring AI framework.
*   It defines a contract for communicating with different AI providers (e.g., OpenAI, Azure OpenAI, Anthropic Gemini).
*   The specific **Chart Model** implementation used depends on the AI starter projects added to your Spring Boot web application.
*   These implementation classes handle the actual API calls to the AI language models (LLMs).
*   📌 **Example:** `OpenAIChartModel` when using the OpenAI starter project or `GeminiChartModel` when using a Google-related starter project.
*   📝 **Note:** These model-specific implementations manage model-specific configurations and parameters.
*   Overall, the **Chart Model** implementation classes provide the foundational layer for AI interactions.

### Chart Client: The Steering Wheel 🚗

The **Chart Client** is a higher-level, more developer-friendly abstraction built on top of the **Chart Model**.

*   It provides a fluent API for easier interaction with AI models.
*   It enhances the developer experience through method chaining, simplified prompt construction, and message handling.
*   It supports both synchronous and streaming programming models.
*   You can think of the **Chart Client** as a friendly wrapper or service layer around the **Chart Model**.
*   Its purpose is to simplify working with an AI model and abstract prompt/message handling.
*   Regardless of the underlying model, the code written using the **Chart Client** remains consistent.
*   Without the **Chart Client**, you'd be working directly with various **Chart Models**, writing a lot of low-level instructions.
*   📌 **Example:** Using the `content()` method to retrieve only the AI model's response, filtering out metadata and token information.

### Chart Model vs. Chart Client: Analogy 💡

The relationship between the **Chart Model** and **Chart Client** can be understood through an analogy:

*   **Chart Model:** The engine of a vehicle, doing the heavy lifting of actual AI communication.
*   **Chart Client:** The steering wheel and dashboard, providing an intuitive interface to control the engine.

The **Chart Client** uses the **Chart Model** internally but wraps it with a more convenient API. When you use the **Chart Client**, it eventually delegates the request to the underlying **Chart Model** to make the actual API service calls.

### How They Work Together 🤝

1.  During Spring Boot application startup, based on the Spring AI dependencies, Spring Boot automatically creates the respective **Chart Model** beans.
2.  The framework also provides an auto-configured **Chart Client Builder**, already wired with the appropriate **Chart Model**.
3.  You use this builder to create an instance of the **Chart Client**.
4.  Once the **Chart Client** instance is created, you can use its fluent API to build prompts.
5.  These prompts are internally converted to the format expected by the respective **Chart Model**.
6.  The prompt/message request is delegated to the respective **Chart Model** by the **Chart Client**.
7.  The prompt/message eventually reaches the respective AI service.

In essence, the flow is:

Your Code -> Chart Client (Fluent API) -> Chart Model Implementation -> AI Provider

### Custom Chart Model Implementation 🛠️

If you need to interact with an LLM not supported by the Spring AI framework (e.g., a model developed by your own organization), you can build your own **Chart Model** implementation. This involves writing the low-level code for communicating with the LLM from your Spring AI application.

### Conclusion ✅

The design of the **Chart Model** and **Chart Client** follows a common pattern of having a low-level technical interface and a high-level user-friendly interface. This makes the framework more accessible while maintaining flexibility for advanced use cases.

---

## 6. Integrating Llama with Spring AI

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

---

## 7. Setting Up Local LM Models Using Docker

We can set up local LM models using Docker, similar to how we use it for databases, servers, and application deployments. Everything needs to be converted into a Docker container for deployment.

To get started:

1.  Install Docker Desktop. It's free for local development. Download it from the official Docker website based on your operating system.
2.  Visit [https://docs.docker.com/ai/model-runner/](https://docs.docker.com/ai/model-runner/). 📝 **Note:** This feature is currently in beta.

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
    *   💡 **Note:**  If option not available in Docker GUI, then install it through docker engine:- `sudo apt-get install docker-model-plugin`. Check the installation status using `docker model version` & `docker model run ai/smollm2`.
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

---

## 8. Setting Up LM Models: Cloud Provider Approach with AWS Bedrock

We've previously explored setting up Language Models (LMs) using the OpenAI platform and local options like Ollama and Docker. Each approach has its own set of advantages and disadvantages.

*   OpenAI offers zero setup, making it easy to get started. However, it requires paying based on consumption.
*   Ollama and Docker require more setup and maintenance but eliminate usage-based costs, only requiring you to pay the server bill.

Organizations choose between these options based on their priorities. Some prioritize ease of setup, while others prioritize cost savings or data privacy.

### Cloud Providers: A Third Approach

Beyond OpenAI and local setups, a third popular approach involves using cloud providers like AWS, Azure, and GCP. These providers offer services that allow you to quickly set up LM models within their cloud infrastructure.

#### Key Differences from OpenAI

The main difference between using OpenAI directly and using cloud providers is **model diversity**.

*   OpenAI locks you into their models.
*   Cloud providers offer a wider range of models from various companies, each specializing in different tasks (e.g., image generation, video generation).

📌 **Example:** While OpenAI excels at communication and charting, other companies offer superior models for image or video generation.

#### Benefits of Using Cloud Providers

1.  **Model Variety:** Access to a diverse range of models tailored to specific use cases.
2.  **Seamless Integration:** If your applications are already deployed on a cloud provider, integrating AI models within the same environment simplifies maintenance and integration.

### AWS Bedrock Demo

We'll now explore the cloud provider approach using AWS Bedrock as an example. You can apply the same principles to Azure and GCP.

#### What is AWS Bedrock?

[AWS Bedrock](https://aws.amazon.com/bedrock/) is a service that provides access to foundational models from various companies.

*   You can find information about Amazon Bedrock on its product page.
*   The pricing tab lists the available models from different providers.

📌 **Example:**
* Amazon offers its own foundational models like Amazon Titan.
* Other providers include Anthropic, Cohere, Meta, Mistral, and Stability AI.

⚠️ **Warning:** You won't find OpenAI models on AWS Bedrock because OpenAI is backed by Microsoft and primarily available through Azure.

#### Equivalent Services on Other Cloud Providers

*   Azure: Azure OpenAI Service
*   GCP: Google Vertex AI

#### Logging into the AWS Console

1.  Log in to the AWS console using your root user email.
2.  You'll need an AWS account with a credit card set up.
    *   If you don't have an account or prefer not to use a credit card, you can skip the setup and watch the demo.
    *   If you have an account, try the demo, as the setup and testing should only cost a few cents.
3.  Select the region.

📝 **Note:** The UI of the AWS console may change over time, but the underlying steps should remain consistent.

#### Accessing Bedrock

1.  Search for "Bedrock" in the AWS console search box.
2.  Open the Bedrock service.
3.  Click on "Model providers" or **"Model catalog"** to view available models.

📝 **Note:** The available models vary depending on the selected region.

#### Exploring the Model Catalog

The model catalog displays models from various providers, including:

*   Anthropic
*   Amazon
*   DeepSeek
*   Hugging Face
*   IBM
*   Meta
*   Mistral

💡 **Tip:** Cloud providers handle the groundwork of providing access to these foundational models, allowing you to focus on selecting the right model for your use case.

#### Requesting Model Access

By default, access to foundational models is disabled. You need to request access before using them.

1.  Click the three dots next to the model you want to use and select "Modify access".
2.  Select the models you want to access:- `Claude Sonnet 4`, and `Claude 3.7 Sonnet`.
3.  Click "Request Model Access".
4.  Select all the models you want to access and click "Next".
5.  Click "Submit".

Access is usually granted instantly or within 5-10 minutes.

#### Setting Up Credentials for Spring AI Integration

To enable communication between your Spring AI application and AWS Bedrock, you need to set up credentials.

1.  Go to IAM (Identity and Access Management).
2.  Create a new policy.
    *   Service: Bedrock
    *   Action: "InvokeModel" (under "Invoke Action") -> This is Read access.
    *   Resource: Initially, use "All resources" (wildcard "*") for the demo, but ⚠️ **Warning:** do not use this in production.  Instead, restrict access to specific models.
    *   Name: "invoke-bedrock-model-policy"
    *   Description: "Policy to invoke bedrock models"
3.  Create a new user.
    *   Name: "spring-ai-demo"
    *   Permissions Options: Attach policy directly.
    *   Attach the "invoke-bedrock-model-policy" policy.
4.  Create an access key for the user.
    *   Go to the user's "Security credentials" tab.
    *   Click "Create access key".
    *   Select "Local code" as the use case. ⚠️ **Warning:** Do not use this option for production environments.
    *   Copy the access key and secret access key to a secure location.

```text
Access Key ID: YOUR_ACCESS_KEY_ID
Secret Access Key: YOUR_SECRET_ACCESS_KEY
```

You now have the necessary credentials to configure your Spring AI application to communicate with AWS Bedrock. We will continue this discussion in the next lecture.

---

## 9. Integrating Spring AI with AWS Bedrock

This section details the steps to integrate a Spring AI project with AWS Bedrock. We'll start with an existing Spring AI project and modify it to use Bedrock instead of OpenAI.

First, we'll modify the `pom.xml` file.

Instead of an OpenAI starter project, we'll use the Bedrock connector.

```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai-starter-model-bedrock-converse</artifactId>
</dependency>
```

After adding the dependency, sync the Maven changes.

Next, we need to configure the `application.properties` file.

1.  Remove all properties related to OpenAI.
2.  Add the properties related to AWS Bedrock.

A common question is: How do I know which properties to configure for a specific model?

The answer is the official [Spring AI](https://docs.spring.io/spring-ai/reference/api/chat/bedrock-converse.html) documentation. 💡 **Tip:** Always refer to the official documentation for the most up-to-date information.

The documentation provides details on dependencies and properties for various models, including: Amazon Bedrock, Anthropic, Azure OpenAI, DeepInfra, Docker Model Runner, Google Vertex AI, Grok, Hugging Face, Mistral, Minimax, Moonshot, Nvidia, Llama, Perplexity, OpenAI

For Amazon Bedrock, the following properties need to be configured:

*   `spring.ai.bedrock.aws.region`: The AWS region.
*   `spring.ai.bedrock.aws.access-key`: The AWS access key.
*   `spring.ai.bedrock.aws.secret-key`: The AWS secret key.
*   `spring.ai.bedrock.model`: The Bedrock model ID.

📌 **Example:**

```properties
spring.ai.bedrock.aws.region=us-east-1
spring.ai.bedrock.aws.access-key=YOUR_ACCESS_KEY
spring.ai.bedrock.aws.secret-key=YOUR_SECRET_KEY
spring.ai.model.chat=bedrock-converse
spring.ai.bedrock.converse.chat.options.model=anthropic.claude-sonnet-4-20250514-v1:0
```

📝 **Note:** For the AWS region, you can find the code name in the AWS console.

⚠️ **Warning:**  Do not hardcode sensitive data like access keys and secret keys in your `application.properties` file in real applications. Use environment variables or other secure methods to inject this data.

The `spring.ai.model.chat.enabled` property is enabled by default. You can keep it for reference.

The `spring.ai.bedrock.model` property specifies the model to use from Bedrock.

To find the model ID, navigate to the AWS Bedrock service in the AWS console.

1.  Go to the Model Catalog.
2.  Select the desired model (e.g., Anthropic Claude Sonnet 4).
3.  Copy the model ID from the Inference profile (e.g., `anthropic.claude-sonnet-4-20250514-v1:0`).

After configuring the properties, build the project.

Rename the main class to `BedrockApplication` and update the file name accordingly.

During initial testing, the application might fail to start due to a missing region configuration.

It seems there is a bug where the Spring framework expects the region property to be set both in `application.properties` and as an environment variable.

To set the environment variable:

1.  Go to Edit Configurations.
2.  Modify options and select Environment variables.
3.  Add `SPRING_AI_BEDROCK_AWS_REGION` with the region value (e.g., `us-east-1`).

The environment variable name should be `SPRING_AI_BEDROCK_AWS_REGION`.

After setting the environment variable, restart the application.

If the application still fails to start, double-check the environment variable name. The framework might be expecting `SPRING_AI_BEDROCK_AWS_REGION`.

Once the application starts successfully, test the APIs using Postman.

If you encounter an **exception stating that the model ID is not supported for on-demand invocation**, it means you need to use the model ID from the inference profile instead of the model catalog.

1.  In the AWS console, navigate to Bedrock.
2.  Go to Infer -> Cross-region inference.
3.  Copy the value from the Inference profile ID column (`us.anthropic.claude-sonnet-4-20250514-v1:0`).

Update the `spring.ai.bedrock.model` property with the new model ID and restart the application.

After these steps, the integration between Spring AI and Bedrock should be working. You should receive successful responses from the Bedrock model.

This setup allows you to leverage the power of AWS Bedrock's cloud LM models within your Spring AI application.

It's possible to combine multiple vendor models within a Spring AI application. This will be covered in future lectures.

All the details discussed around AWS Bedrock are documented in the provided slides. Refer to these slides for a quick refresher on Spring AI skills.

---

## 10. Leveraging Multiple Chat Models in Spring AI Applications

In real-world enterprise applications, it's common to work with multiple chat models within the same Spring AI application. No single AI model excels at all tasks, as different models possess varying capabilities. Adopting diverse models for specific use cases is a prevalent practice.

![Why use Multiple Chat Models](img/Why%20use%20Multiple%20Chat%20Models.png)

This section outlines the steps to integrate multiple models into a Spring AI application. A sample project named "multi-modal" has been created, incorporating both a Llama model and an OpenAI model. 

### Project Setup

1.  **Model Dependencies:** Include dependencies for the desired models (e.g.,  OpenAI, Bedrock) in your `pom.xml` or `build.gradle` file.

    ```xml
    <dependency>
		<groupId>org.springframework.ai</groupId>
		<artifactId>spring-ai-starter-model-bedrock-converse</artifactId>
	</dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-model-openai</artifactId>
    </dependency>
    ```

2.  **Configuration:** Configure the necessary properties in `application.properties` or `application.yml`.

    📌 **Example:**

    ```properties
    # openai
    spring.ai.openai.api-key=

    # bedrock
    spring.ai.bedrock.aws.region=us-east-1
    spring.ai.bedrock.aws.access-key=
    spring.ai.bedrock.aws.secret-key=
    spring.ai.model.chat=bedrock-converse
    spring.ai.bedrock.converse.chat.options.model=us.anthropic.claude-sonnet-4-20250514-v1:0
    ```

### Disabling Auto-Configuration

By default, Spring AI auto-configures a `ChatClient.Builder` bean. When working with multiple models, it's necessary to disable this default behavior and create separate `ChatClient` beans for each model.

1.  Disable the default `ChatClient.Builder` auto-configuration:

    ```properties
    spring.ai.chat.client.enabled=false
    ```

### Creating Chat Client Beans

Create a configuration class to define the `ChatClient` beans for each model.

1.  Create a new package named `config`.
2.  Inside the `config` package, create a class named `ChatClientConfig`.
3.  Annotate the class with `@Configuration`.

    ```java
    @Configuration
    public class ChatClientConfig {
        // Bean definitions will go here
    }
    ```

4.  Define `ChatClient` beans for each model. Two approaches are demonstrated below.

    *   **Approach 1: Using `ChatClient.create()`**

        ```java
        @Bean
        public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
            return ChatClient.create(openAiChatModel);
        }

        @Bean
        public ChatClient bedrockChatClient(BedrockProxyChatModel bedrockModel) {
            return ChatClient.create(bedrockModel);
        }
        ```

        This method injects the `OpenAiChatModel` bean (auto-configured by Spring AI) and uses it to create a `ChatClient` bean.

    *   **Approach 2: Using `ChatClient.builder()`**

        ```java
        @Bean
        public ChatClient openAiChatClient(OpenAiChatModel openAiChatModel) {
            ChatClient.Builder chatClientBuilder = ChatClient.builder(openAiChatModel);
            // You can configure the builder here, e.g., set default system prompt
            // chatClientBuilder.defaultSystemPrompt("You are a helpful assistant.");
            return chatClientBuilder.build();
        }
        ```

        This approach provides more control over the `ChatClient` configuration. You can use methods like `defaultSystemPrompt()` to customize the model's behavior.

        💡 **Tip:** The second approach using `ChatClient.builder()` offers greater flexibility for configuring the chat client.

### Creating a Controller

Create a controller to expose REST endpoints that utilize the different chat models.

1.  Create a new package named `controller`.
2.  Inside the `controller` package, create a class named `MultiModelChatController`.
3.  Annotate the class with `@RestController` and `@RequestMapping("/api")`.

    ```java
    @RestController
    @RequestMapping("/api")
    public class MultiModelChatController {
        // REST API methods will go here
    }
    ```

4.  Inject the `ChatClient` beans into the controller using constructor injection and the `@Qualifier` annotation.

    ```java
     private final ChatClient openAiChatClient;
    private final ChatClient bedrockChatClient;

    public MultiModelChatController(
            @Qualifier("openAiChatClient") ChatClient openAiChatClient,
            @Qualifier("bedrockChatClient") ChatClient bedrockChatClient) {
        this.openAiChatClient = openAiChatClient;
        this.bedrockChatClient = bedrockChatClient;
    }
    ```

    📝 **Note:** The `@Qualifier` annotation is used to specify the bean name, which defaults to the method name in the configuration class.

5.  Create REST endpoints to interact with each chat model.

    ```java
    @GetMapping("/openai/chat")
    public String openAiChat(@RequestParam("message") String message) {
        return openAiChatClient.prompt(message).call().content();
    }

    @GetMapping("/bedrock/chat")
    public String bedrockChat(@RequestParam("message") String message) {
        return bedrockChatClient.prompt(message).call().content();
    }
    ```

### Testing the Application

1.  Build the application.
2.  Ensure that the Llama model is running locally (if using Ollama).
3.  Configure the OpenAI API key.
4.  Use a tool like Postman to test the REST endpoints.

    📌 **Example:**

    *   Send a request to `/api/openai/chat?message=What is your name and which model are you using?`
    *   Send a similar request to `/api/bedrock/chat?message=What is your name and which model are you using?`

    Verify that each endpoint returns a response from the corresponding AI model.

### Key Takeaways

*   When using multiple chat models, it's essential to create separate `ChatClient` beans for each model.
*   Disable the default `ChatClient.Builder` auto-configuration.
*   Use constructor injection and the `@Qualifier` annotation to inject the `ChatClient` beans into your business logic.
*   The approach for creating and injecting `ChatClient` beans remains consistent regardless of the complexity of the application.

---