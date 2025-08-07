## Exploring Spring Boot Actuator Metrics for Spring AI Applications

This section details how to use Spring Boot Actuator to monitor metrics in a Spring AI application. We'll cover adding the actuator dependency, exposing metrics, and interpreting the data.

### Adding the Actuator Dependency ⚙️

The first step is to add the Spring Boot Actuator dependency to your `pom.xml` file. This dependency exposes the metrics endpoint.

1.  Open your `pom.xml` file.
2.  Add the Spring Boot Actuator starter dependency.  In IntelliJ, you can use "Add Starters" and search for "actuator".

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ```

3.  Ensure Maven changes are loaded after adding the dependency.

### Starting the Application 🚀

Before starting the application, ensure Docker Desktop is running in the background. This project uses a `compose.yaml` file to start the Quadrant vector store.

1.  Verify Docker Desktop is running.
2.  Start the Spring Boot application in debug mode. This allows you to observe the loading of RAG-related documents into the vector store.

### Accessing the Actuator Endpoint 🌐

Once the application is running, you can access the actuator endpoint to view exposed APIs.

1.  Access the actuator endpoint using the URL: `http://localhost:8080/actuator`
    ⚠️ **Warning:** Do not include a trailing slash `/` in the URL, as it will result in an error.
2.  Initially, only the `/health` endpoint is exposed by default.

### Exposing Metrics 📊

To expose metrics information, you need to add a property to the `application.properties` file.

1.  Open the `application.properties` file.
2.  Add the following property to expose the metrics endpoint:

    ```properties
    management.endpoints.web.exposure.include=health,metrics
    ```

    📝 **Note:** This property exposes both the `health` and `metrics` endpoints.
3.  Save the changes and rebuild the application.
4.  Restart the application for the changes to take effect.

### Viewing Metrics 📈

After restarting the application, refresh the actuator endpoint in your browser. You should now see the `/metrics` path.

1.  Click on the `/metrics` endpoint.
2.  You'll see various metrics, including those related to Spring and the vector database.
3.  Metrics related to the Gen AI have the prefix `gen_ai`.
4.  The `token.usage` metric provides information on total token consumption.

### Testing and Generating Metrics 🧪

To generate more metrics, test the RAG document and tool-related APIs.

1.  Test the RAG document API by sending a request like "Tell me about the notice period."
2.  Invoke the tool-related API, such as requesting the current time in London.
3.  Ensure you perform these tests to populate the actuator with more metrics.

### Exploring Specific Metrics 🔍

Let's explore some specific metrics and understand the information they provide.

#### DB Vector Store Metrics

1.  Copy the metric name for DB vector store metrics.
2.  Access the metric using the URL: `http://localhost:8080/actuator/metrics/{metric_name}`
    📌 **Example:** `http://localhost:8080/actuator/metrics/db.vector.client.operation`
3.  This provides details about the DB system, operations performed (add, query), and any errors.
4.  The metrics include the total time to complete operations and the maximum time for a single operation.

#### Filtering Metrics by Tag

You can filter metrics by tag to get more specific information.

1.  Use the `tag` query parameter to filter by tag value.
    📌 **Example:** To get information about query operations only: `http://localhost:8080/actuator/metrics/db.vector.client.operation?tag=db.operation:query`
2.  Replace `query` with `add` to see information about add operations.

#### Token Usage Metrics

The `token.usage` metric provides insights into token consumption.

1.  Access the metric using the URL: `http://localhost:8080/actuator/metrics/gen_ai.token.usage`
2.  This shows the total token consumption and metadata about the system (e.g., OpenAI).
3.  Filter by tag to see tokens related to input or output:
    *   Output tokens: `http://localhost:8080/actuator/metrics/gen_ai.token.usage?tag=gen_ai.token.type:output`
    *   Input tokens: `http://localhost:8080/actuator/metrics/gen_ai.token.usage?tag=gen_ai.token.type:input`
4.  Use the `gen_ai.operation.name` tag to filter by operation type (chat, embedding).

    📌 **Example:** To get token consumption for embedding operations: `http://localhost:8080/actuator/metrics/gen_ai.token.usage?tag=gen_ai.operation.name:embedding`

#### Active Metrics

Some metrics have an `active` suffix, indicating the current number of active operations.

1.  Access the active metric using the URL: `http://localhost:8080/actuator/metrics/{metric_name}.active`
    📌 **Example:** `http://localhost:8080/actuator/metrics/gen_ai.client.operation.active`
2.  This shows the number of active tasks currently running.
3.  Trigger an operation and immediately refresh the URL to see the active task value.

💡 **Tip:** Monitor `token.usage` in production and build alerts or dashboards using Prometheus and Grafana.

By exploring these metrics, you can gain valuable insights into the performance and resource consumption of your Spring AI application.
