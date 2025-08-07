## RAG Implementation with Document Loading

This section details the process of implementing Retrieval Augmented Generation (RAG) using a specific HR policy document. The goal is to enable a language model (LLM) to answer questions accurately based on the document's content.

For demonstration purposes, a PDF document containing the HR policies of a fictional company called Easy Bytes was created using ChatGPT. The document covers various aspects of employment, including:

*   Table of contents
*   Introduction
*   Code of conduct
*   Employment policies (equal opportunity, probation period)
*   Background verification
*   Working hours and attendance
*   Leave policies (casual, sick, earned, maternity, paternity, unpaid)
*   Payroll and compensation
*   Performance management
*   Employee benefits (health insurance, wellness program, referral bonus)
*   Travel and expense policy
*   Workplace context and anti-harassment policy
*   IT and data security policy
*   Remote work policy
*   Grievance address
*   Exit policy
*   Acknowledgment

The document contains some intentionally "abnormal" details, such as salaries being credited on the ninth of the month and working hours from 2 PM to 11 PM. This is to ensure the LLM relies on the document's content rather than general knowledge.

The implementation involves the following steps:

1.  **Loading the Document:**
    *   A new class, `HRPolicyLoader`, is created to handle the document loading.
    *   The `@Component` annotation marks it as a Spring component.
    *   A `vectorStore` dependency is injected.
    *   A `@Value` annotation is used to specify the location of the PDF file within the resources directory.
    *   📝 **Note:** The PDF file should be downloaded from the GitHub repository and placed in the `resources` directory.
2.  **Reading the PDF:**
    *   Apache Tika is used to extract text from the PDF.
    *   Apache Tika is a powerful library that supports reading text from various file types (PPT, XLS, PDF, Word documents, etc.).
    *   A dependency for `spring-document-reader` is added to the `pom.xml` file.
    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-rest</artifactId>
    </dependency>
    ```
    *   A `TikaDocumentReader` object is created, passing the PDF file location to its constructor.
    *   The `get()` method of the `TikaDocumentReader` is invoked to retrieve the document content as a list of `Document` objects.
3.  **Storing in Vector Store:**
    *   The list of `Document` objects is added to the vector store using the `add()` method.
    *   ⚠️ **Warning:**  Initially, the document is not split into smaller chunks, which can lead to performance issues and increased token consumption.
4.  **Creating a REST API:**
    *   A new REST API endpoint (`/document/chat`) is created in the `RagController`.
    *   The API uses a system prompt template to instruct the LLM.
    *   The system prompt instructs the LLM to answer questions about Easy Bytes company policies based on the provided context.
    *   If the answer is not found in the document, the LLM should respond with "I don't know."
    *   The `HRSystemTemplate` is used instead of the original `promptTemplate`.
5.  **Token Usage Tracking:**
    *   A `TokenUsageAuditAdvisor` is created to track token usage details.
    *   The advisor is added to the `ChatClient` to log token consumption.
6.  **Testing the Implementation:**
    *   A question about working hours is sent to the API.
    *   The LLM correctly answers based on the document's content.
    *   Token usage details are examined, revealing a high number of prompt tokens due to the entire document being included in the context.
    *   A breakpoint is set in the `RagController` to verify that the entire document content is being sent to the LLM.
    *   Another question about the notice period is asked, and the LLM provides the correct answer.

The initial implementation stores the entire document as a single chunk in the vector store. This approach has drawbacks:

*   Increased token consumption, as the entire document is sent with each request.
*   Potential performance issues with large documents.

The next step is to split the document into smaller chunks to improve efficiency and reduce token usage. 💡 **Tip:** Proper chunking is crucial for effective RAG implementation.
