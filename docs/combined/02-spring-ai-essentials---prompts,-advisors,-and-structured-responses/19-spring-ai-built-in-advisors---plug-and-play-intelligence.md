## Configuring Advisors in Spring AI

Let's explore how to configure advisors using the default advisor method available on the `ChatClientBuilder` object.

When you type `defaultAdvisors`, you'll find three overloaded methods:

*   Configure a single advisor.
*   Configure a list of advisors.
*   Use a Lambda expression of the `Consumer` Functional interface.

For this example, we'll use the first method to configure a single advisor: the `SimpleLoggerAdvisor`.

This advisor is included in the framework. Let's take a look at the class.

The documentation for `SimpleLoggerAdvisor` states that it logs request and response messages.

As a reminder, when implementing your own advisor, ensure you implement the `CallAdvisor` and `StreamAdvisor` interfaces.

There are two styles for receiving responses from LLMs:

1.  **Normal Style:** Wait for the complete response, which is then displayed all at once.
2.  **Streaming Style:** Stream the response as the LLM generates it, allowing for real-time updates to the user interface.

You can implement either or both interfaces. Implementing both means the advisor supports both normal and streaming calls to the LLMs.

Let's examine the contents of the `SimpleLoggerAdvisor`.

Scrolling down, you'll find the `adviceCall` method.

Inside `adviceCall`, the request is logged using the `logRequest` method. After logging, the request is forwarded to the next advisor in the chain. If no more advisors exist, the request is sent to the LLM.

Once the LLM responds, the response is logged using the `logResponse` method.

Let's look at the `logRequest` method.

The request is logged using `logger.debug`. The response is logged similarly.

Since debug logging is disabled by default, we need to enable it for this class to ensure the advisor works correctly. We'll do that shortly.

Before that, let's discuss other methods available in the advisor.

Every advisor has a name. If you don't provide one, the name is derived from the class name itself.

Each advisor also has an order. The `SimpleLoggerAdvisor` has a default order of zero when using the plain constructor.

You can set a different order by using the constructor that accepts an order number.

The higher the order number, the higher the preference the advisor gets, meaning it will be executed earlier. If multiple advisors have the same order, the execution order is not guaranteed.

⚠️ **Warning:** Always handle the order carefully if the execution order matters for your application.

We'll stick with the default order of zero for now.

Just like `adviceCall`, there's also an `adviceStream` method. This is used when invoking the LLM with the streaming option.

When using streaming, the response is streamed to the user interface as it's generated. The return type of this method is `Flux<ChatClientResponse>`, leveraging Spring's Reactive library.

In `adviceStream`, the request is logged using `logRequest`. However, response logging is handled differently. All `ChatClientResponse` objects are aggregated, and the response is logged only at the end. This is because responses are received continuously during streaming.

Hopefully, this clarifies the purpose of the `SimpleLoggerAdvisor`.

Let's examine the `CallAdvisor` interface. If we look at its implementation classes, we'll see the built-in advisors provided by the Spring framework, including those related to chat model, message chart, and prompt chart. We'll explore these in future lectures.

For now, we understand the purpose of `SimpleLoggerAdvisor`. Let's also look at `SafeguardAdvisor`.

Reading the documentation, we see that `SafeguardAdvisor` blocks calls to the model provider if the user input contains sensitive words.

When creating an instance of this advisor, you need to provide a list of sensitive words. If the user's request contains any of these words, the advisor will prevent the request from reaching the LLM.

Instead, it will generate a failure response of type `AssistantMessage`.

The default failure response content is: "I'm unable to respond to that due to sensitive content. Could we rephrase or discuss something else?"

These types of advisors are essential in enterprise environments.

As Spring AI evolves, more advisors will be introduced. Feel free to explore and use them based on your specific needs.

Let's return to the chat client configuration.

We've configured an advisor by creating an instance of `SimpleLoggerAdvisor`. To see it in action, we need to enable debug logging for the `SimpleLoggerAdvisor` class.

Let's go to `application.properties`.

We'll add the following property:

```properties
logging.level.org.springframework.ai.chat.client.advisor=debug
```

This enables debug logging for the package containing `SimpleLoggerAdvisor`.

Save the file and rebuild the project.

Once the build is complete, everything sent to and received from the LLM should be logged in the console.

Let's clean the console and invoke an API, such as the chat API.

We'll send the message "how to reset my password".

This time, if we check the logs, we'll see complete details about the request and response from the OpenAI model.

First, let's examine the request content.

We're sending a system message stating "You are an internal IT help desk assistant." This system role message was set using the `defaultSystem` and `system` methods.

Under the user message, we have our question: "how to reset my password". The message type is "user".

We also see the default model being used by Spring AI, the temperature, context, and that stream usage is false.

In future lectures, I'll show you how to change these default chat options, including the model type and temperature.

Now, let's look at the response.

The message type is "assistant", as expected.

The ID is specific to OpenAI and is included in the response.

Scrolling down, the response itself is under the "tags".

This is the same response we saw in Postman. We also have other metadata information.

As you can see, advisors are powerful. They allow us to easily implement cross-cutting concerns and housekeeping activities.

Currently, we've configured the advisor using `defaultAdvisors`. If you don't want to apply an advisor to all REST APIs using a `ChatClient` bean, you can configure it within your REST APIs.

For example, in a controller like `ChartController`, you can invoke the `advisors` method, similar to how we invoked `system` and `user` messages.

You can pass a single advisor, a list of advisors, or use a consumer Lambda expression using the third `advisors` method.

If you need to provide dynamic placeholder data, use the third `advisors` method with a consumer Lambda expression.

Let's examine the implementation of this method.

The Lambda expression takes `AdvisorSpecification` along with parameters and advisor details. If your advisor requires dynamic data, this is the method to use.

This is similar to what we saw in the prompt template demo.

In the next lecture, we'll build our own custom advisor and configure it in our Spring AI application.
