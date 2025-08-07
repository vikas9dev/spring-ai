## Creating a Stream Controller

Let's create a new controller class named `StreamController` and define a REST API within it.

First, copy the necessary annotations and constructor dependency injection from an existing controller (e.g., `ChatController`). Then, copy a simple REST API as a starting point.

Rename the API endpoint to `/stream` followed by the method name. This method will receive a request parameter named `message`.

```java
@RestController
@RequestMapping("/stream")
public class StreamController {

    // ... (Dependency Injection)

    @GetMapping("/methodName")
    public Flux<String> streamMethod(@RequestParam String message) {
        // ... (Implementation)
    }
}
```

Remove any unnecessary commented code or default system message configurations if you're satisfied with the existing setup.

When aiming for a streamed response, ensure you're invoking the `stream` method. Following this, you can use methods similar to those discussed previously, such as `contentChat`, `responseChat`, or `clientResponseChat`. 📝 **Note:** The return type for these methods should be `Flux<String>` or `Flux<Object>`.

If you were to invoke `chatResponse`, it would return a `chatResponse` object wrapped in a `Flux`. This is because the `stream` method sends responses as they are generated.

The streaming process continuously emits messages from the Language Model (LM) to our Spring application, resulting in a `Flux` of objects as output. If your return type is defined as `String`, you'll encounter a compilation error. To resolve this, wrap the `String` with `Flux`.

```java
return Flux.just("Example String");
```

`Flux` is a core concept in Spring's reactive ecosystem (Spring Reactor).

The primary benefit of using `Flux` is its non-blocking nature. The thread isn't held up waiting for the entire response. Instead, it processes data as it arrives and is then freed until the next piece of data is received.

Think of `Flux` as a conveyor belt delivering products asynchronously. As responses are received, they're processed, and the thread is released. Once the complete response is received, `Flux` emits a "complete" signal, finalizing the entire process.

If you're new to `Flux`, it might seem complex initially. A demo will illustrate how streaming and `Flux` work, providing a foundational understanding. This will enable you to explore `Flux` and Spring reactive programming in more detail when faced with similar scenarios in the future.

Perform a build of your application.

After the build is complete, invoke the REST API from a browser. ⚠️ **Warning:** Avoid using Postman, as it doesn't fully support response streaming.

📌 **Example:**

Invoking the `/stream` API with the message "Tell me about all the air policy details."

Since we're using a default system message to configure the LM model as an HR assistant, the query relates to HR policy.

Upon pressing Enter, you'll observe the response being streamed. The browser doesn't wait for the entire response to arrive before displaying it.

In contrast, invoking another REST API with the path `/chat`, which uses the `call` method instead of the `stream` method, will cause the browser (or client application) to wait for the complete response before displaying it.

You can refresh the page to observe this behavior. The browser waits, and once the full response is received, it's displayed.

With the `/stream` API, the response is displayed as it's being received.

If you have a scenario where you need to stream responses to client applications, ensure you're using the `stream` method instead of the `call` method.

📝 **Note:** When using the `stream` method, remember to wrap your return object with `Flux`. This is crucial for enabling the streaming behavior.
