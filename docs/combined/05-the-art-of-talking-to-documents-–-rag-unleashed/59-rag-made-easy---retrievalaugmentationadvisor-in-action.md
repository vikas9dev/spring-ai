## Retrieval Augmentation Advisor

In previous lectures, we built two REST APIs using the RAG pattern, manually searching the vector store by writing code. Now, we'll explore how to automate this process using the Retrieval Augmentation Advisor.

Instead of manually populating search requests and iterating through documents, the Retrieval Augmentation Advisor handles this automatically. Let's integrate this advisor into our REST APIs.

**Configuration Steps:**

1.  **Create a Retrieval Augmentation Advisor Bean:**
    *   Go to the class where you are creating the beans for `chat line` and `chart memory`.
    *   Create a bean of type `RetrievalAugmentationAdvisor`.
    *   This method requires a bean of `vector store` to know where to query for similar documents.

2.  **Build the Advisor:**
    *   Use the following code snippet to build the advisor:

    ```java
    RetrievalAugmentationAdvisor advisor = RetrievalAugmentationAdvisor.builder()
            .documentRetriever(VectorStoreDocumentRetriever.builder()
                .vectorStore(vectorStoreBean)
                .topK(3)
                .similarityThreshold(0.5f)
                .build())
            .build();
    return advisor;
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
