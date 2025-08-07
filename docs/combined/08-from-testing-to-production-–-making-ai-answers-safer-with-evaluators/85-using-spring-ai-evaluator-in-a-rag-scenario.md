## Unit Testing Chat API with Prompt Stuffing

This section covers unit testing methods for a chat API, specifically focusing on the "prompt stuffing" API, which restricts the LLM to answer questions based solely on provided context.

The core idea is to ensure the LLM uses the provided context when answering questions. This is achieved by passing contextual information along with the user's question to the API.

### Key Concepts

*   The API endpoint is `/prompt-hyphen-stuffing`.
*   The LLM's responses are restricted to the provided context.
*   Contextual information is passed using a system method, specifically the **air policy template**.

### Evaluation Request Class

The `EvaluationRequest` class is crucial for passing data to the evaluator. It accepts three types of data:

*   **User Text:** The original question asked by the user.
*   **Response Content:** The response from the LLM.
*   **Contextual Information:**  Passed as a list of `Document` objects.

The `EvaluationRequest` class uses various constructors to accept these data types.

### Building a Unit Test Method

Let's outline the steps to create a unit test for this scenario:

1.  **Copy an Existing Test:** Start by copying an existing unit test method as a template. This saves time and provides a structure to follow.
2.  **Name and Display Name:** Give the new test method a descriptive name and display name. 📌 **Example:** `evaluateHRPolicyAnswerWithRagContext` with display name `should correctly evaluate factual response based upon HR policy context`.
3.  **Define the Question:** Create a question to test the API. 📌 **Example:** "How many failures do employees get annually?"
4.  **Invoke the API:** Call the `promptStuffing` method in the chat controller with the question.
5.  **Prepare Contextual Information:** This is a crucial step.
    *   **Inject the Air Policy Template:** Inject the HR policy prompt template into the test class.
        ```java
        @Value("classpath:air-policy-template.txt")
        private Resource airPolicyTemplate;
        ```
    *   **Extract Template Content:** Extract the content of the template as a string.
        ```java
        String policyContent = airPolicyTemplate.getContentAsString(StandardCharsets.UTF_8);
        ```
    *   **Create a Document:** Convert the template content into a `Document` object.
        ```java
        Document document = new Document(policyContent);
        ```
    *   **Create a List of Documents:** Add the `Document` to a list. This list will be passed as the contextual information.
        ```java
        List<Document> documents = List.of(document);
        ```
6.  **Create Evaluation Request:** Create an `EvaluationRequest` object, passing the user's question, the LLM's response, and the list of `Document` objects.
7.  **Fact Checking Evaluator:** Use the `FactCheckingEvaluator` to evaluate the response against the context. The framework will populate placeholders in the evaluator:
    *   `document`: Populated with the contextual information.
    *   `claim`: Populated with the LLM's response.
8.  **Assert the Result:** Check if the `sparse` variable in the evaluation response is `true`. If it is, the test passes; otherwise, the test fails.

### Code Example

Here's a simplified example of the unit test structure:

```java
@Test
@DisplayName("Should correctly evaluate factual response based upon HR policy context")
void evaluateHRPolicyAnswerWithRagContext() {
    String question = "How many failures do employees get annually?";
    String aiResponse = chatController.promptStuffing(question);

    // Prepare contextual information (as described above)
    String policyContent = airPolicyTemplate.getContentAsString(StandardCharsets.UTF_8);
    Document document = new Document(policyContent);
    List<Document> documents = List.of(document);

    EvaluationRequest evaluationRequest = new EvaluationRequest(question, aiResponse, documents);
    EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);

    assertTrue(evaluationResponse.isSparse(), "The response should be factual based on the HR policy context.");
}
```

### Testing

1.  Comment out other tests to focus on the new test.
2.  Run the test in debug mode to observe the process.
3.  Verify that the test passes.
4.  Uncomment all tests and run them to ensure no regressions.

### Alternative Evaluators

💡 **Tip:** If you want to use the `RelevancyEvaluator` instead of the `FactCheckingEvaluator`, the logic remains the same. Simply replace the `FactCheckingEvaluator` object with a `RelevancyEvaluator` object.

### Summary

This approach allows you to effectively unit test your chat API's prompt stuffing functionality, ensuring that the LLM adheres to the provided context when generating responses. 📝 **Note:** Remember to adapt the code and context to your specific use case and data.
