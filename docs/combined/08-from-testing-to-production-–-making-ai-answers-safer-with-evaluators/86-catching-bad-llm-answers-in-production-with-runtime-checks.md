## Self-Evaluating Chat Controller with Spring AI

Large Language Models (LLMs) are inherently non-deterministic. This can lead to inconsistent responses, causing issues in production even if unit tests pass. 😨 This section explores how to integrate Spring AI's evaluator component directly into your business logic to address this.

### The Problem: Non-Deterministic LLMs

*   LLMs can produce different outputs for the same input.
*   This can lead to unexpected behavior in production.
*   Unit tests may pass, but runtime performance can be unreliable.
*   This is especially problematic for critical applications. ⚠️

### The Solution: Integrating Spring AI Evaluator

We can use Spring AI's evaluator component to validate LLM responses before sending them to the user. 🚀

Here's the general approach:

1.  Receive a request.
2.  Send the request to the LLM.
3.  Evaluate the LLM's response using the Spring AI evaluator.
4.  If the evaluation is successful, return the response to the user.
5.  If the evaluation fails, throw an exception or return an error message.

📌 **Example:** We'll create a `SelfEvaluatingChatController` to demonstrate this.

### Implementation Steps

1.  **Create a new controller:** Copy the existing chat controller and rename it to `SelfEvaluatingChatController`. Update the API paths to avoid conflicts (e.g., `/evaluate/chat`).

    ```java
    @RestController
    @RequestMapping("/evaluate")
    public class SelfEvaluatingChatController {
    }
    ```

2.  **Create a Fact Checking Evaluator:** Instantiate a `FactCheckingEvaluator` using the `ChatClient`.

    ```java
    private FactCheckingEvaluator factCheckingEvaluator;

    public SelfEvaluatingChatController(ChatClient chatClient) {
        this.factCheckingEvaluator = new FactCheckingEvaluator(chatClient);
    }
    ```

3.  **Create a Validation Method:** Create a private method `validate` that takes the question and answer as input. This method will use the `FactCheckingEvaluator` to evaluate the response.

    ```java
    private void validate(String message, String answer) {
        EvaluationRequest evaluationRequest = EvaluationRequest.builder()
                .input(message)
                .output(answer)
                .build();

        EvaluationResponse evaluationResponse = factCheckingEvaluator.evaluate(evaluationRequest);

        if (evaluationResponse.isCorrect() == false) {
            throw new InvalidAnswerException(message, answer);
        }
    }
    ```

4.  **Create a Custom Exception:** Create a custom exception class `InvalidAnswerException` that extends `RuntimeException`.

    ```java
    package com.example.ai.exception;

    public class InvalidAnswerException extends RuntimeException {

        public InvalidAnswerException(String question, String answer) {
            super("Answer check failed. The answer " + answer + " is not correct for the question " + question);
        }
    }
    ```

5.  **Integrate Validation into API:** In your REST API method, call the LLM, then call the `validate` method with the question and response. If validation fails, throw the `InvalidAnswerException`.

    ```java
    @PostMapping("/chat")
    public String chat(@RequestBody String message) {
        String response = chatClient.call(message);
        try {
            validate(message, response);
            return response;
        } catch (InvalidAnswerException e) {
            // Handle the exception, e.g., return a user-friendly error message
            return "Error: Invalid answer from the LLM.";
        }
    }
    ```

### Pros and Cons

*   ✅ **Pros:** Ensures users receive accurate responses.
*   ❌ **Cons:** Doubles token consumption because the LLM is called twice (once for the initial response and once for evaluation).

### Optimizations and Recommendations

*   💡 **Tip:** Use a different, potentially less expensive, LLM for the evaluation process. This can help reduce token costs.
*   💡 **Tip:** Consider using a local LLM (e.g., with Ollama or Docker) for evaluation to avoid unnecessary token consumption on paid services.
*   📝 **Note:** The demo uses the same OpenAI model for simplicity, but using a separate model is strongly recommended.

### Conditional Evaluation

*   You don't have to evaluate every request.
*   Implement custom logic to trigger evaluation only under certain conditions:
    *   Periodically (e.g., every half hour, test 10 requests).
    *   For critical scenarios.
    *   For premium users.

### Assignment

*   Apply the same changes to the prompt stuffing REST API.
*   Remember to provide contextual information from the air policy template to the evaluation request.

### Conclusion

Integrating Spring AI's evaluator component provides a way to ensure the reliability of LLM responses in production. By validating responses before sending them to the user, you can mitigate the risks associated with the non-deterministic nature of LLMs. Remember to optimize your implementation by using separate or local LLMs for evaluation and by implementing conditional evaluation logic. 🚀
