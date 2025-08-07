## Structured Output Converter

By default, Large Language Models (LLMs) and applications like ChatGPT and Google Gemini return responses in plain English. This plain English response is typically converted into a string and sent to client applications. While this works for chat and customer support bots, building business logic that leverages the intelligence of GenAI models often requires a more structured approach.

Instead of plain text, receiving responses in a structured format like JSON, XML, or as Java class objects can be more beneficial. This structured data is easier to parse, use, and integrate into business logic. The **Structured Output Converter** in the Spring AI framework enables communication with LLMs to receive responses in these structured data formats.

### Understanding the Structured Output Converter

The converter classes act as helper classes that perform two key functions when communicating with an LLM:

1.  **Formatting Instructions:** Before sending a prompt, the converter adds formatting instructions to guide the LLM on how to structure its response. This ensures the LLM replies in a parsable format. 📝 **Note:** Clear and strict instructions are crucial. 💡 **Tip:** Think of it as providing a template for the LLM to fill.
2.  **Response Conversion:** After receiving the response (e.g., in JSON or XML), the converter converts the raw text into a Java object or a collection object like a list, map, or custom class.

### Demo with Google Gemini

To demonstrate structured output, Google AI Studio is recommended. ⚠️ **Warning:** At the time of this recording, other popular applications like ChatGPT may not fully support structured output in their UI.

1.  Navigate to the Google AI Studio website.
2.  Ensure that the "Chat" option is selected.
3.  Initially, asking a question like "Provide me the list of cities in India" will result in a plain English response.

    📌 **Example:** The response might list cities like Delhi, Mumbai, and Bengaluru. However, this unstructured format is difficult to parse within Java business logic.
4.  To receive a structured response, enable the **Structured Output** option in the "Run settings" (top right corner).
5.  Click the "Edit" button to define the JSON structure.
6.  Use the visual editor to create the desired structure.

    *   Add a property named "response" with the data type "object."
    *   Inside the "response" object, add nested properties:
        *   "country" of type "string."
        *   "cities" of type "array of string."  Make sure to select the array option for the cities property.
7.  Save the structure.
8.  Re-enter the prompt and run the query.

    Initially, you might only get the country name. This highlights the importance of clear instructions.
9.  Refine the prompt to be more specific: "Provide me the list of cities present in India."
10. Run the query again.

    This time, the response should be a JSON object with a "response" key containing:

    *   "country": "India"
    *   "cities": \[ "Mumbai", "Delhi", "Bengaluru", ... ]

    ```json
    {
      "response": {
        "country": "India",
        "cities": ["Mumbai", "Delhi", "Bengaluru", "Chennai", "Hyderabad", "Pune", "Jaipur"]
      }
    }
    ```

By providing clear instructions, LLMs can effectively return responses in a structured format.

### Spring AI and Structured Output

Spring AI simplifies the process of preparing prompts for structured output. The framework handles the prompt engineering behind the scenes, allowing developers to focus on defining the desired data structure. This will be explored further in the next lecture. 🚀
