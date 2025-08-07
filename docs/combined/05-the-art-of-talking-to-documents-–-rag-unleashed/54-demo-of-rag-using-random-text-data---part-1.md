## Implementing RAG in a Spring AI Application

This section details the initial steps for implementing Retrieval Augmented Generation (RAG) in a Spring AI application, focusing on loading information into a vector store.

### Loading Data into the Vector Store

The first step in implementing RAG is to load the information into the vector store. We'll start with a simple example and then move to a more complex one that closely resembles a real-world application.

1.  **Create a `Rag` Package and `RandomDataLoader` Class:**
    Create a new package named `rag` and within it, create a class named `RandomDataLoader`.

    ```java
    package com.example.ai.rag;

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
    import org.springframework.ai.vectorstore.VectorStore;
    import org.springframework.beans.factory.annotation.Autowired;

    @Component
    public class RandomDataLoader {

        private final VectorStore vectorStore;

        @Autowired
        public RandomDataLoader(VectorStore vectorStore) {
            this.vectorStore = vectorStore;
        }
    }
    ```

    📝 **Note:** Since we've configured a `VectorStore` dependency (e.g., using Quadrant) in our Spring Boot application, Spring will automatically create a bean of type `VectorStore` (specifically, a `QuadrantVectorStore` instance) during startup.

4.  **Create a Constructor for Dependency Injection:**
    Create a constructor to inject the `VectorStore` dependency.

5.  **Create a Method to Load Sentences:**
    Create a method named `loadSentencesIntoVectorStore` to handle the data loading. Annotate it with `@PostConstruct`.

    ```java
    import jakarta.annotation.PostConstruct;

    import java.util.List;

    @Component
    public class RandomDataLoader {

        private final VectorStore vectorStore;

        @Autowired
        public RandomDataLoader(VectorStore vectorStore) {
            this.vectorStore = vectorStore;
        }

        @PostConstruct
        public void loadSentencesIntoVectorStore() {
            // Data loading logic will go here
        }
    }
    ```

    📝 **Note:** The `@PostConstruct` annotation ensures that this method is executed immediately after the bean is created by the Spring framework.

    ⚠️ **Warning:** While `@PostConstruct` is used here for demo purposes, in a real-world application, data loading should be handled by a scheduled task or a dedicated job.

6.  **Populate Sentences and Create Documents:**
    Inside the `loadSentencesIntoVectorStore` method, add the following logic:

    ```java
    import org.springframework.ai.document.Document;

    import java.util.Arrays;
    import java.util.List;
    import java.util.stream.Collectors;

    @PostConstruct
    public void loadSentencesIntoVectorStore() {
        List<String> sentences = Arrays.asList(
                "Java is a popular programming language.",
                "Python is known for its readability.",
                "JavaScript is essential for web development.",
                "Docker simplifies application deployment.",
                "A good credit score is important for financial health.",
                "Mutual funds offer diversified investment options.",
                "Bitcoin is a decentralized digital currency.",
                "Ethereum enables smart contracts.",
                "The stock market can be volatile.",
                "Compound interest can significantly increase savings.",
                "Photosynthesis is essential for plant life.",
                "The water cycle is crucial for Earth's ecosystems.",
                "The ozone layer protects us from harmful UV radiation.",
                "Earth revolves around the sun.",
                "Lightning is a powerful natural phenomenon.",
                "DNA carries genetic information.",
                "Volcanoes can cause significant destruction.",
                "Earthquakes can be devastating.",
                "The Sahara is the largest hot desert in the world.",
                "Mountains are formed by tectonic activity.",
                "Kilimanjaro is the highest mountain in Africa.",
                "Japan is known for its technology and culture.",
                "China has a large and growing economy.",
                "Canada is the second-largest country in the world.",
                "The Amazon River is the largest river by discharge volume of water in the world.",
                "Pizza is a popular Italian dish.",
                "Sushi is a traditional Japanese food.",
                "Meditation can reduce stress and improve focus.",
                "Walking 30 minutes daily is beneficial for health.",
                "Reading daily expands knowledge and vocabulary.",
                "Setting daily goals increases productivity."
        );

        List<Document> documents = sentences.stream()
                .map(sentence -> new Document(sentence))
                .collect(Collectors.toList());

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

7.  **Build the Application:**
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
    import org.springframework.ai.chat.ChatClient;
    import org.springframework.ai.vectorstore.VectorStore;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.beans.factory.annotation.Qualifier;

    @RestController
    @RequestMapping("/api/rag")
    public class RagController {

        private final ChatClient chatClient;
        private final VectorStore vectorStore;

        @Autowired
        public RagController(
                @Qualifier("chatMemoryChatClient") ChatClient chatClient,
                VectorStore vectorStore) {
            this.chatClient = chatClient;
            this.vectorStore = vectorStore;
        }
    }
    ```

    📝 **Note:** Use the `@Qualifier` annotation to specify the correct `ChatClient` bean if there are multiple implementations.

4.  **Create a Constructor for Dependency Injection:**
    Create a constructor to inject the `ChatClient` and `VectorStore` dependencies.

5.  **Create a Prompt Template File:**
    Create a new prompt template file named `system-prompt-random-data-template.st` in the `resources/prompt-template` directory.

    ```text
    You are a helpful assistant answering questions based upon the given context in the documents section and no prior knowledge.
    If the answer is not in the documents section, then reply with I don't know.

    Documents:
    <documents>
    ```

    This template defines the system message for the LLM, instructing it to answer questions based only on the provided documents.

6.  **Load the Prompt Template:**
    In the `RagController`, load the prompt template as a resource.

    ```java
    import org.springframework.core.io.Resource;
    import org.springframework.beans.factory.annotation.Value;

    @RestController
    @RequestMapping("/api/rag")
    public class RagController {

        private final ChatClient chatClient;
        private final VectorStore vectorStore;

        @Value("classpath:/prompt-template/system-prompt-random-data-template.st")
        private Resource systemPromptTemplate;

        @Autowired
        public RagController(
                @Qualifier("chatMemoryChatClient") ChatClient chatClient,
                VectorStore vectorStore) {
            this.chatClient = chatClient;
            this.vectorStore = vectorStore;
        }
    }
    ```

7