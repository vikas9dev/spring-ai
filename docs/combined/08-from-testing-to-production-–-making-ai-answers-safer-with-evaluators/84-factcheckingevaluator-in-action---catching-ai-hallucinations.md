## Evaluating Fact Accuracy with Fact Checking Evaluator

In the previous lecture, we explored using relevancy evaluators to check if an answer is relevant to a given question. However, relevancy doesn't guarantee correctness. 

📌 **Example:** If asked "What is the capital of India?", a language model (LM) might respond with something related to India, but not the actual capital.

For critical scenarios where answer correctness is paramount, we can use the **fact checking evaluator**. Let's explore how to use it.

### Demo: Fact Checking Evaluator

We'll create a unit test to evaluate the factuality of an LM's response to a question about gravity.

1.  **Copy and Rename Test Method:**
    Duplicate an existing test method and rename it to reflect the new test.

    ```csharp
    [TestMethod]
    [DisplayName("Should return factually correct response for gravity related question")]
    [Timeout(30000)]
    public async Task EvaluateFactAccuracyForGravityQuestion()
    {
        // ... test logic here ...
    }
    ```

2.  **Define the Question:**
    Ask a question with a known answer.

    ```csharp
    string question = "Who discovered the law of universal gravitation?";
    ```

3.  **Create Evaluation Request:**
    Pass the question to the chart method of the chart controller and populate an evaluation request object with the question and the LM's response.

4.  **Instantiate Fact Checking Evaluator:**
    Create an instance of the `FactCheckingEvaluator`.

    ```csharp
    FactCheckingEvaluator factCheckingEvaluator = new FactCheckingEvaluator(_chartLineBuilder);
    ```

    📝 **Note:**  Pass the `_chartLineBuilder` object to the constructor, similar to the relevancy evaluator.

5.  **Evaluate the Response:**
    Invoke the `Evaluate` method of the `FactCheckingEvaluator`.

    ```csharp
    var response = await factCheckingEvaluator.Evaluate(evaluationRequest);
    ```

6.  **Assert Correctness:**
    Check if the response is not blank and if the answer is factually correct.

    ```csharp
    Assert.IsFalse(string.IsNullOrWhiteSpace(response.Pass), "Response was blank.");
    Assert.IsTrue(response.Pass, "The answer was not considered factually correct for this question and response.");
    ```

    ⚠️ **Warning:**  We don't need to check the relevancy score when using the fact checking evaluator.

### Debugging and Understanding the Process

To understand how the framework populates the prompt templates, set a breakpoint inside the `Evaluate` method.

1.  **Inspect Evaluation Request:**
    The `EvaluationRequest` contains the question under `UserText` and the LM's response under `ResponseContent`.

2.  **Contextual Information:**
    The framework populates contextual information by invoking a default method inside the evaluator. If no contextual information is provided, the context variable will be empty.

3.  **Framework Logic:**
    The framework leverages the entire LM response under the "claim." It asks the model to check if the claim is factually correct. The document contains the contextual information (empty in our case), and the claim contains the LM's response.

    The framework checks if the response is "yes" or "no" and populates the `Pass` boolean variable accordingly. It does not calculate a relevancy score.

### Manipulating the Answer for Testing

To verify the evaluator's behavior with incorrect answers, manipulate the LM's response during debugging.

1.  **Set Breakpoint:**
    Set a breakpoint after the LM's response is received.

2.  **Modify Response:**
    Change the response to an incorrect answer. 📌 **Example:** Replace "Sir Isaac Newton" with your name.

3.  **Continue Execution:**
    Release the breakpoint and observe the test results.

If the answer is factually incorrect, the assertion will fail, and the test will be marked as failed.

### Conclusion

You should now understand how to leverage both relevancy and fact checking evaluators in your unit tests. 💡 **Tip:** Remember that the unit tests developed so far do not involve retrieval-augmented generation (RAG) or prompt stuffing scenarios. We'll address these in the next lecture.
