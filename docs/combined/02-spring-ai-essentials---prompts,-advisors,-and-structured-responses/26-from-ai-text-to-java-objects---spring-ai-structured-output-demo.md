## Using Spring AI to Request LM Model Responses in POJO Format

This section details how to use the Spring AI framework to request responses from a Language Model (LM) and map them directly into a Plain Old Java Object (POJO).

### Creating the POJO

First, we define the structure of the expected response using a Java record.

1.  Create a new package named `model`.
2.  Inside the `model` package, create a new record class named `CountryCities`.
3.  Define the fields within the `CountryCities` record:
    *   `country`: Represents the country name (String).
    *   `cities`: Represents a list of cities (List of String).

📌 **Example:**

```java
package model;

import java.util.List;

public record CountryCities(String country, List<String> cities) {}
```

### Creating the Controller

Next, we create a controller to handle the API request and interact with the LM.

1.  Create a new controller class named `StructuredOutputController`.
2.  Add necessary annotations to the class (similar to the `ChartController`).
3.  Inject a bean of `ChatClient`.

```java
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import org.springframework.ai.client.ChatClient;
import model.CountryCities;

@RestController
public class StructuredOutputController {

    private final ChatClient chatClient;

    public StructuredOutputController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    // Rest of the code will be added below
}
```

### Configuring the Chat Client

To ensure flexibility, create a new `ChatClient` bean specifically for this controller. This allows for customized configurations without affecting other parts of the application.

1.  In the `ChatClientConfig` (or a similar configuration class), copy the existing `ChatClient` bean configuration.
2.  Paste the configuration into the `StructuredOutputController`.
3.  Use the `ChatClientBuilder` to create a new `ChatClient` bean.

```java
import org.springframework.ai.client.ChatClient;
import org.springframework.ai.client.ChatClient.ChatClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatClientConfig {

    @Bean
    public ChatClient chatClient() {
        // Existing ChatClient configuration
    }
}
```

```java
    @Bean
    public ChatClient structuredOutputChatClient() {
        ChatClientBuilder chatClientBuilder = new ChatClientBuilder();
        // Configure the builder (e.g., with API key, model, etc.)
        return chatClientBuilder.build();
    }
```

### Creating the REST API

Now, define the REST API endpoint that will trigger the LM interaction and return the structured output.

1.  Create a new REST API endpoint with the path `/chat-bean`.
2.  Accept a request parameter named `message`.
3.  Set the return type of the method to `ResponseEntity<CountryCities>`.
4.  Invoke the `entity` method of the `ChatClient`, passing `CountryCities.class` as input. This tells the framework to expect the output in the form of a `CountryCities` object.
5.  Wrap the `CountryCities` object in a `ResponseEntity` with an HTTP status of `OK`.

```java
    @GetMapping("/chat-bean")
    public ResponseEntity<CountryCities> chatBean(@RequestParam("message") String message) {
        CountryCities countryCities = chatClient.entity(message, CountryCities.class);
        return ResponseEntity.ok(countryCities);
    }
```

⚠️ **Warning:** Ensure you import `org.springframework.http.ResponseEntity` from the Spring Framework, not the one from the Spring AI client library.

### Testing the API

After building and restarting the application, test the API using a tool like Postman.

1.  Send a request to the `/chat-bean` endpoint with a relevant question in the `message` parameter. For example: "Provide me the city's details in USA".
2.  Verify that the response is a JSON object conforming to the structure of the `CountryCities` record.

⚠️ **Warning:** The question should be relevant to the expected output format (country and cities). Asking unrelated questions may lead to errors or unexpected results.

### Analyzing the Logs

To understand how Spring AI formats the request for the LM, configure a logger advisor.

1.  Add default advisors to the `ChatClient` bean configuration.
2.  Pass an object of `SimpleLoggerAdvisor` to the default advisors.
3.  Invoke the `build` method to create the `ChatClient` bean.

```java
import org.springframework.ai.autoconfigure.vectorstore.SimpleVectorStoreProperties.SimpleLoggerAdvisor;
import org.springframework.ai.client.ChatClient.ChatClientBuilder;

    @Bean
    public ChatClient structuredOutputChatClient() {
        ChatClientBuilder chatClientBuilder = new ChatClientBuilder();
        // Configure the builder (e.g., with API key, model, etc.)
        chatClientBuilder.withDefaultAdvisors(new SimpleLoggerAdvisor());
        return chatClientBuilder.build();
    }
```

After rebuilding and restarting the application, send the request again and examine the logs. The logs will show the format instructions sent to the LM, including the JSON schema that defines the expected output structure.

📝 **Note:** Spring AI handles the heavy lifting of formatting the request with clear instructions, simplifying the developer's role.

💡 **Tip:** The framework automatically generates a JSON schema object with properties like `cities` (of type array) and `country` (of type string), ensuring the LM provides a well-structured response.

### Conclusion

Spring AI provides a powerful ecosystem for building intelligent Spring Boot applications by integrating with LM models. It simplifies the process of requesting structured output by automatically formatting requests and mapping responses to POJOs.
