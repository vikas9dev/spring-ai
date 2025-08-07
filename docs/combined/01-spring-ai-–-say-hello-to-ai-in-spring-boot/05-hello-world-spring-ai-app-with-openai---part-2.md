## Setting Up OpenAI API Key and Testing the Spring AI Application

This section will guide you through setting up your OpenAI API key and testing your Spring AI application.

### Understanding OpenAI and ChatGPT 🤖

It's important to understand the relationship between OpenAI and ChatGPT. Think of it this way:

*   OpenAI is like **Apple**, the company.
*   ChatGPT is like **iPhone**, one of Apple's products.

OpenAI develops various AI models, and ChatGPT is a chatbot platform that utilizes these models behind the scenes. ChatGPT is an intelligent chatbot or chat platform. Behind the scenes, it uses LLM models developed by OpenAI.

### Obtaining Your OpenAI API Key 🔑

1.  Navigate to [platform.openai.com](https://platform.openai.com). This is **NOT** the ChatGPT website.
2.  Sign up or log in. If you already use ChatGPT, log in with the same account.
3.  If signing up for the first time, provide the requested details, such as your organization name.
4.  Go to **Settings** and then **Billing**.
5.  Add at least $5 to your account. This is sufficient for completing this course. You can use your credit card regardless of your location.
6.  Once the balance is added, click on **API Keys**.
7.  Click on **Create new secret key**.
8.  Give your key a name and select a project (the default project is fine).
9.  Click **Create Secret key**.
10. Copy the generated API key (starts with `SK-`) and store it securely. This is your **secret key**.

### Alternatives to Recharging 💰

If you don't want to recharge $5, you have a couple of options:

*   Set up LMS locally (covered in later lectures). 📝 **Note:** These are not as powerful as OpenAI models.
*   Watch the demos and test them later when you can recharge.
*   Share an API key with a group of friends. ⚠️ **Warning:** Ensure responsible usage.

### OpenAI Pricing 💲

*   Click on **Pricing** in the OpenAI platform to see the current rates.
*   Prices are typically per 1 million **tokens**.
*   **Tokens** are used to calculate the cost of both input messages and model responses.
*   Smaller models like `GPT 4.1 nano` are cheaper than more powerful models.
*   Spring AI allows you to select the model during chat operations.

### Configuring the API Key in Your Spring AI Application ⚙️

1.  Open your `application.properties` file.
2.  Add the following property:

    ```properties
    spring.ai.openai.api-key=${OPENAI_API_KEY}
    ```

3.  Set the `OPENAI_API_KEY` environment variable.

    *   In IntelliJ, click the three dots next to your run configuration, select "Modify options," and then "Environment Variables."
    *   Add a new variable named `OPENAI_API_KEY` and set its value to your API key.
    *   Click "Apply" and then "OK."

### Testing Your Spring AI Application with Postman 🧪

1.  Start your Spring AI application.
2.  Use Postman (or a similar tool) to send a GET request to your API endpoint.
3.  Include a `message` query parameter with your prompt. 📌 **Example:**

    ```
    http://localhost:8080/your-endpoint?message=What is your name and which model are you using?
    ```

4.  Verify that you receive a meaningful response from the AI model.

### Importing the Postman Collection 📦

To simplify testing, a Postman collection will be provided in the GitHub repository.

1.  Download the collection JSON file from the repository.
2.  In Postman, click **Import**.
3.  Select the downloaded JSON file.
4.  The collection will be imported, allowing you to easily test various API endpoints.

### Understanding Non-Deterministic Responses 🤔

LLM models are **non-deterministic**, meaning they can provide different responses for the same prompt at different times. This is due to the probabilistic nature of these models and contributes to a more human-like interaction.

### Spring AI's Abstraction 🏗️

Spring AI simplifies the process of interacting with AI models by handling complex logic and best practices behind the scenes. You can focus on your business logic without worrying about the underlying API calls.

*   The `OpenAIChatModel` class contains the `internalCall` method, which handles the API interaction.
*   The `OpenAIApiConstants` class defines the `defaultBaseUrl`, which is the OpenAI API endpoint URL.
