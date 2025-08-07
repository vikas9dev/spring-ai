## Implementing Structured Responses with List and Map Output Converters

This section explores how to obtain structured responses from LLMs, specifically focusing on list and map formats using Spring AI's structured output converters.

### Returning a List of Strings

Let's consider a scenario where we want to retrieve a list of cities for a given country.

1.  **Create a REST API endpoint:** Define a new endpoint, for example, `/chart-list`, to handle this request. Rename the method to `ChartList`.
2.  **Specify the return type:**  The desired return type is a `List<String>`, representing the list of city names.
3.  **Use `ListOutputConverter`:**  Instead of directly passing a class name to the entity method, leverage the `ListOutputConverter`. This converter instructs the LLM to return a comma-separated list of values.

    ```java
    // Example usage:
    ListOutputConverter listOutputConverter = new ListOutputConverter();
    ```

4.  **Invoke the entity method:** Pass the `ListOutputConverter` object to the entity method.
5.  **LLM Instructions:** The `ListOutputConverter` provides specific instructions to the LLM: "Respond with only a list of comma-separated values without any leading or trailing text. 📌 **Example** format: Foo, Bar, Baz."
6.  **Expected Output:** The API will return a list of city names without any additional information like the country name or Java object details.

    ```json
    [
      "New York",
      "Los Angeles",
      "Chicago"
    ]
    ```

### Returning a Map

Now, let's explore how to retrieve data in a map format.

1.  **Create a REST API endpoint:** Define a new endpoint, such as `/chart-map`. Rename the method to `ChartMap`.
2.  **Specify the return type:** The return type should be a `Map<String, Object>`, where the key is a string (e.g., city name) and the value is an object containing city details.
3.  **Use `MapOutputConverter`:**  Utilize the `MapOutputConverter` to instruct the LLM to return a map.

    ```java
    // Example usage:
    MapOutputConverter mapOutputConverter = new MapOutputConverter();
    ```

4.  **Invoke the entity method:** Pass the `MapOutputConverter` object to the entity method.
5.  **LLM Behavior:** The LLM might make default assumptions about how to structure the map. For instance, it might use the city name as the key and include details like state, population, and area as the value object.
6.  **Prompt Engineering:** 💡 **Tip:** You can influence the structure of the map by providing clearer and more specific instructions in your prompt message.
7.  **Expected Output:** The API will return a map where keys are city names and values are objects containing corresponding details.

    ```json
    {
      "New York": {
        "state": "New York",
        "population": 8419000,
        "area": 302.6
      },
      "Los Angeles": {
        "state": "California",
        "population": 3972000,
        "area": 469
      }
    }
    ```

### Using Bean Output Converter

Spring AI also provides `BeanOutputConverter` to map the LLM response to a specific Java object (POJO).

1.  **Define a POJO:** Create a Java class (e.g., `CountryCities`) representing the desired structure.
2.  **Use `BeanOutputConverter`:** Create an instance of `BeanOutputConverter`, passing the POJO class as an argument.

    ```java
    // Example usage:
    BeanOutputConverter<CountryCities> beanOutputConverter = new BeanOutputConverter<>(CountryCities.class);
    ```

3.  **Invoke the entity method:** Pass the `BeanOutputConverter` object to the entity method.
4.  **Alternative Approach:** 📝 **Note:**  You can achieve the same result by directly passing the POJO class (e.g., `CountryCities.class`) to the entity method. Spring AI handles the conversion behind the scenes.
5.  **Custom Implementation:** If you need a specific structured output converter, you can implement the `StructuredOutputConverter` interface.

### Summary

Spring AI provides flexible mechanisms for obtaining structured responses from LLMs. The `ListOutputConverter`, `MapOutputConverter`, and `BeanOutputConverter` offer convenient ways to retrieve data in list, map, and object formats, respectively. 💡 **Tip:** Effective prompt engineering is crucial for guiding the LLM to produce the desired output structure.
