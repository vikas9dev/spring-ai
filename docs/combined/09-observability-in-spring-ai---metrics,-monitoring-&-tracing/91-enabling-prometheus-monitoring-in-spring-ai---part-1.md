## Visualizing Metrics with Prometheus

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
