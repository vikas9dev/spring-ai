## Prompt Templates in Spring AI

In real-world enterprise applications, prompt messages can easily reach 200-300 lines due to the complexity of scenarios and the need to provide detailed context to the Language Model (LM). This necessitates a more structured approach to prompt construction.

Prompt templates in Spring AI offer a solution to build complex prompts effectively. They simplify prompt construction, promote reusability and maintainability, and ensure a clean separation between business logic and prompt text. They also support parameterized placeholders.

### Benefits of Prompt Templates
*   Simplifies prompt construction.
*   Makes prompts reusable across the application.
*   Improves maintainability.
*   Keeps business logic and prompt texts cleanly separated.
*   Supports parameterized placeholders.

### What is a Prompt Template?

A prompt template is essentially a string that accepts placeholders. It can contain both static content and dynamic placeholders.

### Best Practices for Defining Prompt Templates

While you can define prompt templates directly within your controller or business logic class, this is not recommended for complex prompts (hundreds of lines).

💡 **Tip:** Define prompt templates in a separate folder under the `resources` directory.

1.  Create a folder named `prompt-templates` under `resources`.
2.  Create files with the `.st` (string template) extension within this folder.
3.  Define your prompt template with both static content and dynamic placeholders inside the `.st` files.

### Using Prompt Templates in Your Code

1.  Use the `@Value` annotation to inject the prompt template file into a `Resource` variable.
    ```java
    @Value("classpath:prompt-templates/email-prompt.st")
    private Resource userPromptTemplate;
    ```
2.  Pass the `Resource` to the LM model using methods like `user()`, `system()`, `defaultSystem()`, or `defaultUser()`.
3.  For dynamic placeholders, use a lambda expression with the `PromptTemplateSpec` and the `text()` and `param()` methods.

    ```java
    chatClient.call(promptTemplate -> promptTemplate.user(userPromptTemplate)
            .param("customerName", customerName)
            .param("customerMessage", customerMessage));
    ```

### Demo: Email Response Generator

Let's create a REST API endpoint that helps customer support teams generate email responses based on customer complaints.

1.  Create a new controller (e.g., `PromptTemplateController`).
2.  Annotate the controller with `@RestController` and `@RequestMapping`.
3.  Inject the `ChatClient` dependency.
4.  Create a REST API endpoint (e.g., `/email`) that accepts customer name and message as request parameters.

    ```java
    @RestController
    @RequestMapping("/prompt")
    public class PromptTemplateController {

        @Autowired
        private ChatClient chatClient;

        @GetMapping("/email")
        public String emailResponse(@RequestParam String customerName, @RequestParam String customerMessage) {
            // ... implementation ...
        }
    }
    ```

5.  Define a system message to set the role of the LM (e.g., "You are a professional customer service assistant...").
6.  Create a prompt template with placeholders for customer name and message.

    📌 **Example:**  `prompt-templates/email-prompt.st`
    ```
    A customer named ${customerName} sent the following message:

    ${customerMessage}

    Write a polite and helpful email response addressing the issue.
    Maintain a professional tone and provide reassurance.
    Respond as if you are writing the email body only.
    Don't include subject and signature.
    ```

7.  Use the `@Value` annotation to inject the prompt template.
8.  Invoke the `user()` method with a lambda expression to pass the template and parameters.

    ```java
    @Value("classpath:prompt-templates/email-prompt.st")
    private Resource userPromptTemplate;

    @GetMapping("/email")
    public String emailResponse(@RequestParam String customerName, @RequestParam String customerMessage) {
        Prompt prompt = new Prompt(message -> message.user(userPromptTemplate)
                .param("customerName", customerName)
                .param("customerMessage", customerMessage));

        return chatClient.call(prompt).getResult().getOutput().getContent();
    }
    ```

9.  Test the API using Postman or a similar tool.

### Addressing Common Questions

*   **Why use prompt templates instead of string concatenation?** Prompt templates offer better maintainability and separation of concerns compared to string concatenation.
*   **Can I use different special characters for placeholders (e.g., `<` and `>`)?** Yes, but you need to configure a custom `TemplateRenderer`.

    📝 **Note:**  Refer to the Spring AI documentation for details on customizing the template renderer.

    You can find the official documentation on the [Spring AI website](https://spring.io/projects/spring-ai). Look for the `TemplateRenderer` interface under the "Prompts" section.

    📌 **Example:** Custom Template Renderer
    ```java
    // Define your own TemplateRenderer
    public class CustomTemplateRenderer implements TemplateRenderer {
        // Implement methods like startDelimiterToken() and endDelimiterToken()
    }
    ```
