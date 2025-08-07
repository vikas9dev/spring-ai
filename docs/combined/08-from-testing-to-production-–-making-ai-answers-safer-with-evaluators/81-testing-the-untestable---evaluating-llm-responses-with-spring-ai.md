## Unit Testing in Spring AI Applications

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
