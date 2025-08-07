## Leveraging Relevancy Evaluator in Unit Testing

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
