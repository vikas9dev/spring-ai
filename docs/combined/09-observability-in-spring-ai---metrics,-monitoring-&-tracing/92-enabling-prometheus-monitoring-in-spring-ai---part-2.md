## Monitoring Spring AI Applications with Prometheus

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
