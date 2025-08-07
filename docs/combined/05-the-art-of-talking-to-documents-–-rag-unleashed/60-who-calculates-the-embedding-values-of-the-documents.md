## Understanding Embedding Calculation in Spring AI RAG Flow

We've successfully implemented a Retrieval Augmented Generation (RAG) flow within our Spring application. But who's actually calculating the embedded values for our document chunks? 🤔

We know that calculating embeddings—generating vector representations for text—is a complex process. Let's dive into how this is handled.

### Quadrant Vector Store

Let's consult the official documentation to understand how Quadrant, our vector store, handles embeddings.

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

OpenAI offers several embedding models with varying input limits and pricing.

To specify a different model, use the `spring.ai.embeddings.options.model` property in `application.properties`:

```properties
spring.ai.embeddings.options.model=text-embedding-3-large
```

Replace `text-embedding-3-large` with a valid model name from the LLM provider's documentation.

📝 **Note:** If using a different LLM provider (e.g., Ollama), replace `OpenAI` with the appropriate provider name.

### Conclusion

The framework calculates the embedded values of our documents by leveraging the LLM provider (e.g., OpenAI).

OpenAI offers various embedding models with different capabilities and pricing.

You can customize the embedding model using the `spring.ai.embeddings.options.model` property.

All the code discussed is available in the GitHub repository, along with slides containing important code snippets.
