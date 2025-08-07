# 08 From Testing To Production – Making Ai Answers Safer With Evaluators


Sections-
1. [Unit Testing in Spring AI Applications](#1-unit-testing-in-spring-ai-applications)
2. [Unit Testing Concepts Demo with Spring AI](#2-unit-testing-concepts-demo-with-spring-ai)
3. [Leveraging Relevancy Evaluator in Unit Testing](#3-leveraging-relevancy-evaluator-in-unit-testing)
4. [Evaluating Fact Accuracy with Fact Checking Evaluator](#4-evaluating-fact-accuracy-with-fact-checking-evaluator)
5. [Unit Testing Chat API with Prompt Stuffing](#5-unit-testing-chat-api-with-prompt-stuffing)
6. [Self-Evaluating Chat Controller with Spring AI](#6-self-evaluating-chat-controller-with-spring-ai)
7. [Leveraging Retry Logic in Spring AI](#7-leveraging-retry-logic-in-spring-ai)


---

## 1. Unit Testing in Spring AI Applications

As developers, we write code daily and should ensure its reliability through unit testing before deployment. This section focuses on unit testing Spring AI applications.

Even if you're experienced with JUnit, Mockito, or test containers, this section is relevant because unit testing LM models differs significantly from traditional web applications.

Let's explore the challenges of unit testing Spring AI applications and how the Spring AI framework helps overcome them.

### Challenges in Testing Generative AI Applications

A major challenge is the **non-deterministic** nature of LM model responses. Asking the same question multiple times may yield different answers, even if the underlying meaning is the same.

📌 **Example:**

If you ask an LM model, "What is the capital of India?" you might get:

*   "Delhi is the capital of India."
*   "India has a capital named Delhi."

While both convey the same information, traditional `assertEquals` methods would fail because the strings are different. This makes automated unit testing unreliable for LM-generated outputs.

```java
// Traditional assert equals will fail
String expected = "Delhi is the capital of India.";
String actual = "India has a capital named Delhi.";
assertEquals(expected, actual); // This will fail!
```

### Spring AI Evaluators: A Solution

The Spring AI team addresses this with **Spring AI Evaluators**. An evaluator checks if an LM response is appropriate for a given prompt, focusing on satisfactory or acceptable responses rather than exact matches.

Here's how it works:

1.  A prompt is submitted to the LM model. 💬
2.  The LM model returns a response. 🤖
3.  Both the prompt and the response are fed to the evaluator component. ⚙️
4.  The evaluator component sends both inputs to another LM model, asking if the response is correct given the prompt. 🤔
5.  The evaluator leverages the evaluation results to determine whether the unit test passes or fails. ✅/❌

As developers, we leverage these evaluators in our unit tests, and the Spring framework handles the rest.

### Unit Testing Process with Evaluators

Here's a breakdown of the unit testing process:

1.  Send a prompt to the actual LM model using the Chat Client API provided by Spring.
2.  The LM model generates a response.
3.  Feed both the prompt and the response to the evaluator component.
4.  The evaluator provides context to another LM model, explaining why the question and answer are being sent together.
5.  Based on this context, the LM model provides evaluation results (e.g., whether the answer is correct or relevant).
6.  The evaluator component uses these results to determine if the unit test passes or fails.

### The Importance of Unit Testing

Unit testing is crucial for ensuring code quality and preventing surprises in production.

💡 **Tip:** Focus on critical scenarios during unit testing to maximize coverage.

⚠️ **Warning:** Avoid writing unit tests just for the sake of it. Put in genuine effort to ensure their effectiveness.

If you neglect unit testing:

*   You might constantly ask the QA team to skip testing certain features.
*   Even the QA team might miss critical defects.
*   You risk encountering unexpected issues in production.

📝 **Note:** This course includes a dedicated section on writing proper unit testing code for generative AI applications.

### A Humorous Take on Unit Testing

A junior developer might ask: "You're telling me to write code to check if the code I wrote earlier was right?" While seemingly logical, it highlights the importance of unit testing in catching errors and ensuring code reliability.

---

## 2. Unit Testing Concepts Demo with Spring AI

This blog post details a demonstration of unit testing concepts using a basic Spring AI project.

The project structure includes:

*   A controller named `ChartController`.
*   Two APIs: `/chart` and `/prompt-stuffing`.

### `/chart` API

The `/chart` API simply passes the received prompt to the Language Model (LM) using a chat client bean. This bean is created in the constructor of the `ChartController` class. Invoking this API allows users to ask any question to the LM model.

### `/prompt-stuffing` API

The `/prompt-stuffing` API provides a system message by loading data from the `Air Policy` template located in the resources folder. This API simulates a Retrieval-Augmented Generation (RAG) scenario, providing the LM model with context to answer questions. The `Air Policy` template contains air policy-related data.

The goal is to write unit tests for both of these REST APIs.

### Application Properties

The `application.properties` file contains the following configurations:

*   Logging pattern for the console.
*   Log level set to debug for advisor-related packages.
*   OpenAI API key set using an environment variable. The value of the environment variable is assigned to the `openai.api-key` property, which is used by the Spring AI framework to interact with the OpenAI LM model.

### Dependencies (pom.xml)

The `pom.xml` file includes the following dependencies:

*   `spring-boot-starter-web`
*   `spring-ai-openai`
*   `spring-boot-devtools`
*   `spring-boot-starter-test` (the primary dependency for unit testing)

The project is available in a GitHub repository.

### Test Package

Under the standard Spring Boot project structure, the test files are located in the `src/test/java` directory, mirroring the main application's package structure (e.g., `com.easy.springai`). The default generated test file will be enhanced to include unit testing code.

### Spring AI Evaluator Component

The Spring AI framework includes an interface called `Evaluator`, which is crucial for unit testing AI scenarios.

#### Evaluator Interface

The `Evaluator` interface is a functional interface with a single abstract method: `evaluate`.

```java
EvaluationResponse evaluate(EvaluationRequest request);
```

*   `EvaluationRequest`: Contains the user-provided prompt text, the LM model's response content, and optional RAG-related data in a list. If there is no RAG or prompt stuffing scenario, the data list can be empty.
*   `EvaluationResponse`: Contains details about the evaluation, such as whether the answer passed, the score, feedback, and metadata.

The `Evaluator` interface also has a default method that converts the context data (from the data list) into a single string, separated by line separators. This utility method is useful for consolidating multiple document data into a single string.

#### Evaluator Implementations

The `Evaluator` interface defines a contract for performing unit testing of GenAI scenarios. There are a couple of implementations:

1.  **Relevancy Evaluator**: Checks if the response generated by the LM model is relevant to the question.
2.  **Fact Checking Evaluator**: Checks if the answer provided by the LM model is correct.

##### Relevancy Evaluator

The `RelevancyEvaluator` determines if the LM model's response aligns with the provided context.

*   It uses a prompt template with instructions for the LM model:
    > "Your task is to evaluate if the response for the query is in line with the context information provided. You have two options to answer either yes or no. Answer yes if the response for the query is in line with the context information, otherwise no."
*   The template includes placeholders for the query (original prompt), the LM model's response, and any context information (for RAG scenarios).
*   The framework makes a call to the LM model, providing the user question, the response, and the context information.
*   Based on the LM model's evaluation (yes or no), the `passing` variable is set to `true` (and the score to 1) if relevant, or `false` (and the score to 0) if not.
*   The `EvaluationResponse` object is then constructed with this information.

##### Fact Checking Evaluator

The `FactCheckingEvaluator` verifies the correctness of the LM model's answer. Relevancy alone is not sufficient; the answer must also be factually accurate.

📌 **Example:** If the question is "What is the capital of India?" and the LM model responds with "India is a great country with various cultures and languages," the answer is relevant to India but does not provide the correct answer.

*   It uses a prompt template with instructions for the LM model:
    > "Evaluate whether or not the following claim is supported by the provided document. Respond with yes if the claim is supported, or no if it is not."
*   The framework asks the LM model to answer "yes" if the claim is supported by the document, and "no" if it is not.
*   The `evaluate` method passes the relevant information to the LM model.
*   The `passing` Boolean variable in the `EvaluationResponse` object indicates whether the answer is correct.
*   The framework does not provide a score for fact-checking, only a pass/fail indication.

📝 **Note:** Currently, the relevancy score is either 0 or 1. Future versions of the framework may provide more granular relevancy scores (e.g., 0.5, 0.7) to indicate partial relevancy.

---

## 3. Leveraging Relevancy Evaluator in Unit Testing

Let's explore how to use the relevancy evaluator within our unit tests.

First, we'll look at the Spring AI application test Java class.

To begin, we need to add a couple of annotations to the class:

*   `@TestInstance(Lifecycle.PER_CLASS)`: 📝 **Note:** By default, JUnit creates a new instance of the test class for each test method. This annotation ensures that the same instance is used for all test methods. The default lifecycle is `Lifecycle.PER_METHOD`.
*   `@TestPropertySource`: This annotation allows us to inject properties for use during unit testing. 📌 **Example:**

    ```java
    @TestPropertySource(properties = {
            "openai.api-key=${OPENAI_API_KEY:sk-...}",
            "logging.level.org.springframework.ai=DEBUG"
    })
    ```

    This example sets the OpenAI API key using an environment variable (`OPENAI_API_KEY`) and defaults to a specific value if the environment variable is not found. It also sets the log level to DEBUG for Spring AI packages.

Next, we create a `setup` method annotated with `@BeforeEach`. This method will be executed before each unit test method.

```java
@BeforeEach
void setup() {
    // Initialization logic here
}
```

Inside the `setup` method, we'll inject some beans and initialize required objects.

First, let's inject the necessary beans:

*   `ChartController`: This class contains the REST APIs we want to test. We'll use field injection.
*   `ChartModel`: Since we're using OpenAI as a dependency, an `OpenChartModel` object will be created. We'll inject this bean as well. 📝 **Note:** The `OpenChartModel` class is a single class with the name Open Chart model.

After dependency injection, we'll create Java fields for `ChartClient` and `RelevancyEvaluator`. These fields will be initialized in the `setup` method.

Now, let's initialize the `ChartClient` and `RelevancyEvaluator` inside the `setup` method:

1.  Create a `ChartClient` using the `ChartClient.Builder`:

    ```java
    ChartClientBuilder chartClientBuilder = ChartClient.builder(chartModel)
            .withAdvisor(new SimpleLoggingAdvisor());
    chartClient = chartClientBuilder.build();
    ```

    Here, we pass the `chartModel` bean to the builder and add a `SimpleLoggingAdvisor`.
2.  Create a `RelevancyEvaluator` object:

    ```java
    relevancyEvaluator = new RelevancyEvaluator(chartClientBuilder);
    ```

    We pass the `chartClientBuilder` to the `RelevancyEvaluator` constructor.

With the setup complete, we can now define our unit testing logic.

We'll create a method annotated with `@Test` to define our unit test:

```java
@Test
@DisplayName("Should return relevant response for basic geography question")
@Timeout(30)
void evaluateChartControllerResponseRelevancy() {
    // Test logic here
}
```

*   `@Test`: Marks the method as a unit test.
*   `@DisplayName`: Provides a description of the test. 📌 **Example:** "Should return relevant response for basic geography question".
*   `@Timeout`: Sets a timeout for the test (in seconds). 💡 **Tip:** This helps test the responsiveness of the LLM. If the model takes longer than the specified time, it indicates potential performance or network issues.

Inside the test method:

1.  Define a question:

    ```java
    String question = "What is the capital of India?";
    ```
2.  Invoke the REST API in the `ChartController`:

    ```java
    String response = chartController.chart(question);
    ```

    This invokes the actual controller logic and retrieves the response.
3.  Evaluate the relevancy of the response using the `RelevancyEvaluator`. First, create an `EvaluationRequest`:

    ```java
    EvaluationRequest evaluationRequest = new EvaluationRequest(question, response);
    ```

    Then, invoke the `evaluate` method:

    ```java
    EvaluationResponse evaluationResponse = relevancyEvaluator.evaluate(evaluationRequest);
    ```

    This method returns an `EvaluationResponse` object. 📝 **Note:** The `EvaluationRequest` constructor accepts the user text and response content. There are other constructors available if you need to pass contextual information.
4.  Write unit testing checks using assertions:

    ```java
    import static org.assertj.core.api.Assertions.assertThat;
    import org.junit.jupiter.api.Assertions;

    Assertions.assertAll(
            () -> assertThat(response).isNotBlank(),
            () -> assertThat(evaluationResponse.isPass()).isTrue()
                    .withFailMessage("The answer was not considered relevant for a given question and response.")
    );
    ```

    These assertions check that the response is not blank and that the `isPass` value in the `EvaluationResponse` is true. 💡 **Tip:** These are basic unit testing concepts.

    We can also check the relevancy score:

    ```java
    double minimumRelevancyScore = 0.7; // Or inject this value via property
    Assertions.assertAll(
            () -> assertThat(evaluationResponse.getScore()).isGreaterThan(minimumRelevancyScore)
                    .withFailMessage("The given score is lower than minimum required value for this question and response.")
    );
    ```

    This assertion checks if the score is greater than a minimum required value.

Before running the test, make sure to configure the environment variables in the run configuration. Right-click and select "Modify Run Configuration" to add the necessary environment variables (e.g., `OPENAI_API_KEY`).

Finally, run the unit test. If all assertions pass, the test will be marked as successful. You can also debug the test to inspect the values of variables and step through the code.

Debugging 💡 **Tip:** Place breakpoints in the code to inspect the response from the LLM and the values in the `EvaluationResponse`.

By following these steps, you can effectively use the relevancy evaluator in your unit tests to ensure the quality and relevance of responses from your LLM-powered applications.

---

## 4. Evaluating Fact Accuracy with Fact Checking Evaluator

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

---

## 5. Unit Testing Chat API with Prompt Stuffing

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

---

## 6. Self-Evaluating Chat Controller with Spring AI

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

---

## 7. Leveraging Retry Logic in Spring AI

With the current code, an invalid answer exception is thrown if the Language Model (LM) provides an incorrect response. It would be beneficial to give the LM another chance to provide a valid answer. Spring AI offers support for retry operations to achieve this.

As a developer, you can use the `@Retryable` annotation on top of a REST API method to enable retries.

```java
@Retryable(value = InvalidAnswerException.class)
public String yourRestApiMethod() {
    // Your logic here
}
```

This annotation instructs the Spring AI framework to retry sending the prompt to the LM in case of an `InvalidAnswerException`.

*   By default, the framework retries three times.
*   If the LM still fails to provide a correct answer after three attempts, a runtime exception is thrown to the end user.

You can modify the default number of retries using the `maxAttempts` property.

```java
@Retryable(value = InvalidAnswerException.class, maxAttempts = 3)
public String yourRestApiMethod() {
    // Your logic here
}
```

📝 **Note:** The default value for `maxAttempts` is three.

⚠️ **Warning:** Avoid setting `maxAttempts` to high values (e.g., 5, 6, or 10) as it can cause the end user to wait for an extended period.

In addition to the controller class changes, you need to make the following changes:

1.  In the Spring Boot main class, add the `@EnableRetry` annotation to enable the retry functionality.

    ```java
    @SpringBootApplication
    @EnableRetry
    public class YourApplication {
        public static void main(String[] args) {
            SpringApplication.run(YourApplication.class, args);
        }
    }
    ```

    This enables the retry functionality provided by the Spring framework.

2.  Since the retry logic utilizes Spring's Aspect-Oriented Programming (AOP) concepts, ensure that you add the `spring-aspects` dependency to your `pom.xml`.

    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-aspects</artifactId>
    </dependency>
    ```

    After adding the dependency, sync the Maven changes and rebuild the application.

### Handling Exceptions After Retries

Even after multiple retries, the LM might still return an invalid answer. To display a meaningful message to the end user instead of a runtime exception, you can leverage the retry library's recovery mechanism.

Create a `recover` method in your controller that returns a string. This method should accept the same exception type for which you are performing the retry operation.

```java
@Recover
public String recover(InvalidAnswerException e) {
    return "I'm sorry, I could not answer your question. Please try rephrasing it.";
}
```

Add the `@Recover` annotation on top of this method. The framework will retry the logic for the specified number of times. If the exception persists, it will execute the logic within the `recover` method.

When defining the `recover` method, adhere to these guidelines:

*   The return type of the `recover` method must match the return type of your retry method.
*   The `recover` method must accept the same exception type for which you are performing the retry operation.

### Spring AI Evaluators

💡 **Tip:** When using Spring AI evaluators, consider the following:

Spring AI evaluator provides two types of evaluators:

*   Relevancy evaluator: Checks the relevancy of the answer.
*   Correctness evaluator: Checks the correctness of the answer.

When using the relevancy evaluator, there's still a chance the end user might receive an invalid answer, even if it's related to the question. Therefore, it's highly recommended to use the fact-checking evaluator, which validates the correctness of the answer.

📌 **Example:** Fact Checking Evaluator

The Spring AI framework team recommends using specialized models like B-scope mini check, a grounded factuality checking model developed by Scope Labs and available in Olama, for efficient and accurate fact-checking. These models are designed to fact-check responses generated by other models, helping to detect and reduce hallucinations.

For more information, refer to the research paper and blog provided by Olama.

*   [White Paper on Efficient Fact Checking](link_to_whitepaper)
*   [Olama Blog on Reducing Hallucinations](link_to_olama_blog)

The B-scope mini check model can be set up using Olama in your own environment. This offers the advantage of avoiding extra charges from vendors like OpenAI.

All evaluation checks can be forwarded to this model, which performs a decent job.

We have already discussed how to set up an LM model using Olama and establish integration between a Spring AI application and Olama-based LM models.

---