## Tracing I/O Operations

After stopping the current running instance of the application and ensuring no running containers in Docker Desktop, we can start the application in debug mode. 🚀

This will create four Docker containers during startup.

### Accessing the Zager UI
The Zager UI can be accessed at port 1686.

### Testing the UI with Postman
Before testing the UI, trigger some I/O operations using Postman:

1.  Trigger local time related requests.
2.  Trigger Rag document related API requests.

Once you get successful responses from both APIs, refresh the Zager UI.

### Exploring Services and Operations
Under the "Services" section, you should see a service named "Spring Eye". Select it.

In the "Operations" section, you'll find all the operations invoked within the application.

### Finding Traces
You can find traces for all requests in the last hour by selecting the "all" option and clicking "find traces". This might show a lot of traffic related to actuator Prometheus.

To focus on Rest APIs:

1.  Select the "call" option under "operation".
2.  Click "find traces".

This will show the Rest APIs that were invoked.

📌 **Example:** The first Rest API is related to local time. Clicking on it will show the entire tracing information.

### Understanding the Tracing Path
The tracing path shows how the request traveled:

1.  Rest API
2.  Chat client
3.  Chat memory
4.  Logger layer
5.  Token Usage Audit advisor
6.  LLM model

When the question reaches the LLM model, it instructs Spring I/O to invoke a tool (e.g., "Get current time"). Based on the tool's information, ChatGPT provides the response.

The entire operation's details are displayed, including:

*   Total time (e.g., 2.64 seconds)
*   Service calls
*   Depth
*   Total spans

You can click on the trace information to see more details, including the duration at each layer.

📌 **Example:** Clicking on "token usage audit" shows the start time and total execution time for that advisor. You can click on the advisor name or process for more details.

This helps debug performance bottlenecks or runtime issues.

### Tracing the Rag Scenario
For the Rag scenario, the request might travel through layers like:

1.  Chat client
2.  Chat memory
3.  Retrieval augmentation
4.  Quadrant query
5.  Embedding model (ChatGPT)
6.  Advisors (simple logger, token usage audit)
7.  OpenAI LLM model

Clicking on any of these tracing information points provides more details.

### Visualizing Tracing Information
By default, Zager displays tracing information using the "Trace Timeline" option. You can change this to a graph:

1.  Select "graph".

This displays a visual representation of how the request traveled through different layers.

📌 **Example:** The graph shows the request going from the Rest API to the Spring chat client, then to chat memory, retrieval augmentation, quadrant query, embedding LM model, and finally to the actual LLM model. Advisors intercept the request along the way.

Other visualization options include statistics, span table, and flame graph.

### Comparing Requests
Zager allows comparing two different requests:

1.  Click on the "compare" option.
2.  Provide the trace IDs of the two requests.

You can find the trace ID in the URL for each API.

📌 **Example:**

```
# URL for API A
https://zager.example.com/trace/A_TRACE_ID

# URL for API B
https://zager.example.com/trace/B_TRACE_ID
```

This is helpful for comparing requests sent to the same API, especially when one request takes significantly longer.

### Steps to Trace I/O Operations
The following steps are required to trace I/O operations:

1.  Add the following dependencies to `pom.xml`:

    ```xml
    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-exporter-otlp</artifactId>
    </dependency>
    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-sdk</artifactId>
    </dependency>
    ```

2.  Configure a bean of type `OtlpGrpcSpanExporter`, providing the Zager endpoint details.

3.  Add the following properties to `application.properties`:

    ```properties
    spring.application.name=your-application-name
    opentelemetry.exporter.otlp.endpoint=http://localhost:4317
    ```

4.  Set up Zager using Docker.

Once Zager is running, access the UI at port 1686 to view tracing information.

📝 **Note:** Spring Boot handles log generation by default. For metrics and tracing, implement the changes discussed.

💡 **Tip:** Implementing observability related changes in your real applications is strongly recommended.
