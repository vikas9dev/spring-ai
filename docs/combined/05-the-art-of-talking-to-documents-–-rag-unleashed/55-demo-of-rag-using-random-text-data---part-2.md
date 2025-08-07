## Performing Search Operations on the Vector Store

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
String answer = chatClient.prompt(prompt -> prompt.system(systemSpec -> systemSpec.text(promptTemplate).param("documents", similarContext)).advisors(user).user(message)).call().getContent();
```

### Application Lifecycle and Vector Store Data

⚠️ **Warning:**  The application is configured to delete the vector store container when it stops. This is controlled by a property (likely in a configuration file).

If the container is not deleted, restarting the application will add new data to the existing vector store.

⌨️ **Shortcut:** To avoid writing logic to check for duplicate data, the application is configured to always delete and recreate the container on startup. This ensures a clean slate for each run.

### Vector Store Dashboard

The vector store dashboard provides insights into the data stored.

*   The "Points" indicate the number of documents created.
*   Each document's content is converted into a multi-dimensional vector embedding.
*   The "Find Similar" option allows you to fetch similar documents.
*   The graph representation provides a visual representation of the document relationships (limited to five dimensions for display purposes).

In real applications, tuning the search operation using `topK()` and `similarityThreshold()` is crucial for relevance and performance.
