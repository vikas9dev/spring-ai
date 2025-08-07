# 09 Observability In Spring Ai   Metrics, Monitoring & Tracing


Sections-
1. [Monitoring AI Operations in Spring AI Applications](#1-monitoring-ai-operations-in-spring-ai-applications)
2. [Exploring Spring Boot Actuator Metrics for Spring AI Applications](#2-exploring-spring-boot-actuator-metrics-for-spring-ai-applications)
3. [Spring AI Advisor, Tool, and Chat Client Metrics](#3-spring-ai-advisor-tool-and-chat-client-metrics)
4. [Visualizing Metrics with Prometheus](#4-visualizing-metrics-with-prometheus)
5. [Monitoring Spring AI Applications with Prometheus](#5-monitoring-spring-ai-applications-with-prometheus)
6. [Setting Up Grafana with Docker](#6-setting-up-grafana-with-docker)
7. [Tracing AI Operations in Spring Applications](#7-tracing-ai-operations-in-spring-applications)
8. [Tracing I/O Operations](#8-tracing-io-operations)


---

## 1. Monitoring AI Operations in Spring AI Applications

This section focuses on understanding how to monitor AI operations within a Spring AI-based application. We'll leverage the Spring Boot Actuator to expose metrics and gain insights into application performance, health, and potential bottlenecks. By the end of this section, you'll understand how to implement observability in a Spring AI application.

### What is Observability? 🧐

Observability allows us to understand how our application is running behind the scenes. It's crucial for debugging and resolving issues.

There are three pillars of observability:

*   Logs
*   Metrics
*   Tracing

While we're already familiar with logs (e.g., console output), this section will focus on exposing **metrics** and **tracing** information specific to Spring AI operations.

### Why is Observability Important? 🚗

Think of your application like a car. When driving, you constantly monitor:

*   Fuel level
*   Engine temperature
*   Warning lights
*   Speed and RPM

Sometimes, a mechanic uses diagnostic tools to check hidden aspects like engine codes or battery performance.

Similarly, web applications have internal systems that need monitoring using:

*   Metrics
*   Traces
*   AI-specific insights (model usage, latency, failure rates)

The Spring AI framework helps expose this data, enabling us to build monitoring dashboards and alerts.

### What We'll Cover 🚀

This section will cover the following topics:

1.  Enabling observability in a Spring application.
2.  Understanding the metrics published by the Spring AI framework.
3.  Visualizing metrics using tools like Prometheus and Grafana.
4.  Exploring request flows using tracing and spans.

This data will help you troubleshoot, optimize, and scale AI components in real applications.

### Setting Up the Project 🛠️

To demonstrate observability, we'll start with a base project.

1.  Create a new folder in your workspace (e.g., "section-9").
2.  Copy the code from "section-6" into this new folder.

    📝 **Note:** We're using the "section-6" project because it includes implementations for RAG, vector store, and tool calling, unlike "section-7" which focuses on MCP.

3.  Use this copied project as the foundation for exploring observability demos.

---

## 2. Exploring Spring Boot Actuator Metrics for Spring AI Applications

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

---

## 3. Spring AI Advisor, Tool, and Chat Client Metrics

This section explores Spring AI metrics related to advisors, tools, and chat clients. These metrics are available if you're using advisors within your Spring application.

### Advisor Metrics

To explore advisor metrics, use the `spring.ai.advisor` prefix.

📌 **Example:** Accessing advisor metrics:

1.  Navigate to the metrics endpoint.
2.  Enter `spring.ai.advisor`.

You'll see a list of advisors used in your application, such as:

*   Simple Logging Advisor
*   Call Message Chat
*   Memory Advisor
*   Token Usage
*   Audit Advisor
*   Retrieval Argument Advisor

These metrics show how many times each advisor was invoked and the total time taken.

To get information for a specific advisor, use the `tag` parameter.

📌 **Example:** Getting metrics for the Simple Logger Advisor:

```
/actuator/metrics/spring.ai.advisor?tag=name:simpleLoggerAdvisor
```

This will show the number of times the `simpleLoggerAdvisor` was invoked and the total time it took. The `operationName` will be `framework` because Spring is triggering the advisors.  The JNI system value will be `spring_ai`.

### Tool Metrics

The `spring.ai.tool` metric shows the list of tools executed within your application.

📌 **Example:** Accessing tool metrics:

1.  Navigate to the metrics endpoint.
2.  Enter `spring.ai.tool`.

If you've triggered an operation using a tool (e.g., `getCurrentTime`), you'll see it listed. The `spring.ai.kind` will be `tool_call`, and the `operationName` will be `framework`. The JNI system value will be `spring_ai`.

You can also use the `actor` suffix to see the number of active tasks related to a tool invocation.

📌 **Example:** Accessing active tasks for a tool:

```
/actuator/metrics/spring.ai.tool.active
```

In a local system with no active tasks, you'll see a value of zero. In production, this metric provides insights into current traffic.

### Chat Client Metrics

The `spring.ai.chat.client` metric shows the number of operations triggered using the chat client.

📌 **Example:** Accessing chat client metrics:

1.  Navigate to the metrics endpoint.
2.  Enter `spring.ai.chat.client`.

This metric shows the number of times you contacted the LLM using the chat client (e.g., using the `prompt` method) and the total time consumed. Dividing the total time by the count gives the average time per chat operation.

### General Notes on Spring AI Metrics

*   The metrics exposed by the actuator depend on the dependencies and logic within your Spring AI application.
*   Metrics will differ from application to application based on the operations invoked and dependencies used.
*   Expect more metrics to be added in future versions of the framework.

### Enabling Metrics in Spring Applications

To enable metrics in your Spring application, follow these steps:

1.  Add the dependency related to the actuator.
2.  Enable the metrics-related path. By default, the actuator only exposes health information.
3.  Access the list of metrics exposed by the actuator by visiting the appropriate URL.

⚠️ **Warning:** Manually visiting metrics through URLs can be cumbersome in real applications. Consider using Prometheus to explore these metrics more efficiently.

### Further Information

For more details on Spring AI metrics, refer to the official documentation. Look for the "Observability" section under "References." This page provides the latest information on metrics exposed by the framework.

---

## 4. Visualizing Metrics with Prometheus

After enabling metrics exposure in our application, the next step is to visualize them using Prometheus. Here's how to set up Prometheus to collect and display these metrics:

### Exposing Metrics in Prometheus Format
Our Spring Boot application uses the **Actuator** to expose metrics. However, Prometheus requires metrics to be in a specific format. To bridge this gap, we use **Micrometer**.

📝 **Note:** Micrometer allows us to expose metrics in various vendor formats, including Prometheus.

### Adding the Micrometer Dependency
To use Micrometer with Prometheus, we need to add the following dependency to our `pom.xml` file:

```xml
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```

After adding the dependency:
1.  Sync the Maven changes.
2.  Build the project.

### Enabling the Prometheus Endpoint
Next, enable the Prometheus endpoint in `application.properties`:

```properties
management.endpoints.web.exposure.include=health,metrics,prometheus
```

Restart the application to apply these changes. You should now see a new path under the Actuator: `/actuator/prometheus`.

Accessing this URL will display the metrics in a format that Prometheus can understand.

### Setting up Prometheus with Docker
Prometheus is an open-source metrics and monitoring system that allows us to monitor our applications and services. We can query metrics to create alerts and dashboards.

To set up Prometheus, we'll use Docker. Add a new service to your `compose.yaml` file:

```yaml
services:
  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    volumes:
      - ./prometheus-config.yaml:/etc/prometheus/prometheus.yml
    networks:
      - spring-i
    ports:
      - "9090:9090"
networks:
  spring-i:
    driver: bridge
```

📝 **Note:** This configuration assumes you have a Docker network named `spring-i`.

This configuration does the following:
*   Defines a service named `prometheus`.
*   Uses the `prom/prometheus:latest` Docker image.
*   Maps a local file `prometheus-config.yaml` to `/etc/prometheus/prometheus.yml` inside the container.
*   Attaches the Prometheus container to the `spring-i` network.
*   Exposes Prometheus on port 9090.

### Creating the Prometheus Configuration File
Create a new file named `prometheus-config.yaml` in the same directory as your `compose.yaml` file. This file will contain the configuration for Prometheus to scrape metrics from our application.

Add the following configuration to `prometheus-config.yaml`:

```yaml
global:
  scrape_interval:     5s
  evaluation_interval: 5s

scrape_configs:
  - job_name: 'spring-aa'
    metrics_path: '/actuator/prometheus'
    scrape_interval: 5s
    static_configs:
      - targets: ['host.docker.internal:8080']
```

This configuration tells Prometheus to:
*   Scrape metrics every 5 seconds.
*   Evaluate queries every 5 seconds.
*   Scrape metrics from the `/actuator/prometheus` endpoint of our Spring Boot application.

📌 **Example:** If `host.docker.internal` doesn't work, try using `localhost:8080`.

### Restarting the Application
After creating the `prometheus-config.yaml` file, rebuild the project and stop any running Docker containers. Then, restart the application using Docker Compose. This will start both the Quadrant database and the Prometheus container.

```bash
docker-compose up -d
```

After the application starts, you can access the Prometheus UI at `http://localhost:9090`.

With these steps, Prometheus is now set up to collect metrics from our Spring Boot application. In the next steps, we can explore the Prometheus UI and learn how to query and visualize these metrics.

---

## 5. Monitoring Spring AI Applications with Prometheus

You can access the Prometheus UI by navigating to `localhost:1990`. This UI provides a powerful way to monitor your application's metrics.

While Prometheus offers extensive functionality, including alert creation, we'll focus on its core capabilities for monitoring Spring AI applications. DevOps teams typically handle the setup and alerting configurations in real projects. As developers, our responsibility is to expose the metrics from our Spring AI application to Prometheus, following the steps outlined previously.

Let's explore Prometheus at a high level:

*   **Searching for Metrics:** Use the search box to find specific metrics. 📌 **Example:** Typing "token" will display metrics like `gen_a_client_token_usage_total`.
*   **Naming Conventions:** 📝 **Note:** Prometheus uses underscores (`_`) to separate words in metric names, unlike the dot (`.`) convention used in Actuator.
*   **Viewing Data:**
    *   **Tabular Format:** The search results are initially displayed in a tabular format.
    *   **Graphs:** Switch to the "Graph" tab to visualize the metric data over time. You can adjust the time range (e.g., last 5 minutes, 1 hour).

To see the token consumption in action, trigger your REST APIs:

```bash
# Example: Invoking a REST API
curl http://localhost:8080/your-api-endpoint
```

After invoking the APIs, wait a few seconds for the changes to reflect in Prometheus. The `scrape_interval` and `evaluation_interval` in the `docker-compose.yml` file determine how frequently Prometheus updates the metrics (in our case, every 5 seconds).

Click "Execute" in the Prometheus UI to refresh the graph and observe the updated token usage. You can also use the stacked option to visualize token consumption trends. Adjust the time range to analyze different periods (e.g., 10 minutes, 30 minutes).

You can toggle the night mode for better visibility. All exposed metrics can be queried directly from Prometheus. 📌 **Example:** Typing `spring_ai` will show all metrics exposed by the Spring AI framework.

Prometheus also offers an alert section for defining alert rules and receiving notifications (e.g., email) when those rules are triggered. ⚠️ **Warning:** Setting up alerts requires configuring an Alertmanager, which is an advanced DevOps concept.

While Prometheus is user-friendly for querying metrics, it has limitations in creating visually appealing dashboards. This is where Grafana comes in. We will integrate Prometheus with Grafana to build dashboards using the same metric data.

### Revisiting the Steps for Enabling Prometheus Monitoring

Before moving on, let's recap the steps to enable Prometheus monitoring in our Spring application:

1.  **Add Micrometer Registry Prometheus Dependency:** This dependency exposes metrics in a format that Prometheus can understand.

    ```xml
    <!-- Example: Maven dependency -->
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-registry-prometheus</artifactId>
    </dependency>
    ```

2.  **Enable Prometheus Endpoint:** Include the following property to enable the Prometheus path.

    ```properties
    # Example: application.properties
    management.endpoints.web.exposure.include=prometheus
    ```

3.  **Set Up Prometheus with Docker:** Use Docker to set up the Prometheus container. Ensure you define the necessary volumes in the Docker configuration. This tells Prometheus where to read the metrics from.

4.  **Prometheus Configuration (prometheus.yml):** The `prometheus.yml` file contains the configuration for Prometheus.

    ```yaml
    # Example: prometheus.yml
    scrape_configs:
      - job_name: 'spring-actuator'
        metrics_path: '/actuator/prometheus'
        scrape_interval: 5s
        static_configs:
          - targets: ['localhost:8080']
    ```

5.  **Access Prometheus UI:** After completing the setup and starting your application, access the Prometheus UI at `localhost:1990`. You can then query metrics easily through the UI.

---

## 6. Setting Up Grafana with Docker

We can set up Grafana using Docker. Here's how:

First, we'll add a new service to our `docker-compose.yml` file for Grafana, alongside the existing Prometheus service.

```yaml
version: "3.8"
services:
  prometheus:
    # Prometheus service configuration...
  grafana:
    image: grafana/grafana
    container_name: grafana
    ports:
      - "3000:3000"
    volumes:
      - grafana_storage:/var/lib/grafana
    networks:
      - spring-i2c
volumes:
  grafana_storage:
```

This configuration does the following:

*   Specifies the **image** to use: `grafana/grafana`.
*   Sets the **container name** to `grafana`.
*   Maps port **3000** on the host to port 3000 in the container, allowing us to access Grafana at `localhost:3000`.
*   Defines a **volume** named `grafana_storage` to persist Grafana data.
*   Attaches the Grafana service to the `spring-i2c` **network**.

### Persisting Grafana Data with Volumes 💾

The `volumes` configuration is crucial. It tells Grafana to store its data (including dashboards) in the `grafana_storage` volume.

⚠️ **Warning:** Without this volume configuration, restarting the Grafana container will result in the loss of all dashboards and settings.

By using volumes, Grafana retains its dashboards even after restarts. The `grafana_storage` volume is created on your local system's hard drive.

### Creating the Volume ⚙️

Just like we created networks, we also need to create a volume with the same name specified in the `docker-compose.yml` file: `grafana_storage`.

This volume is initially empty. When the container starts, any data saved inside `/var/lib/grafana` within the container will be stored in this volume.

### Networking 🌐

We attach the Grafana service to the same Docker network (`spring-i2c`) as the Prometheus service. This allows Grafana and Prometheus to communicate using service names as hostnames.

Since both are on the same network, they can use service names as hostnames for communication.

### Starting the Application ▶️

After making these changes, stop and restart the application using Docker Compose. This will create three containers: Prometheus, Grafana, and the quadrant (assuming you have a quadrant service defined elsewhere).

### Accessing Grafana 🔑

To access Grafana, navigate to `localhost:3000` in your web browser.

The default username is `admin` and the default password is `admin`. You'll be prompted to change the password after logging in. You can skip this step.

### Connecting Grafana to Prometheus 🔗

To build dashboards using Prometheus data, you need to establish a connection between Grafana and Prometheus.

1.  Navigate to **Connections** in the Grafana menu.
2.  Select **Data Sources**.
3.  Click **Add data source** and choose **Prometheus**.
4.  Give the data source a name (e.g., "Prometheus").
5.  In the **HTTP URL** field, enter `http://prometheus:9090`.

    📝 **Note:** Use `prometheus` as the hostname because Grafana and Prometheus are on the same Docker network.
6.  Click **Save & test**. A successful message confirms the integration.

### Building Dashboards 📊

1.  Go to **Dashboards** and click **New dashboard**.
2.  Click **Add visualization**.
3.  Select **Prometheus** as the data source.
4.  Enter a **metric** in the query field. 📌 **Example:** `jina_client_token_usage_total`.
5.  Select labels to filter the data. These labels correspond to the tags in the actuator metrics page. 📌 **Example:** Select the `jni_token_type` label to filter by input, output, or total tokens.
6.  Click **Run queries** to display the graph.
7.  Adjust the time range as needed. 📌 **Example:** Select "Last 15 minutes".
8.  Give the panel a name and description. 📌 **Example:** "Jina Input Token Consumption".
9.  Choose a visualization type. The default is "Time series," but you can select other options like "Bar chart" or "Gauge."
10. Click **Save dashboard**.
11. Give the dashboard a name and description. 📌 **Example:** "Spring AI".
12. Choose a folder to save the dashboard in.
13. Click **Save**.

You can add multiple panels to a dashboard, each displaying different metrics.

### Auto-Refreshing Dashboards 🔄

Grafana allows you to set up auto-refresh for dashboards. By default, you may need to manually refresh the dashboard.

Set the auto-refresh interval (e.g., every 5 seconds) to automatically update the data.

### Persisting Dashboards After Restarts ✅

Even if you restart your application, the dashboards will persist because they are stored in the `grafana_storage` volume.

You can verify this by checking the Docker Desktop app. You should see a volume named `spring_a_grafana_storage`.

As long as this volume exists, your dashboards will be preserved.

### Summary of Steps 📝

1.  Start the Grafana container by adding its configuration to your `docker-compose.yml` file.
2.  Open `localhost:3000` and log in with the default credentials (`admin/admin`).
3.  Add Prometheus as a data source, using `http://prometheus:9090` as the URL.
4.  Create dashboards and alerts based on your business requirements.

💡 **Tip:** Grafana is a powerful tool with many features. Consider exploring DevOps courses to learn more about observability concepts and building advanced dashboards.

---

## 7. Tracing AI Operations in Spring Applications

This section explores how to trace AI operations within a Spring application, allowing developers to understand request flow and identify performance bottlenecks.

When a request is sent to the Spring AI application, it traverses multiple components like advisors, tools, and vector stores. Tracing helps visualize this journey and pinpoint areas where the request spends the most time.

### Setting Up Tracing

To enable tracing, we need to add dependencies and configure the application.

#### Dependencies

Add the following dependencies to your `pom.xml`:

1.  Micrometer Tracing Bridge: This dependency tells Micrometer to expose tracing information in the OpenTelemetry (OTel) format.

    ```xml
    <dependency>
        <groupId>io.micrometer</groupId>
        <artifactId>micrometer-tracing-bridge-otel</artifactId>
    </dependency>
    ```

2.  OpenTelemetry Exporter: This dependency exports tracing information to a collector component, such as Jaeger.

    ```xml
    <dependency>
        <groupId>io.opentelemetry</groupId>
        <artifactId>opentelemetry-exporter-otlp</artifactId>
    </dependency>
    ```

    📝 **Note:** OpenTelemetry is an open-source project with APIs and tools for instrumenting, generating, collecting, and exporting telemetry data (metrics, logs, and traces). This data helps analyze software performance and behavior.

#### Configuration

1.  Create a configuration class (e.g., `OpenTelemetryExporterConfig`) under the `config` package. Annotate the class with `@Configuration`.

    ```java
    @Configuration
    public class OpenTelemetryExporterConfig {
        // Bean definition will go here
    }
    ```

2.  Define a bean of type `OtlpGrpcSpanExporter` to export tracing information to a collector component using the OpenTelemetry protocol.

    ```java
    import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;

    @Configuration
    public class OpenTelemetryExporterConfig {

        @Bean
        public OtlpGrpcSpanExporter otlpGrpcSpanExporter(@Value("${otel.exporter.otlp.endpoint}") String endpoint) {
            return OtlpGrpcSpanExporter.builder()
                    .setEndpoint(endpoint)
                    .build();
        }
    }
    ```

    This bean configuration uses the `otel.exporter.otlp.endpoint` property to specify the URL of the collector component.

3.  Add the following properties to your `application.properties` file:

    ```properties
    otel.exporter.otlp.endpoint=http://localhost:4317
    management.tracing.sampling.probability=1.0
    ```

    *   `otel.exporter.otlp.endpoint`: Specifies the URL of the tracing collector component (Jaeger in this case).  The default port for the collector is 4317.
    *   `management.tracing.sampling.probability`:  Sets the sampling probability for tracing. A value of `1.0` means 100% of traffic is traced. ⚠️ **Warning:** In production, consider using a lower value (e.g., 0.1 or 0.2) to reduce overhead.

### Setting Up a Collector Component (Jaeger)

A collector component aggregates tracing information for visualization. Jaeger is an open-source distributed tracing platform that can be used for this purpose. Zipkin is another alternative.

To set up Jaeger using Docker, add the following service to your `compose.yaml` file:

```yaml
version: "3.9"
services:
  jaeger:
    image: jaegertracing/all-in-one:latest
    ports:
      - "16686:16686" # UI port
      - "4317:4317"   # gRPC port
    environment:
      COLLECTOR_OTLP_ENABLED: "true"
    networks:
      - spring-ai
networks:
  spring-ai:
```

This configuration sets up Jaeger using the `jaegertracing/all-in-one:latest` image. It exposes two ports:

*   `4317`: Used to accept tracing information via the gRPC protocol. This is the port specified in the `otel.exporter.otlp.endpoint` property.
*   `16686`: Used to access the Jaeger UI.

The `COLLECTOR_OTLP_ENABLED` environment variable enables the OpenTelemetry protocol collector.

After making these changes, you can start the Docker Compose environment to begin testing.

---

## 8. Tracing I/O Operations

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

---