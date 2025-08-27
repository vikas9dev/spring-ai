# 05 The Art Of Talking To Documents – Rag Unleashed


Sections-
1. [Understanding Retrieval-Augmented Generation (RAG)](#1-understanding-retrieval-augmented-generation-rag)
2. [Why RAG is Necessary for Advanced Use Cases](#2-why-rag-is-necessary-for-advanced-use-cases)
3. [Understanding Vector Databases for RAG](#3-understanding-vector-databases-for-rag)
4. [Setting Up Quadrant Vector Database Locally](#4-setting-up-quadrant-vector-database-locally)
5. [Implementing RAG in a Spring AI Application](#5-implementing-rag-in-a-spring-ai-application)
6. [Performing Search Operations on the Vector Store](#6-performing-search-operations-on-the-vector-store)
7. [Testing the New REST API](#7-testing-the-new-rest-api)
8. [RAG Implementation with Document Loading](#8-rag-implementation-with-document-loading)
9. [Building a Successful RAG Implementation: Document Splitting](#9-building-a-successful-rag-implementation-document-splitting)
10. [Retrieval Augmentation Advisor](#10-retrieval-augmentation-advisor)
11. [Understanding Embedding Calculation in Spring AI RAG Flow](#11-understanding-embedding-calculation-in-spring-ai-rag-flow)


---

## 1. Understanding Retrieval-Augmented Generation (RAG)

This section introduces the Retrieval-Augmented Generation (RAG) framework and its benefits for enhancing LLM applications. RAG helps LLMs provide more accurate and personalized responses by incorporating external knowledge.

### Limitations of Simple Prompting

![Simple Prompting](/docs/img/Simple%20Prompting.png)

Currently, we've been integrating LLM models into our applications using simple prompting. This involves sending a prompt directly to the LLM and receiving a response based on its pre-trained knowledge. However, this approach has several limitations:

*   **Hallucination:** LLMs may generate incorrect or random answers, especially when they lack sufficient information about a topic.
*   **Lack of Access to Private Data:** LLMs are trained on vast amounts of public data, but they don't have access to private or proprietary data owned by enterprises. This limits their ability to answer questions related to internal company information.
*   **Limited Personalization:** LLMs offer little to no personalization in their responses because they lack access to real-time or private data.

📌 **Example:**

*   An LLM can accurately answer questions about planets and the solar system because this information is publicly available.
*   However, if you ask an LLM about a Fortune 500 company's internal policies or proprietary data, it will likely provide a generic or incorrect answer.

### Introducing the RAG Framework 🚀

The RAG framework addresses these limitations by providing LLM models with the necessary external knowledge.

![RAG Framework](/docs/img/RAG_with_LLM.png)

In a RAG framework:

1.  A user asks a question (prompt).
2.  The system retrieves relevant context information from a knowledge base (e.g., a vector database).
3.  The prompt and the retrieved context are combined and sent to the LLM.
4.  The LLM generates a more accurate and informed response based on both the prompt and the context.

### How RAG Works ⚙️

Unlike simple prompting, RAG augments the prompt with context retrieved from a knowledge base. This knowledge base typically contains an organization's private data.

Consider a company with thousands of pages of internal documentation. When a user asks a question, RAG retrieves only the relevant information from those documents and provides it as context to the LLM.

### RAG vs. Prompt Stuffing 🆚

In previous sections, we discussed prompt stuffing, where we included all the necessary context information in the prompt. While similar in concept, prompt stuffing has limitations:

*   It requires sending large amounts of data with every prompt, increasing token consumption and costs.
*   RAG efficiently retrieves only the relevant information, reducing token usage and improving performance.

For example, if a user's question can be answered using information from only a few pages of a large document, RAG will only fetch those specific pages.

### Benefits of RAG ✅

Implementing RAG provides several benefits:

*   **Access to Private Data:** LLM applications can access and utilize private, dynamic, or domain-specific data.
*   **Personalized Responses:** LLM responses are more personalized and relevant because they are based on specific context.
*   **Improved Accuracy:** By providing relevant context, RAG reduces hallucination and improves the accuracy of LLM responses.

📝 **Note:** We will discuss vector databases and other components of the RAG framework in more detail later.

We will continue this discussion around RAG in the next lecture as well.

---

## 2. Why RAG is Necessary for Advanced Use Cases

This section details why Retrieval-Augmented Generation (RAG) is essential for advanced use cases, especially when dealing with large volumes of proprietary data.

### The Need for RAG 🚀

As discussed previously, prompt stuffing can lead to challenges, particularly regarding token consumption. RAG addresses this by:

*   Adding only the most relevant information to the prompt.
*   Keeping the prompt compact and focused.
*   Improving accuracy and reducing token waste.

📝 **Note:** This information was covered in a previous lecture but is included here for future reference.

### What Exactly is RAG? 🤔

RAG is a three-step process:

1.  **Retrieval:** Fetching relevant information.
2.  **Augmentation:** Adding the retrieved information to the prompt.
3.  **Generation:** Generating an accurate response using the augmented prompt.

By using the RAG framework, LLMs can access external knowledge, leading to better and more accurate answers.

### RAG in Action: An E-commerce Example 🛒

Imagine an e-commerce application like Amazon with millions of documents containing product information (usage, installation, precautions, etc.). This information may not be publicly available, limiting the LLM's ability to answer product-related questions accurately.

With RAG, the scenario changes:

1.  **Retriever:** An end-user asks a question about a product (e.g., "Tell me about product with ID X"). A search operation is performed on the company's product documentation (PDFs, vector database). From millions of documents, the 1-2 most relevant to the product ID are retrieved.
2.  **Augmenter:** The retrieved documents are added to the prompt as context.
3.  **Generation:** The augmented prompt (question + context) is fed to the LLM, which generates an accurate answer.

### RAG Flow Representation 🌊

![RAG Flow](/docs/img/RAG_Flow.png)

Here's another representation of the RAG flow:

1.  **Retriever:** Documents related to the prompt are searched within a vector database or storage system containing proprietary and non-proprietary documents.
2.  **Augmentation:** The fetched content is added as context to the prompt.
3.  **Generation:** The LLM generates the most accurate response using the entire information.

This flow is designed to be easily understandable, even for non-technical individuals. As developers, our responsibility is to build this RAG framework into our LLM applications.

![RAG Flow 2](/docs/img/RAG_Flow_2.png)

### Implementing the RAG Framework 🛠️

To implement the RAG flow, consider the following steps:

1.  **Load Documents into Vector Database:** Load all proprietary or private documents into a storage system like a vector database.

    ⚠️ **Warning:** There's a specific reason for using a vector database over traditional databases, which will be explained in the next lecture.

![Loading Documents to the Vector Database](/docs/img/Loading_Documents_to_the_Vector_Database.png)

1.  **Document Loading Logic:** Write a logic or job to load all the organization's documents into the vector database. This is typically a one-time activity, but updated documents can be reloaded as needed.

2.  **Document Chunking:** Original documents often have hundreds of pages covering multiple topics. Instead of loading them as-is, split them into smaller document chunks.

    📌 **Example:** A 1000-page document (D1) can be split into 1000 different document chunks. A 100-page document (D2) can be split into 100 document chunks.

3.  **Embeddings Calculation:** Calculate the embedded value for each document chunk.

4.  **Store Embeddings:** Store the calculated embedded values inside the vector store.

    💡 **Tip:** This may seem complex, but it's achievable using frameworks like Spring. A demo will be provided in future lectures.

    📝 **Note:** Ensure you're clear about the document loader's purpose. Without loading documents into the vector store, search operations will return zero results.

### RAG Implementation Flow in a Spring Application ⚙️

Once all organization documents are loaded into the vector store, the RAG framework can be implemented.

1.  **End-User Question:** The end-user asks a question to the Spring application.
2.  **Search for Similar Documents:** Based on the question, a search for similar documents is performed.
3.  **Context Augmentation:** The information received from the search is added as context to the prompt.
4.  **LLM Response:** The LLM uses the relevant information to respond to the end-user with an accurate answer.

The document loading steps (1, 2, 3) are usually a one-time activity or re-executed only when new or updated documents need to be stored in the vector store.

### Why Vector Store? 🤔

The next lecture will explain why documents should be stored in a vector store rather than traditional databases like SQL or NoSQL databases.

---

## 3. Understanding Vector Databases for RAG

To begin with Retrieval Augmented Generation (RAG), setting up a **vector store** or **vector database** is essential. Let's explore what a vector database is and why it's crucial for RAG.

### What is a Vector Database? 🤔

A vector database is a specialized database designed to store and search data in the form of vectors.

*   It stores text, image, video, or other data formats as vectors and embeddings.
*   The information is represented in a multi-dimensional format.
*   Each vector represents the semantic meaning of the data, generated using Large Language Model (LLM) embedding models.

📌 **Example:** Think of how LLMs represent word meanings using vectors and embeddings. Vector databases apply the same concept to store various types of data.

### How Embedding Models Work ⚙️

LLM providers offer models that perform embedding operations.

*   These models take input data and output a vector representation or embedded value.
*   The resulting vectors are then stored in the vector database.
*   Behind the scenes, these models utilize embedding algorithms developed by researchers.

### Why Use Vectors? 🤔

Traditional databases rely on keyword searches, which have limitations. Vector search, on the other hand, understands context and meaning, enabling semantic search.

*   Traditional keyword search fetches exact or similar words.
*   Vector search understands the context and meaning, enabling semantic search.

📌 **Example:**
Searching "how to fix a laptop screen" in a vector database can retrieve content related to repairing a broken display, even if the exact words don't match.

### Available Vector Databases 🗄️

There are numerous vector databases available, including both commercial and open-source options. Some popular choices include:

*   Azure AI Search
*   Cassandra
*   Elasticsearch
*   MongoDB
*   Neo4j
*   Pinecone
*   PostgreSQL with PGVector extension

📝 **Note:** This is not an exhaustive list. Choose a database based on your project requirements.

### How Vector Stores Enable RAG 🔄

Vector databases are the backbone of RAG. Here's how they enable the process:

1.  **Indexing:** Documents are converted into vector embeddings and stored in the vector database. 📚
2.  **Semantic Retrieval:** When a user provides a prompt, it's converted into a vector embedding. 🗣️
3.  **Similarity Search:** The query vector is used to search the vector store for similar document chunks. 🔎
4.  **Prompt Injection:** The retrieved chunks are injected into the prompt. 💉
5.  **Accurate Answers:** The LLM generates accurate answers using the relevant context. ✅

### Vector Store vs. Traditional Databases 🆚

| Feature             | Traditional Databases             | Vector Stores                               |
| ------------------- | --------------------------------- | ------------------------------------------- |
| Data Format         | Structured or Semi-structured       | Unstructured (text, images, audio, video)   |
| Search Operation    | Keyword-based or SQL queries        | Semantic (meaning-based)                    |
| Use Cases           | CRUD operations, report generation | AI, NLP, recommendations, search scenarios |
| Similarity Matching | Not easily identifiable            | Easily achievable                           |

### Spring AI Framework and Vector Databases 🌷

The Spring AI framework supports integration with various vector databases.

*   The official documentation includes a section on [Vector Databases](https://docs.spring.io/spring-ai/reference/api/vectordbs.html).
*   Supported databases include:
    *   Azure AI Search
    *   Chroma
    *   Couchbase
    *   Elasticsearch
    *   MariaDB
    *   MongoDB Atlas (Atlas is the one that makes MongoDB act like a vector database)
    *   Neo4j
    *   OpenSearch
    *   Oracle
    *   PGVector
    *   Pinecone
    *   Qdrant
    *   Redis

📝 **Note:** This list is expected to grow with future releases.

The documentation highlights that vector databases are essential for AI applications. The typical workflow involves:

1.  Loading data into the vector database.
2.  Sending a user query to the LLM.
3.  Retrieving similar documents to provide context.
4.  Sending the documents and query to the LLM.

This technique is known as Retrieval Augmented Generation (RAG).

💡 **Tip:** Refer to the official Spring AI documentation for detailed information and guidance when working on real-world projects.

### Next Steps 🚀

In the next lecture, we'll set up a vector database to use in our RAG flow.

---

## 4. Setting Up Quadrant Vector Database Locally

This section outlines how to set up the Quadrant vector database locally using Docker. Quadrant is an open-source vector database widely used in the industry due to its ease of setup. The concepts and code demonstrated here are applicable regardless of the specific vector database you choose.

### Prerequisites

*   🐳 **Docker Desktop:** Ensure Docker Desktop is installed on your system.
*   🔑 **Docker Account:** Log in to your Docker account within Docker Desktop.

### Steps

1.  **Add Spring Boot Docker Compose Dependency:**
    Add the `spring-boot-docker-compose` dependency to your `pom.xml` file. This dependency facilitates the automatic setup of dependent Docker containers during application startup.

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-docker-compose</artifactId>
        <scope>runtime</scope>
    </dependency>
    ```

2.  **Create `compose.yml` File:**
    Create a file named `compose.yml` in the root directory of your project (not under `src` or `resources`).

    📝 **Note:** Using the default name `compose.yml` avoids the need to specify its location in `application.properties`.

3.  **Configure `compose.yml`:**
    Paste the following configuration into the `compose.yml` file. This configuration defines a service named `quadrant` using the `qdrant/qdrant` Docker image and maps ports 6333 (HTTP) and 6334 (gRPC).

    ```yaml
    services:
        qdrant:
            image: 'qdrant/qdrant:v1.13.0' 
            ports:
            - "6333:6333"
            - "6334:6334"
    ```

    📝 **Note:** These configurations are based on the official Quadrant documentation. Refer to the documentation for more details.

    Now whenever we start the Spring Boot application, Docker will automatically start the a container using Quadrant image.

4.  **Add Additional Dependencies to `pom.xml`:**
    Include the following dependencies in your `pom.xml` file:

    ```xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-rag</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-advisors-vector-store</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.ai</groupId>
            <artifactId>spring-ai-starter-vector-store-qdrant</artifactId>
        </dependency>
    </dependencies>
    ```

    *   `spring-ai-rag`: Enables Retrieval-Augmented Generation (RAG) implementation.
    *   `spring-ai-spring-store`: Provides advisors around vector stores.
    *   `spring-ai-starter-vector-qdrant`: Offers seamless integration between Spring Boot and Quadrant.

5.  **Sync Maven Changes:**
    After adding the dependencies, synchronize the Maven changes.

6.  **Configure `application.properties`:**
    Add the following properties to your `application.properties` file:

    ```properties
    spring.docker.compose.stop.command=down
    spring.ai.vectorstore.qdrant.initialize-schema=true
    spring.ai.vectorstore.qdrant.host=localhost
    spring.ai.vectorstore.qdrant.port=6334
    spring.ai.vectorstore.qdrant.collection-name=easybytes
    ```

    *   `spring.docker.compose.stop.command=down`:  Ensures the container is completely deleted when the application stops.
    *   `spring.ai.vectorstore.qdrant.initialize-schema=true`:  Instructs the framework to initialize the required schema upon Quadrant database creation.
    *   `spring.ai.vectorstore.qdrant.host=localhost`: Specifies the host for the Quadrant database (default is localhost).
    *   `spring.ai.vectorstore.qdrant.port=6334`: Sets the port for gRPC communication with the Quadrant database (default is 6334).
    *   `spring.ai.vectorstore.qdrant.collection-name=easybytes`: Defines the name of the collection (similar to a table in SQL databases).

7.  **Start the Application:**
    Run your Spring Boot application. The Spring Boot framework will automatically start a Docker container using the Quadrant Docker image.

8.  **Verify Container Startup:**
    Check the application logs for information related to Docker Compose. You should see logs indicating that the framework detected the `compose.yml` file and executed Docker CLI commands to create and start the container.

    The logs should show steps like:
    *   Creating a network.
    *   Creating a container.
    *   Starting the container.
    *   Waiting for the container to become healthy.

9.  **Access Quadrant Dashboard:**
    Open your Docker Desktop and navigate to the containers section. You should see a container named `spring-ai-qdrant-1` running on ports 6333 and 6334.

    Access the Quadrant dashboard by opening the following URL in your browser: `http://localhost:6333/dashboard`.

    You should see a collection named `easybytes` in the dashboard.

### Conclusion

By following these steps, you should have successfully set up the Quadrant vector database locally. Ensure that your database is running correctly and that you can access its dashboard.

---

## 5. Implementing RAG in a Spring AI Application

This section details the initial steps for implementing Retrieval Augmented Generation (RAG) in a Spring AI application, focusing on loading information into a vector store.

### Loading Data into the Vector Store

The first step in implementing RAG is to load the information into the vector store. We'll start with a simple example and then move to a more complex one that closely resembles a real-world application.

1.  **Create a `rag` Package and `RandomDataLoader` Class:**
    Create a new package named `rag` and within it, create a class named `RandomDataLoader`.

    ```java
    package com.knowprogram.openai.rag;
    import org.springframework.stereotype.Component;
    @Component
    public class RandomDataLoader {
    }
    ```

2.  **Annotate with `@Component`:**
    Mark the `RandomDataLoader` class as a Spring component using the `@Component` annotation. This allows Spring to manage it as a bean.

3.  **Inject `VectorStore` Dependency:**
    Inject the `VectorStore` dependency into the `RandomDataLoader` class.

    ```java
    package com.knowprogram.openai.rag;
    import lombok.RequiredArgsConstructor;
    import org.springframework.ai.vectorstore.VectorStore;
    import org.springframework.stereotype.Component;
    @Component
    @RequiredArgsConstructor
    public class RandomDataLoader {
        private final VectorStore vectorStore;
    }
    ```

    📝 **Note:** Since we've configured a `VectorStore` dependency (e.g., using Quadrant) in our Spring Boot application, Spring will automatically create a bean of type `VectorStore` (specifically, a `QuadrantVectorStore` instance) during startup.

4.  **Create a Method to Load Sentences:**
    Create a method named `loadSentencesIntoVectorStore` to handle the data loading. Annotate it with `@PostConstruct`.

    ```java
    @PostConstruct
    public void loadSentencesIntoVectorStore() {
        // Data loading logic will go here
    }
    ```

    📝 **Note:** The `@PostConstruct` annotation ensures that this method is executed immediately after the bean is created by the Spring framework.

    ⚠️ **Warning:** While `@PostConstruct` is used here for demo purposes, in a real-world application, data loading should be handled by a scheduled task or a dedicated job.

    ```java
    @PostConstruct
    public void loadSentencesIntoVectorStore() {
        List<String> sentences = Arrays.asList(
                "Java is used for building scalable enterprise applications.",
                "Python is commonly used for machine learning and automation tasks.",
                "JavaScript is essential for creating interactive web pages.",
                "Docker packages applications into lightweight containers.",
                "Kubernetes automates container orchestration at scale.",
                "Redis is an in-memory data store used for caching.",
                "PostgreSQL supports complex queries and full ACID compliance.",
                "Kafka is a distributed event streaming platform.",
                "REST APIs allow stateless client-server communication.",
                "GraphQL enables clients to fetch exactly the data they need.",
                "Credit scores influence the interest rates on loans.",
                "Mutual funds pool money from investors to buy securities.",
                "Bitcoin operates on a decentralized peer-to-peer network.",
                "Ethereum supports smart contract deployment.",
                "The stock market opens at 9:30 a.m. EST on weekdays.",
                "Compound interest increases investment returns over time.",
                "Diversifying investments reduces overall risk.",
                "A blockchain is a distributed, immutable ledger of transactions.",
                "Photosynthesis is how plants convert sunlight into energy.",
                "The water cycle involves evaporation, condensation, and precipitation.",
                "The ozone layer protects Earth from harmful ultraviolet rays.",
                "Earth revolves around the Sun in an elliptical orbit.",
                "Lightning is a discharge of electricity caused by charged clouds.",
                "DNA is the molecule that carries genetic instructions in living organisms.",
                "Volcanoes form when magma rises through Earth's crust.",
                "Earthquakes are caused by sudden tectonic shifts.",
                "The Sahara is the largest hot desert in the world.",
                "Mount Kilimanjaro is the tallest mountain in Africa.",
                "Japan is known for its cherry blossoms and advanced technology.",
                "The Great Wall of China is over 13,000 miles long.",
                "Niagara Falls is located between Canada and the U.S.",
                "The Amazon River is the second longest river in the world.",
                "Oats are high in fiber and help reduce cholesterol.",
                "Drinking water improves digestion and skin health.",
                "A balanced diet includes proteins, carbs, fats, and vitamins.",
                "Broccoli is rich in vitamins A, C, and K.",
                "Green tea contains antioxidants beneficial for metabolism.",
                "Too much sugar increases the risk of diabetes.",
                "Walking 30 minutes a day improves cardiovascular health.",
                "Meditation can reduce stress and improve focus.",
                "Gratitude journaling is linked to higher happiness levels.",
                "Deep breathing exercises help regulate anxiety.",
                "Reading daily improves vocabulary and cognitive function.",
                "Setting daily goals increases productivity.",
                "STEM stands for Science, Technology, Engineering, and Mathematics.",
                "Bloom’s taxonomy categorizes educational goals.",
                "Project-based learning enhances student engagement.",
                "Online courses offer flexibility for remote learners.",
                "Flashcards are effective for memorizing vocabulary.",
                "Agile methodology promotes iterative software development.",
                "OKRs help align team goals with business strategy.",
                "Remote work offers flexibility but requires clear communication.",
                "CRM systems manage customer relationships and sales pipelines.",
                "SWOT analysis identifies strengths, weaknesses, opportunities, and threats."
        );

        List<Document> documents = sentences.stream()
                .map(Document::new)
                .toList();

        vectorStore.add(documents);
    }
    ```

    This code snippet does the following:

    *   Creates a list of sentences covering various topics.
    *   Converts each sentence into a `Document` object using `Stream.map`.
    *   Collects all the documents into a list.
    *   Adds the list of documents to the `VectorStore` using the `add` method.

    📌 **Example:** The sentences cover topics like Java, Python, JavaScript, Docker, credit scores, mutual funds, Bitcoin, Ethereum, the stock market, compound interest, photosynthesis, and more.

    💡 **Tip:** Ensure you select the correct `Document` class from `org.springframework.ai.document.Document` to avoid import conflicts.

5.  **Build the Application:**
    Build the Spring Boot application to ensure there are no compilation errors.

### Building a REST API for RAG

Now that the data is loaded into the vector store, the next step is to build a REST API that leverages RAG.

1.  **Create a `RagController`:**
    Create a new controller class named `RagController` inside the `controller` package.

    ```java
    package com.example.ai.controller;

    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    @RequestMapping("/api/rag")
    public class RagController {

    }
    ```

2.  **Add Annotations:**
    Annotate the class with `@RestController` and `@RequestMapping("/api/rag")` to define it as a REST controller with a base path.

3.  **Inject Dependencies:**
    Inject `ChatClient` and `VectorStore` dependencies into the `RagController`.

    ```java    
    @RestController
    @RequestMapping("/api/rag")
    @RequiredArgsConstructor
    public class RagController {
        private final ChatClient chatMemoryChatClient;
        private final VectorStore vectorStore;
    }
    ```

4.  **Create a Prompt Template File:**
    Create a new prompt template file named `system-prompt-random-data-template.st` in the `resources/prompt-template` directory.

    ```text
    You are a helpful assistant answering questions based upon the given context in the documents section and no prior knowledge.
    If the answer is not in the documents section, then reply with I don't know.

    DOCUMENTS:
    -----------
    {documents}
    -----------
    ```

    This template defines the system message for the LLM, instructing it to answer questions based only on the provided documents.

5.  **Load the Prompt Template:**
    In the `RagController`, load the prompt template as a resource.

    ```java
    import org.springframework.core.io.Resource;
    import org.springframework.beans.factory.annotation.Value;

    @RestController
    @RequestMapping("/api/rag")
    @RequiredArgsConstructor
    public class RagController {

        private final ChatClient chatMemoryChatClient;
        private final VectorStore vectorStore;

        @Value("classpath:/prompt-template/system-prompt-random-data-template.st")
        private Resource systemPromptTemplate;

        @GetMapping("/random/chat")
        public String randomChat(
            @RequestParam String message, 
            @RequestHeader String username) {
            // ...
        }
    }
    ```

---

## 6. Performing Search Operations on the Vector Store

To perform a search operation on the vector store, you need to create a `SearchRequest` object. This object is part of the `org.springframework.ai.vectorstore` framework.

Here's how to construct the `SearchRequest`:

1.  Use the `SearchRequest.builder()` method.
2.  Invoke the `query()` method, passing the user's prompt (the message received from the end user).

```java
SearchRequest searchRequest = SearchRequest.builder()
    .query(message)
    .build();
```

You can fine-tune the search operation using other methods.

### Fine-Tuning Search Operations

*   **Top K Results:** Use the `topK()` method to specify the number of top documents to consider. 📌 **Example:** `topK(3)` will consider only the top three documents.  💡 **Tip:** Choose a reasonable number like 3 or 5 to balance accuracy and token consumption.  The more documents considered, the more tokens are consumed when passing the information to the LLM.

*   **Similarity Threshold:** Use the `similarityThreshold()` method to set a minimum probability threshold for document similarity. 📌 **Example:** `similarityThreshold(0.5)` will only consider documents with a similarity probability of 50% or higher.

    *   The `similarityThreshold()` method accepts values between `0.0` and `1.0`.
    *   `0.0` means any similarity is accepted.
    *   `1.0` means an exact match is required.
    *   📝 **Note:** Refer to the method documentation for more details.

```java
SearchRequest searchRequest = SearchRequest.builder()
    .query(message)
    .topK(3)
    .similarityThreshold(0.5)
    .build();
```

*   **Filter Expressions:**  There are other methods like `filterExpression()` to provide filter criteria for the search operation.

### Querying the Vector Store

Once you have the `SearchRequest` object, you can query the vector store using the `similaritySearch()` method.

1.  Invoke the `similaritySearch()` method on the `VectorStore` object.
2.  Pass the `SearchRequest` object as input.

```java
List<Document> similarDocs = vectorStore.similaritySearch(searchRequest);
```

If you don't need to fine-tune the search, you can directly pass the query to the `similaritySearch()` method.

The output will be a list of `Document` objects that are similar to the input message.

### Processing the Search Results

The following code snippet processes the list of similar documents to extract the text content and concatenate them into a single string, separated by line separators.

```java
String similarContext = similarDocs.stream()
    .map(Document::getText)
    .collect(Collectors.joining(System.lineSeparator()));
```

### Integrating with Prompt Template and LLM

The extracted `similarContext` is then passed as input to a prompt template and used to interact with the LLM.

1.  Use the `ChatClient` to invoke the `prompt()` method, followed by the `system()` method.
2.  Pass a lambda expression to the `system()` method that accepts a `PromptSystemSpec` variable.
3.  Use the `text()` method to pass the prompt template.
4.  Use the `param()` method to pass the `documents` key with the `similarContext` as the value.
5.  Invoke the `advisors()` method, passing the user name as the conversation ID.
6.  Invoke the `user()` method, passing the message received from the end user.
7.  Finally, invoke the `call().getContent()` method to get the answer from the LLM.

```java
String answer = chatMemoryChatClient
                .prompt()
                .system(promptSystemSpec -> promptSystemSpec.text(promptTemplate).param("documents", similarContext))
                .advisors(a -> a.param(CONVERSATION_ID, username))
                .user(message)
                .call()
                .content();
```

See complete method here: [RagController](/sec05/spring-ai/src/main/java/com/knowprogram/openai/controller/RagController.java)

### Application Lifecycle and Vector Store Data

⚠️ **Warning:**  The application is configured to delete the vector store container when it stops. This is controlled by a property (likely in a configuration file).

If the container is not deleted, restarting the application will add new data to the existing vector store.

⌨️ **Shortcut:** To avoid writing logic to check for duplicate data, the application is configured to always delete and recreate the container on startup: `spring.docker.compose.stop.command=down`. This ensures a clean slate for each run.

Restart the application and check the vector store dashboard. You should see new data added to the vector store.

### Vector Store Dashboard

The vector store dashboard provides insights into the data stored.

*   The "Points" indicate the number of documents created.
*   Each document's content is converted into a multi-dimensional vector embedding.
*   The "Find Similar" option allows you to fetch similar documents.
*   The graph representation provides a visual representation of the document relationships (limited to five dimensions for display purposes).

In real applications, tuning the search operation using `topK()` and `similarityThreshold()` is crucial for relevance and performance.

---

## 7. 🚀 Testing the New REST API with Vector Store Integration

We’re ready to test our REST API! To observe the data flow, I set a breakpoint in the controller.

### 🌌 Query 1: "Mars" (not in dataset)

`curl --location 'http://localhost:8080/api/rag/random/chat?message=Tell%20me%20about%20MARS' \
--header 'username: madan11'`

* **Query:** *"Tell me about Mars"*
* **Context retrieved:** Sahara, Japan, and Earth (most semantically related sentences).
* **Expected:** Since none mention Mars → LLM should respond *"I don’t know."*
* **Result:** ✅ Correct!

### ⚡ Query 2: "Redis" (present in dataset)

* **Query:** *"What do you know about Redis?"*
* **Context retrieved:** Redis, REST APIs, and PostgreSQL sentences.
* **Result:** ✅ Response: *"Redis is an in-memory data store used for caching."*

### 💾 Query 3: "MySQL" (not in dataset)

* **Query:** *"Tell me about MySQL."*
* **Context retrieved:** PostgreSQL, Redis, and Java sentences.
* **Result:** ✅ Correct *"I don’t know."*

### 🚶 Query 4: "Walking daily" (semantic match)

* **Query:** *"What happens if I walk daily?"*
* **Context retrieved:**

  * Walking 30 minutes improves cardiovascular health
  * Setting daily goals
  * Reading daily
* **Result:** ✅ Correctly pulled lifestyle-related sentences
* **Response:** *"Walking 30 minutes a day improves cardiovascular health."*

💡 **Tip:** You can improve responses by refining the **system prompt**. For example, ask the LLM to provide **descriptive answers** instead of just echoing context.

### 🌊 Query 5: "Niagara Falls" (present in dataset)

* **Query:** *"What do you know about Niagara Falls?"*
* **Result:** ✅ *"Niagara Falls is located between Canada and the US."*

### 💰 Query 6: "Investments" (edge case)

* **Query:** *"Tell me about investments."*
* **Context retrieved:** Diversification, compound interest, and mutual funds
* **Result:** 😕 Initially returned *"I don’t know."*
* **Retry with “?”** → *"Tell me about investments?"* → 🎉 Meaningful response

⚠️ **Observation:** LLMs can be unpredictable. It’s safer for them to say *"I don’t know"* than to generate incorrect information.

### 🔄 Important Note on DevTools

⚠️ **Warning:** If you still have the `spring-boot-devtools` dependency in your `pom.xml`, remove it.

* With DevTools, every rebuild restarts the app in the background
* This causes your **random data loader** to reload and duplicate entries in the vector store

✅ Solutions:

1. Remove `devtools` dependency
2. Or update your loader logic to check if data already exists before re-inserting

### 🎯 Wrap-Up

* We’ve validated our RAG flow with **known, unknown, and semantically related queries**
* The vector store is proving its power — retrieving context by **meaning**, not just keywords
* We also learned that prompt design and application setup (like avoiding duplicate data) are critical for reliable results.

📝 **Note:** Example of devtools dependency in pom.xml:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <optional>true</optional>
</dependency>
```

---

## 8. RAG Implementation with Document Loading

This section details the process of implementing Retrieval Augmented Generation (RAG) using a specific [HR policy document](/sec05/spring-ai/src/main/resources/Eazybytes_HR_Policies.pdf). The goal is to enable a language model (LLM) to answer questions accurately based on the document's content.

For demonstration purposes, a PDF document containing the HR policies of a fictional company called Easy Bytes was created using ChatGPT. The document covers various aspects of employment, including: Code of conduct, Employment policies (equal opportunity, probation period), e.t.c.

The document contains some intentionally "abnormal" details, such as salaries being credited on the ninth of the month and working hours from 2 PM to 11 PM. This is to ensure the LLM relies on the document's content rather than general knowledge.

The implementation involves the following steps:

1.  **Loading the Document:**
    *   A new class, `HRPolicyLoader`, is created to handle the document loading.
    *   The `@Component` annotation marks it as a Spring component.
    *   A `vectorStore` dependency is injected.
    *   A `@Value` annotation is used to specify the location of the PDF file within the resources directory.
    *   📝 **Note:** The [PDF file](/sec05/spring-ai/src/main/resources/Eazybytes_HR_Policies.pdf) should be downloaded from the GitHub repository and placed in the `resources` directory.

    ```java
    @Component
    @RequiredArgsConstructor
    public class HRPolicyLoader {
        private final VectorStore vectorStore;

        @Value("classpath:/Eazybytes_HR_Policies.pdf")
        private final Resource hrPolicyPdf;

        @PostConstruct
        public void loadPdf(){
            // ...
        }
    }
    ```

2.  **Reading the PDF:**
    *   [Apache Tika](https://tika.apache.org/) is used to extract text from the PDF.
    *   Apache Tika is a powerful library that supports reading text from various file types (PPT, XLS, PDF, Word documents, etc.).
    *   A dependency for `spring-document-reader` is added to the `pom.xml` file.
    ```xml
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-tika-document-reader</artifactId>
        <version>1.0.0</version>
    </dependency>
    ```
    *   A `TikaDocumentReader` object is created, passing the PDF file location to its constructor.
    *   The `get()` method of the `TikaDocumentReader` is invoked to retrieve the document content as a list of `Document` objects.
    ```java
    import org.springframework.ai.reader.tika.TikaDocumentReader;

    TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(hrPolicyPdf);
    List<Document> documents = tikaDocumentReader.get();
    ```
3.  **Storing in Vector Store:**
    *   The list of `Document` objects is added to the vector store using the `add()` method. `vectorStore.add(documents);`
    *   ⚠️ **Warning:**  Initially, the document is not split into smaller chunks, which can lead to performance issues and increased token consumption. We will address this in the next section.
4.  **Creating a REST API:**
    *   A new REST API endpoint (`/document/chat`) is created in the `RagController`.
    *   The API uses a system prompt template to instruct the LLM.
    *   The system prompt instructs the LLM to answer questions about Easy Bytes company policies based on the provided context.
    *   If the answer is not found in the document, the LLM should respond with "I don't know."
    *   The [HRSystemTemplate.st](/sec05/spring-ai/src/main/resources/prompt-templates/HRSystemTemplate.st) is used instead of the original `promptTemplate`.
    ```txt
    You are a helpful assistant, answering questions about EazyBytes company policies to it's employees.

    Given the context in the DOCUMENTS section and no prior knowledge, answer the employee's question. If the answer is not in the DOCUMENTS section, then reply with "I don't know".

    DOCUMENTS:
    ----------
    {documents}
    ----------
    ```
5.  **Token Usage Tracking:**
    *   A `TokenUsageAuditAdvisor` is created to track token usage details.
    *   The advisor is added to the `ChatMemoryChatClientConfig` to log token consumption.
    ```java
    @Bean("chatMemoryChatClient")
    public ChatClient chatMemoryChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
        Advisor loggerAdvisor = new SimpleLoggerAdvisor();
        Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
        Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
        return chatClientBuilder.defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor)).build();
    }
    ```   
6.  **Testing the Implementation:**
    *   A question about working hours is sent to the API:- `curl --location 'http://localhost:8080/api/rag/document/chat?message=What%20are%20the%20working%20hours%3F' \
--header 'username: madan12'`
    *   The LLM correctly answers based on the document's content.
    *   Token usage details are examined, **revealing a high number of prompt tokens due to the entire document being included in the context**.
    *   A breakpoint is set in the `RagController` to verify that the entire document content is being sent to the LLM.
    *   Another question about the notice period is asked, and the LLM provides the correct answer.

The initial implementation stores the entire document as a single chunk in the vector store. This approach has drawbacks:

*   Increased token consumption, as the entire document is sent with each request.
*   Potential performance issues with large documents.

The next step is to split the document into smaller chunks to improve efficiency and reduce token usage. 💡 **Tip:** Proper chunking is crucial for effective RAG implementation.

---

## 9. Building a Successful RAG Implementation: Document Splitting

To create an effective Retrieval-Augmented Generation (RAG) implementation, it's crucial to split your original documents into smaller, manageable chunks before storing them in your vector store. Here's how you can achieve this:

### Document Splitting Process

1.  **Using a Text Splitter:** Employ a text splitter class, such as `TokenTextSplitter`, to divide your documents.

    ```java
    TextSplitter textSplitter = TokenTextSplitter.builder()
            .withChunkSize(100)
            .withMaxNumChunks(400)
            .build();
    ```

2.  **Configuring Chunk Size:**
    *   Specify the `chunkSize` to control the approximate size (in tokens) of each document chunk. 📌 **Example:** Setting `chunkSize` to 100 means each chunk will contain roughly 100 tokens.
    *   Set `maxNumChunks` to limit the maximum number of tokens in a single document. 📌 **Example:** Setting `maxNumChunks` to 400 ensures no document exceeds 400 tokens.

3.  **Splitting Documents:** Use the `split` method of the text splitter to divide your documents into smaller chunks.

    ```java
    List<Document> splittedDocs = textSplitter.split(docs);
    ```

    This will take your original documents (`docs`) and return a list of smaller, split documents (`splittedDocs`).

4.  **Storing Chunks:** Add the resulting smaller documents to your vector store.

```java
@PostConstruct
public void loadPdf() {
    TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(hrPolicyPdf);
    List<Document> documents = tikaDocumentReader.get();

    TokenTextSplitter textSplitter = TokenTextSplitter.builder()
            .withChunkSize(100)
            .withMaxNumChunks(400)
            .build();

    List<Document> splittedDocs = textSplitter.split(documents);
    vectorStore.add(splittedDocs);
}
```

### Understanding Chunk Size Parameters

*   **Chunk Size:** This defines the approximate size (in tokens) of each small document generated. The framework will aim for this size.
*   **Max Num Chunks:** This sets the upper limit on the number of tokens a single document can contain.

📝 **Note:** There's also a `withMinimumChunkSizeCache` method that allows you to set a minimum chunk size, but you should be comfortable with the configured `chunkSize` and `maxNumChunks` when using it.

### Optimizing RAG Performance

1.  **Token Consumption:** Splitting documents effectively reduces token consumption, leading to cost savings and faster processing. 📌 **Example:** Reducing the number of tokens from 1300 to 531 demonstrates a significant improvement.

2.  **Top K Parameter:** The `topK` parameter determines how many relevant documents are considered for context.

    *   Reducing `topK` can further decrease token usage. 📌 **Example:** Changing `topK` from 3 to 2 resulted in a reduction to 41 prompt tokens.
    *   ⚠️ **Warning:** It's generally recommended to keep `topK` at least at 3 to provide sufficient context to the LLM for better response quality.

3.  **Finding the Sweet Spot:** Experiment with different `topK` values and chunk size configurations to find the optimal balance between token consumption and response quality for your specific use case. 💡 **Tip:** Thorough testing is crucial to identify the best settings for your business requirements.

### Example Scenario

Imagine you ask the question, "Tell me about the notice period." With effective document splitting:

*   The system retrieves only the most relevant documents. 📌 **Example:** Documents related to exit policy, leave policy, and new hire probation periods.
*   The LLM receives a focused context, leading to more accurate and efficient responses.

---

## 10. Retrieval Augmentation Advisor

In previous lectures, we built two REST APIs using the RAG pattern, manually searching the vector store by writing code. Now, we'll explore how to automate this process using the Retrieval Augmentation Advisor.

Instead of manually populating search requests and iterating through documents, the Retrieval Augmentation Advisor handles this automatically. Let's integrate this advisor into our REST APIs.

**Configuration Steps:**

1.  **Create a Retrieval Augmentation Advisor Bean:**
    *   Go to the `ChatMemoryChatClientConfig` class.
    *   Create a new bean of type `RetrievalAugmentationAdvisor`.
    *   This method requires a bean of `vector store` to know where to query for similar documents.

2.  **Build the Advisor:**
    *   Use the following code snippet to build the advisor:

    ```java
    @Bean
    RetrievalAugmentationAdvisor retrievalAugmentationAdvisor(VectorStore vectorStore){
        RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(VectorStoreDocumentRetriever.builder()
                    .vectorStore(vectorStoreBean)
                    .topK(3)
                    .similarityThreshold(0.5)
                    .build())
                .build();
        return advisor;
    }
    ```

    *   `VectorStoreDocumentRetriever.builder()`:  Creates a document retriever.
    *   `.vectorStore(vectorStoreBean)`: Passes the vector store bean.
    *   `.topK(3)`:  Specifies the number of documents to retrieve (in this case, 3).
    *   `.similarityThreshold(0.5f)`: Sets the similarity threshold for document retrieval.
    *   `.build()`:  Builds the `VectorStoreDocumentRetriever` object.
    *   `RetrievalAugmentationAdvisor.builder().documentRetriever(...).build()`: Builds the `RetrievalAugmentationAdvisor` object using the configured document retriever.

3.  **Configure the Chat Client:**
    *   Pass the newly created advisor as a dependency to the method where you create the `chat client` bean.
    *   Inject the advisor into the list of advisors for the chat client.

    ```java
    public ChatClient chatMemoryChatClient(ChatClient.Builder chatClientBuilder, 
        ChatMemory chatMemory, 
        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor) { // pass the advisor

        // ...
        return chatClientBuilder.defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor, retrievalAugmentationAdvisor)).build();
    }
    ```

4.  **Update the REST Controller:**
    *   Comment out the manual code for populating the search request, searching the vector store, and extracting text from documents. This is no longer needed.
    *   Comment out the code related to populating the system prompt. The framework will handle this automatically based on the context information fetched from the vector store.

**Example Code Changes (Rack Back Controller):**

```java
// Comment out manual vector store search code
// ...

// Comment out manual system prompt population
// ...
```

**Testing the Changes:**

1.  Build the application.
2.  Restart the application.
3.  Test the changes using Postman. Send the same question you used previously.

**Expected Outcome:**

*   You should receive the same accurate response as before.
*   The framework automatically populates the system prompt with context information fetched from the vector store.

**System Prompt Content:**

The framework sends the following information in the system prompt:

*   Context information fetched from the database.
*   Instructions to the LLM:
    *   Answer the query based on the context information and no prior knowledge.
    *   If the answer is not in the context, say "I don't know."
    *   Avoid statements like "based on the context" or "the provided information."

**Benefits of Using the Advisor:**

*   Automates the process of searching the vector store and populating the system prompt.
*   Provides a standardized way to retrieve and use context information.

**Considerations:**

*   The advisor uses a predefined logic for searching and populating context information.
*   If you require more customization, you can continue using the manual approach discussed in previous lectures.

💡 **Tip:** Use the Retrieval Augmentation Advisor if you are satisfied with the framework's default logic.

⚠️ **Warning:** If you need custom search scenarios or context population, stick with the manual approach.

📝 **Note:** The framework automatically populates the system prompt based on the context information it retrieves.

---

## 11. Understanding Embedding Calculation in Spring AI RAG Flow

We've successfully implemented a Retrieval Augmented Generation (RAG) flow within our Spring application. But who's actually calculating the embedded values for our document chunks? 🤔

We know that calculating embeddings—generating vector representations for text—is a complex process. Let's dive into how this is handled.

### Quadrant Vector Store

Let's consult the official documentation to understand how [Quadrant](https://docs.spring.io/spring-ai/reference/api/vectordbs/qdrant.html), our vector store, handles embeddings.

Quadrant is an open-source, high-performance vector search engine/database. It uses specific algorithms for efficient vector storage and retrieval.

To use Quadrant with Spring Boot, we need to add the appropriate starter dependency. We've already done this in our application.

The documentation provides an example of loading documents into the vector store:

```java
vectorStore.add(documents);
```

When you pass documents to the `add` method, Quadrant performs the embedding process behind the scenes. We'll explore this embedding process in more detail shortly.

The documentation also covers querying the vector store using similarity search and configuring Quadrant properties.

### Manual Bean Configuration (If Not Using Auto-Configuration)

If you choose not to use Spring Boot's auto-configuration, you'll need to add a specific dependency and create the vector store bean manually.

This involves creating a `QuadrantClient` bean, ensuring you provide the host name, gRPC port, TLS settings, and API key.

Then, you create the `VectorStore` bean using the `QuadrantVectorStore` builder:

```java
QuadrantVectorStore.builder()
    .quadrantClient(quadrantClient)
    .embeddingModel(embeddingModel)
    .collectionName("my_collection")
    .initializeSchema(true)
    .batchingStrategy(BatchingStrategy.TOKEN_COUNT)
    .build();
```

The builder requires a `QuadrantClient` bean and an `embeddingModel` bean. The `collectionName` specifies the collection to use, `initializeSchema` creates the schema if it doesn't exist, and `batchingStrategy` defines how documents are batched for embedding. If no `batchingStrategy` is provided, it defaults to `TOKEN_COUNT`.

### The Embedding Model

The `embeddingModel` is a crucial component. It's an interface implemented by classes like `OpenAIEmbeddingModel`.

The framework relies on these embedding models, provided by LLM providers, to perform the actual embedding.

With Spring Boot auto-configuration, these beans are created automatically.

### OpenAI Embedding Model in Detail

Let's examine the `OpenAIEmbeddingModel` class.

The `EmbeddingModel` interface is implemented by `OpenAIEmbeddingModel` (when using OpenAI). If you're using Gemini or Ollama, you'll have equivalent embedding model classes.

Inside `OpenAIEmbeddingModel`, the `call` method accepts an `EmbeddingRequest` and returns an `EmbeddingResponse`.

When loading documents into the vector store, the framework invokes this `call` method to calculate the embedded values for each document. The framework doesn't perform the calculation itself; it makes an API call to the LLM provider (like OpenAI). The received embedded values are then used for subsequent steps, such as saving them into the vector store.

### Proof Through Debugging

We can verify this by setting breakpoints in the `call` method of `OpenAIEmbeddingModel`.

During application startup, the breakpoint will be hit. Initially, the framework sends a sample input ("hello world") in the `EmbeddingRequest`.

After releasing the breakpoint, it will be hit again with the actual documents as input.

In our case, the framework sent 13 documents, demonstrating a batching strategy.

### Batching Strategy

The framework employs a batching strategy, such as `TOKEN_COUNT`, to optimize performance. It determines the maximum number of tokens allowed by the LLM embedding model and batches documents accordingly.

This prevents performance issues caused by sending too many individual documents or exceeding the token limit.

Spring AI handles these complexities, so developers don't need to worry about the underlying housekeeping.

### Embedding Response

The `EmbeddingResponse` contains the embedded values (vector numbers) for each document, along with metadata.

### Changing the Default Embedding Model

The default embedding model (e.g., OpenAI's) can be changed using a property.

OpenAI offers several embedding models with varying input limits and pricing. See more [here](https://platform.openai.com/docs/guides/embeddings).

To specify a different model, use the `spring.ai.embeddings.options.model` property in `application.properties`:

```properties
spring.ai.openai.embedding.options.model=text-embedding-3-large
```

Replace `text-embedding-3-large` with a valid model name from the LLM provider's documentation.

📝 **Note:** If using a different LLM provider (e.g., Ollama), replace `OpenAI` with the appropriate provider name.

### Conclusion

The framework calculates the embedded values of our documents by leveraging the LLM provider (e.g., OpenAI).

OpenAI offers various embedding models with different capabilities and pricing.

You can customize the embedding model using the `spring.ai.embeddings.options.model` property.

---