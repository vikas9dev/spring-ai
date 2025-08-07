## Large Language Model Applications: Prompt Stuffing

Large language models (LLMs) like ChatGPT are powerful tools capable of answering a wide range of questions due to being trained on vast amounts of publicly available data. However, they have limitations when dealing with private or internal data.

Consider scenarios where a company's policy document or data stored securely within a database or cloud storage is not part of the LLM's training data. In such cases, LLMs might provide generic responses instead of accurate answers.

The reason for this limitation is that the LLM lacks sufficient knowledge about the specific subject or question being asked.

These limitations can be addressed using various techniques, including prompt stuffing and Retrieval Augmented Generation (RAG). Let's focus on prompt stuffing and how it can be used in simple scenarios.

Prompt stuffing involves providing the LLM with an "open book" before it answers a question. 📚 This means giving the LLM enough context or data to answer a user's question accurately.

By including contextual data or reference text along with the user's question, the LLM can use this extra information to provide accurate answers, even if it hasn't been pre-trained on the specific topic. This technique is also known as in-context learning or retrieval augmented prompting when done programmatically.

📌 **Example:**

Imagine you provide the following information to the LLM as part of the system role message: "According to the company's HR policy, employees are eligible for 18 days of paid leave annually. Unused leaves can be carried forward to the next year."

Without this contextual data, the LLM wouldn't be able to answer questions about the company's leave policy.

If a user asks, "How many paid leaves do employees get each year?", the LLM, instead of giving a generic response, will provide an accurate answer based on the provided context.

💡 **Tip:** Use prompt stuffing when you have a limited amount of data to provide to the LLM.

For larger datasets (e.g., 100+ pages), consider using Retrieval Augmented Generation (RAG), which we'll discuss later.

### Demo: Prompt Stuffing

Let's look at a demo of prompt stuffing using a Spring Boot application.

1.  Create a new controller by copying an existing prompt template controller.
2.  Rename the controller to `PromptStuffingController`.
3.  Update the API path to `/prompt-stuffing`.
4.  Rename the method to `promptStuffing`.
5.  Update the input parameters to accept a single request parameter named `message`.

    ```java
    @RestController
    @RequestMapping("/prompt-stuffing")
    public class PromptStuffingController {

        @GetMapping
        public String promptStuffing(@RequestParam String message) {
            // Implementation here
            return "Response from LLM";
        }
    }
    ```

6.  Create a new prompt template file named `SystemPromptTemplate.txt` in the `resources/PromptTemplates` folder.
7.  Paste the system message into this file. This message sets the scope for the LLM, instructing it to act as an internal HR assistant and providing specific HR policy details.

    📌 **Example:** `SystemPromptTemplate.txt` content:

    ```text
    You are an internal HR assistant. You assist employees with the queries related to HR policies only, such as new entitlements, working hours, benefits, and code of conduct.

    HR Policy Details:
    - 18 days of paid leave annually.
    - Up to eight unused leaves can be carried over to the next year.
    - Standard working hours are 9 AM to 5 PM.
    - Notice period is 30 days.
    - Paternity leave details: ...
    ```

    If a user asks a question outside the HR policy scope, the LLM will respond with "I can't perform that job. I'm only an HR assistant."

    📝 **Note:** You can load dynamic values (e.g., notice period, maternity leave details) from a database and populate them into the prompt template dynamically during runtime for more complex use cases.

8.  In the `PromptStuffingController`, inject the `SystemPromptTemplate` instead of the user prompt template.
9.  Pass the `SystemPromptTemplate` as an input to the system method.
10. Pass the user's message directly as the user input.

    ```java
    @RestController
    @RequestMapping("/prompt-stuffing")
    public class PromptStuffingController {

        @Value("classpath:PromptTemplates/SystemPromptTemplate.txt")
        private Resource systemPromptTemplate;

        @Autowired
        private ChatClient chatClient;

        @GetMapping
        public String promptStuffing(@RequestParam String message) throws IOException {
            String systemMessage = new String(Files.readAllBytes(Paths.get(systemPromptTemplate.getURI())));

            ChatMessage systemChatMessage = new ChatMessage(systemMessage, ChatMessage.Role.SYSTEM);
            ChatMessage userChatMessage = new ChatMessage(message, ChatMessage.Role.USER);

            ChatResponse response = chatClient.call(List.of(systemChatMessage, userChatMessage));
            return response.getResult().getOutput();
        }
    }
    ```

11. Build the application.
12. Use Postman to invoke the `/prompt-stuffing` API with a message like "I used five leave days this year. How many will be forwarded to next year?".

The LLM should provide an accurate response based on the HR policy details provided in the `SystemPromptTemplate.txt` file.

If you comment out the system-related logic and invoke the API again, the LLM will provide a generic response because it lacks the necessary contextual data.

⚠️ **Warning:** This prompt stuffing technique is suitable for limited amounts of data (e.g., 100-200 lines). For larger documents, consider using RAG to avoid potential issues. We will discuss these issues in the next lecture.
