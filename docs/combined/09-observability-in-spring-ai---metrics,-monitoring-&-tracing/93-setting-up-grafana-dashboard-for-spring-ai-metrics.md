## Setting Up Grafana with Docker

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
