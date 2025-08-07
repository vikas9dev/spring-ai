## Tracing AI Operations in Spring Applications

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
