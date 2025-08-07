## Spring AI Advisor, Tool, and Chat Client Metrics

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
