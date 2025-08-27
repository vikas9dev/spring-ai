# 06 Tool Calling In Action   Giving Llms The Power To Do Things


Sections-
- [06 Tool Calling In Action   Giving Llms The Power To Do Things](#06-tool-calling-in-action---giving-llms-the-power-to-do-things)
  - [1. Tool Calling in Generative AI](#1-tool-calling-in-generative-ai)
    - [Tool Calling vs. RAG](#tool-calling-vs-rag)
  - [2. Building a REST API to Query Current Time with LLM](#2-building-a-rest-api-to-query-current-time-with-llm)
  - [3. Implementing Tools Calling in Spring Boot](#3-implementing-tools-calling-in-spring-boot)
  - [4. Understanding How Language Models Call Tools](#4-understanding-how-language-models-call-tools)
    - [Demo and Code Walkthrough](#demo-and-code-walkthrough)
  - [5. Building an AI Assistant for Technical Issue Reporting](#5-building-an-ai-assistant-for-technical-issue-reporting)
    - [Setting Up Dependencies](#setting-up-dependencies)
    - [Creating the Entity](#creating-the-entity)
    - [Creating the Repository](#creating-the-repository)
    - [Creating the Service](#creating-the-service)
    - [Creating the Model](#creating-the-model)
  - [6. Building the Help Desk Chat Client and Tools](#6-building-the-help-desk-chat-client-and-tools)
    - [Creating a New Chat Client](#creating-a-new-chat-client)
    - [Adding a System Message](#adding-a-system-message)
    - [Building the Help Desk Tools](#building-the-help-desk-tools)
    - [Creating the REST API Controller](#creating-the-rest-api-controller)
  - [7. Exploring Tool Calling with Language Models](#7-exploring-tool-calling-with-language-models)
  - [8. Using Return Direct for Tool Execution](#8-using-return-direct-for-tool-execution)
    - [When to Use `Return Direct` 🤔](#when-to-use-return-direct-)
    - [How to Configure `Return Direct` ⚙️](#how-to-configure-return-direct-️)
    - [Behind the Scenes 🕵️‍♀️](#behind-the-scenes-️️)
    - [Demonstration 🎬](#demonstration-)
  - [9. Handling Runtime Exceptions in Tools](#9-handling-runtime-exceptions-in-tools)
  - [10. The Generative AI Journey: From LLMs to Agentic AI](#10-the-generative-ai-journey-from-llms-to-agentic-ai)


---

## 1. Tool Calling in Generative AI

Let's explore the concept of **tool calling** and how it empowers Large Language Models (LLMs) to access real-time data and perform actions.

Imagine planning a vacation with ChatGPT. You provide details like destination and dates, and ChatGPT generates a detailed itinerary:

*   Day 1: Visit specific places.
*   Lunch at a particular restaurant.
*   Dinner at another location.

ChatGPT uses its pre-trained knowledge to provide these suggestions.

However, LLMs have limitations. They possess a "cut-off knowledge," typically one or two years old. So, if you ask about real-time information like:

*   Current weather conditions 🌦️
*   Traffic updates 🚗
*   Restaurant opening status 🍽️

The LLM won't be able to provide accurate answers.

To address this, we use **tool calling**. This concept allows LLMs to access and utilize real-time data, making them significantly more powerful.

By implementing tool calling in an AI application, the LLM can:

*   Read real-time data.
*   Answer questions with up-to-date information.

📌 **Example:** Asking the LLM, "What is the weather in Hyderabad right now?" should trigger the LLM to invoke a relevant tool (exposed via Spring AI) to fetch the current weather conditions.

A tool is essentially a Java method. This method contains the logic to be executed when the LLM invokes the tool.

Beyond retrieving real-time data, tool calling enables LLMs to perform actions, such as:

*   Booking tickets 🎫
*   Sending emails 📧
*   Creating JIRA tickets 📝

These actions are defined within the Java methods associated with the tools.

![Tool Calling in Generative AI](/docs/img/tool-calling-in-generative-ai.png)

Tool calling provides LLMs with two key superpowers:

1.  **Information Retrieval:** The ability to fetch real-time data from external systems like databases or APIs. Think of it as googling the latest cricket score.
2.  **Taking Action:** The ability to perform actions within your application. Think of it as asking Siri to set an alarm.

### Tool Calling vs. RAG

It's important to distinguish tool calling from Retrieval-Augmented Generation (RAG), which we discussed previously. Both aim to improve LLM accuracy, but they function differently.

*   **Tool Calling:** Like calling a plumber to fix a leak. The LLM requests an external system (the Spring AI application) to perform an action.
*   **RAG:** Like reading a "do it yourself" manual to fix the leak yourself. The LLM retrieves information and generates a response based on that information, but it doesn't perform actions.

---

## 2. Building a REST API to Query Current Time with LLM

This section focuses on building a new REST API that asks an LLM model about the current time in a given location. The goal is to demonstrate the limitations of LLMs in answering real-time questions and then prepare for implementing tool calling to overcome this limitation.

First, we create a new controller named `TimeController`.

1.  Create a new class `TimeController` inside the `controller` package.
2.  Create a dedicated chat client configuration for this controller. Copy the existing `ChatMemoryChatClientConfig` and rename it to `TimeChatClientConfig`.
3.  Remove unnecessary configurations (chat memory, RAG-related configurations, advisor) from `TimeChatClientConfig`, focusing solely on tool calling.
4.  Rename the chat client bean to `timeChatClient`.
5.  Inject necessary advisors like `loggerAdvisor`, `tokenUserAdvisor`, and `memoryAdvisor`.

    ```java
    @Configuration
    public class TimeChatClientConfig {

        @Bean("timeChatClient")
        public ChatClient timeChatClient(ChatClient.Builder chatClientBuilder, ChatMemory chatMemory) {
            Advisor loggerAdvisor = new SimpleLoggerAdvisor();
            Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
            Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();
            return chatClientBuilder.defaultAdvisors(List.of(loggerAdvisor, memoryAdvisor, tokenUsageAdvisor)).build();
        }
    }
    ```
Next, configure the `TimeController`.

1.  Add necessary annotations to the `TimeController`: `@RestController` and `@RequestMapping("/api/tools")`.
2.  Perform dependency injection using the constructor. Inject the `timeChatClient`.

    ```java
    @RestController
    @RequestMapping("/api/tools")
    @RequiredArgsConstructor
    public class TimeController {
        private final ChatClient timeChatClient;
    }
    ```

Now, create a simple REST API endpoint.

1.  Add a new REST API endpoint `/local-time` that accepts a user message and forwards it to the LLM. The LLM's response is then returned as output.

    ```java
    @GetMapping("/local-time")
    public ResponseEntity<String> localTime(
        @RequestParam String message, 
        @RequestHeader String username) {
        return ResponseEntity.ok(
            timeChatClient.prompt()
            .advisors(a -> a.param(CONVERSATION_ID, username))
            .user(message).call().content()
        );
    }
    ```
2.  Initially, no system prompt is configured, allowing any question to be asked. However, the intention is to restrict questions to the current time.

Build and run the application.

1.  Save the changes and build the application.
2.  ⚠️ **Warning:** Ensure Docker Desktop is running in the background. This is required due to RAG-related configurations from previous sections, which set up a new Quadrant container on application startup. If you don't need these configurations, you can remove them.
3.  Start the Spring Boot application.

Test the API endpoint using Postman:- `curl --location 'http://localhost:8080/api/tools/local-time?message=What%20is%20the%20current%20time%20in%20London%20%3F' \
--header 'username: testUser19'`

1.  Invoke the `/api/tools/local-time` endpoint with a message like "What is the current time in Hyderabad, India?".
2.  Observe the response from the LLM. It should indicate that the LLM lacks real-time capabilities and cannot provide the current time.

Compare the results with ChatGPT.

1.  Ask the same question ("What is the current time in Hyderabad, India?") in ChatGPT.
2.  Notice that ChatGPT provides the correct current time.

Clarification:

*   In our Spring AI application, we are directly interacting with the core LLM model.
*   ChatGPT acts as an LLM wrapper application with additional capabilities, such as web searching, to access real-time information.
*   We cannot directly send requests to ChatGPT from our Spring application because it's a proprietary wrapper application owned by OpenAI.
*   Developers can only interact with the core LLM model.

Conclusion:

The LLM model, in its core form, cannot access real-time data. This limitation will be addressed by implementing tool calling in the subsequent steps. 💡 **Tip:** Tool calling will allow the LLM to interact with external tools to retrieve real-time information.

---

## 3. Implementing Tools Calling in Spring Boot

If you are a Java and Spring Boot developer, implementing tools calling will be straightforward. This section will guide you through the process.

Let's start by creating a new package named `tools`. Inside this package, create a new Java class called `TimeTools`. The class name can be anything.

Annotate the `TimeTools` class with `@Component` to create a Spring bean. This allows the bean to be injected into the Spring framework.

Add a logger statement for debugging purposes.

Next, define a Java method that provides the current time based on the code's execution location. For example, if the code is running from Hyderabad, it should return the local current time. The method should return a string.

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalTime;

@Component
public class TimeTools {

    private static final Logger log = LoggerFactory.getLogger(TimeTools.class); 
    // or use lombok @Slf4j 

    // Method implementation will be added below
}
```

Let's name the method `getCurrentLocalTime`. It won't accept any input. Inside the method, add a log statement indicating that the current time in the user's time zone is being returned.

Now, write the logic to return the current local time. Use the `LocalTime` class in Java to get the local time.

```java
@Tool(
    name = "getCurrentLocalTime",
    description = "Returns the current time in the user's time zone"
)
public String getCurrentLocalTime() {
    log.info("Returning the current time in the user's time zone");
    return LocalDateTime.now().toString();
}
```

Annotate the method with `@Tool`. This is a special annotation from the Spring AI team. Use this annotation when you want to expose the logic of a Java method as a tool.

Simply mentioning the annotation is not enough. Provide as much detail as possible using this annotation so the LM model has enough context on when to invoke this tool. The LM model should not invoke this method if a question related to Paris is asked.

The first piece of information to provide is the `name` of the tool. This name can be anything, but make sure it's relevant to the functionality exposed by the tool. Here, the method name itself is used as the tool name. If you have multiple tools, ensure each tool has a unique name.

After the `name`, provide a `description` of the tool. This description is very important because the LM model uses it to understand when to invoke the tool.

With this, we have successfully created a tool.

Now, configure this tool into your chat client bean. Go to the `timeChatClient` class.

Similar to how advisors are injected, there's a default method to inject tools. The method name is `defaultTools`. Provide the bean where you defined all your tools to this `defaultTools` method.

First, inject the bean into this method. You know you just have to type `timeTools`. This `timeTools` bean needs to be provided as an input to these `defaultTools`.

```java
@Bean("timeChatClient")
public ChatClient timeChatClient(ChatClient.Builder chatClientBuilder,
        ChatMemory chatMemory,
        TimeTools timeTools) { // provide the bean
    Advisor loggerAdvisor = new SimpleLoggerAdvisor();
    Advisor memoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();
    Advisor tokenUsageAdvisor = new TokenUsageAuditAdvisor();

    return chatClientBuilder
            .defaultTools(timeTools) // inject the bean
            .defaultAdvisors(List.of(
                    loggerAdvisor,
                    memoryAdvisor,
                    tokenUsageAdvisor
            ))
            .build();
}
```

In case you want to provide tools specific to your REST API, you also have the option of providing the tools directly when prompting the LM. Just like how you have advisors, there's a method called `callTools`. You can provide the bean of the class where you defined the tools to this method. This method will be explored in future sections.

Save the changes and build the project.

Once the build is completed, restart the application because the dependency of dev tools has been removed.

Invoke the same REST API, but ask the question: "What is the current time in my location?". Don't give any city name. `curl --location 'http://localhost:8080/api/tools/local-time?message=What%20is%20the%20current%20time%20in%20my%20location%3F' \
--header 'username: testUser19'`

Another tool will be built in a few minutes that is capable of providing the time of any time zone. For now, the developed tool can only provide the time from the location where the code is being executed.

The LM should respond with the current time in your location.

The LM responded saying that the current time in your location is 13:17:33, which means 1:17 p.m. Inside the log statements, you should also see a statement with the message saying that returning the time in the user's time zone. This logger was mentioned inside the method defined in the `TimeTools` class.

This confirms that the LM models are capable of giving instructions to the Spring AI application to invoke a specific method and provide the response.

How this works internally will be discussed in detail later.

It is assumed that you are clear on how tool calling provides real-time information to the LM models.

Let's create one more tool that is capable of providing the time of any location. This is very similar to the top method that was discussed.

```java
@Tool(
    name = "getCurrentTime",
    description = "Returns the current time in the specified time zone"
)
public String getCurrentTime(
        @ToolParam(description = "Value representing the time zone")
        String timeZone
) {
    log.info("Returning the current time in timezone: {}", timeZone);
    return LocalDateTime.now(ZoneId.of(timeZone)).toString();
}
```

For the second method, the name `getCurrentTime` has been given, and the same name has been provided for the tool as well under the tool description. This time, the description is: "Get the current time in the specified time zone."

The @ToolParam annotation is used to provide the description of the parameter. In this case, the description is: "Value representing the time zone.". LLMs will use this description to understand the purpose of the parameter, and provide the correct value for the parameter.

Save the changes and build the project. Call the API with questions "What is the current time in Osaka?":- `curl --location 'http://localhost:8080/api/tools/local-time?message=What%20is%20the%20current%20time%20in%20Osaka%3F' \
--header 'username: test19'`

Check the log message, it should say: "Returning the current time in timezone: Asia/Tokyo".

---

## 4. Understanding How Language Models Call Tools

Let's explore how Language Models (LMs) interact with tools. It might seem like Spring is directly exposing our tool logic as an endpoint for OpenAI, but that's not the case. Instead, a conversation takes place between our Spring application and the LM.

![How Language Models Call Tools](/docs/img/How_Language_Models_Call_Tools.png)

Here's a breakdown of the flow when tools are involved:

1.  **User Prompt:** 🧑‍💻 The end user sends a prompt message. The message type is "user." If there's a system message, it's sent along as well.
2.  **Tool Details to LM:** ⚙️ The Spring AI framework sends tool details and definitions to the LM, providing context. This helps the LM understand when to request tool invocation.
3.  **LM Decision:** 🤔 The LM analyzes the prompt and determines if a tool is needed. If not, it provides a direct answer. If a tool is required, it proceeds to the next step.
4.  **Assistant Message with Tool Instructions:** 🤖 The LM sends a chat message to the Spring AI application (client) using the "assistant" message type. This message contains clear instructions on which tool to execute.
    *   The `finish_reason` is set to `tool_calls`. This indicates that the LM is waiting for a response from the tool execution.
    *   ⚠️ **Warning:** Usually, `finish_reason` is "stop," meaning the LM is done. "tool_calls" signifies an ongoing conversation.
5.  **Tool Execution:** 🛠️ The Spring AI application takes the instructions and executes the required tool logic.
6.  **Tool Message with Outcome:** 📤 The outcome of the tool execution is populated in a "tool" message.
    *   The message role is "tool."
    *   The client application sends the question details along with the tool's response.
7.  **LM Response Preparation:** 🧠 Based on the tool's response, the LM prepares the final response.
8.  **Final Assistant Message:** 💬 The LM sends the response back to Spring AI as an "assistant" message. The `finish_reason` is "stop."
9.  **Response to User:** 🚀 The Spring AI application forwards the response to the end user.

This entire process happens behind the scenes.

Here's another way to visualize the tool execution:

*   **AI Model:** The Language Model itself.
*   **Spring AI Framework:** Our Spring AI code.
*   **Tool Calling Manager:** An interface within the Spring AI framework responsible for invoking tools based on LM instructions.

![Tool Execution Flow](/docs/img/Tool_Execution_Flow.png)

The flow is as follows:

1.  The chat request, including the prompt and tool definitions, is sent to the LM. The framework extracts tool details (definitions, names) from the classes where the tool methods are defined.
2.  The LM decides whether to invoke a tool.
3.  If a tool is needed, the LM forwards instructions to Spring AI.
4.  The Tool Calling Manager implementation intercepts these instructions.
5.  The Tool Calling Manager invokes the actual tool and receives the response.
6.  The response is sent back to the AI model.
7.  The AI model prepares the final response and sends it to the end user.

### Demo and Code Walkthrough

Let's look at a demo to clarify the process.

First, we'll invoke a REST API *without* tool calling.

📌 **Example:** Asking "Tell me about notice period" returns "The minimum notice period for resignation is 36 days." `curl --location 'http://localhost:8080/api/rag/document/chat?message=What%20are%20the%20working%20hours%3F' \
--header 'username: testUser12'`

In the console logs, the response from the LM has `finishReason: STOP` inside the `result` -> `metadata`. This is because the LM could answer the question directly using the provided document information, without needing any tools.

Now, let's examine the tool calling scenario.

We'll use the `DefaultToolCallingManager` class, which implements the `ToolCallingManager` interface. This class manages tool execution by default. You can customize this behavior by creating your own implementation.

Inside the `executeToolCalls` method, we'll set a breakpoint to observe the process.

📌 **Example:** Asking "What is the local time in London?"

The breakpoint hits inside `DefaultToolCallingManager`. The `chatResponse` object contains:

*   A `generations` object.
*   An "assistant" message with tool call information. This includes instructions to invoke the `getCurrentTime` function with the timezone "Europe/London".
*   The `type` is "function" because we're using a Java method.
*   The `finish_reason` is `tool_calls`.

Debugging the method reveals:

1.  The code identifies the tool the LM wants to execute.
2.  It retrieves the assistant message and extracts the tool context.
3.  It invokes the `executeToolCall` method.

Inside `executeToolCall`:

1.  The code fetches tool callback details. The Spring framework maintains definitions for tools like `getCurrentTime` and `getCurrentLocalTime`.
2.  It iterates through the tool calls received from the assistant message. In this case, there's only one.
3.  The tool name is `getCurrentTime`, and the input argument is the timezone.
4.  The framework executes the Java method with these details.
5.  The method returns a `ToolResponse` message.

The `ToolResponse` contains:

*   A message type of "tool".
*   The response data: the current time in London.

Using this response data, the LM prepares a more detailed answer.

Back in Postman, the final response is: "The current time in London is 10:41 a.m."

📝 **Note:** The conversation between the LM and the client application regarding tool execution is *not* logged or saved in the conversation history. This is because these are internal conversations, and logging them could lead to performance issues, especially with multiple tool executions and dependencies.

💡 **Tip:** If you have questions, debug the `DefaultToolCallingManager` class to understand the flow in detail.

---

## 5. Building an AI Assistant for Technical Issue Reporting

The tools we've developed can not only fetch real-time information for LLMs but also perform actions based on LLM instructions. This includes updating databases and invoking third-party REST APIs. Tools are a crucial stepping stone for advanced concepts like model context to protocol and AI agents, which we'll explore later.

⚠️ **Warning:** Ensure you have a solid understanding of the tools concept before moving on to these advanced topics.

We'll build an AI assistant to help customers or employees report technical issues. This assistant will:

*   Offer to create a help desk ticket. 🎫
*   Invoke a tool to create the ticket by inserting a record into the database. 💾
*   Help users check the status of their tickets. ✅
*   Use a tool to fetch ticket information from the database. 🔍
*   Provide the ticket status to the user via the LLM. 🗣️

This might seem complex, but with a good grasp of Spring Boot and the tools concept, it's manageable. Let's break it down step by step.

### Setting Up Dependencies

First, we need to add dependencies to the `pom.xml` file.

```xml
<!-- Spring Data JPA -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

After adding these, sync the Maven changes. 🔄

### Creating the Entity

Next, create an `entity` package and a `HelpDeskTicket` class, which will be a POJO representation of the database table.

📌 **Example:**

```java
@Entity
@Table(name = "helpdesk_tickets")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HelpDeskTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String issue;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime eta;
}
```


Annotations used:

*   `@Entity`: Marks the class as a JPA entity.
*   `@Table(name = "helpdesk_tickets")`: Specifies the database table name.
*   `@Getter`, `@Setter`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`: Lombok annotations for generating boilerplate code.

The class contains fields like:

*   `id`: Primary key.
*   `username`: User who reported the issue.
*   `issue`: Description of the issue.
*   `status`: Current status (open, in progress, closed).
*   `createdAt`: Date and time the issue was created.
*   `eta`: Estimated time of arrival for resolution.

To have Spring Data JPA automatically create the table, add the following property to `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=update
```

`update` will update the schema if necessary, creating the table if it doesn't exist. Using `create` would recreate the table on every restart, deleting existing data.

### Creating the Repository

Create a `repository` package and a `HelpDeskTicketRepository` interface.

📌 **Example:**

```java
public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {
    List<HelpDeskTicket> findByUsername(String username);
}
```

This interface extends `JpaRepository` to provide CRUD operations. We also define a derived query method, `findByUsername`, which will query the `helpdesk_tickets` table based on the username.

### Creating the Service

Create a `service` package and a `HelpDeskTicketService` class.

📌 **Example:**

```java
package com.example.service;

import com.example.entity.HelpDeskTicket;
import com.example.model.TicketRequest;
import com.example.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    public HelpDeskTicket createTicket(TicketRequest ticketRequest, String username) {
        HelpDeskTicket ticket = HelpDeskTicket.builder()
                .issue(ticketRequest.getIssue())
                .username(username)
                .status("open")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return helpDeskTicketRepository.save(ticket);
    }

    public List<HelpDeskTicket> getTicketsByUsername(String username) {
        return helpDeskTicketRepository.findByUsername(username);
    }
}
```

Annotations used:

*   `@Service`: Marks the class as a service component.
*   `@RequiredArgsConstructor`: Lombok annotation to generate a constructor for final fields.

This class will contain the logic for database interactions. We inject a `HelpDeskTicketRepository` bean.

We define two methods:

1.  `createTicket`: Takes a `TicketRequest` object (containing the issue details) and the username as input. It creates a new `HelpDeskTicket` entity, sets the default status to "open", sets the creation date, and sets the ETA to seven days from the current date. Finally, it saves the ticket to the database.
2.  `getTicketsByUsername`: Takes a username as input and fetches all help desk tickets for that user using the `findByUsername` method from the repository.

### Creating the Model

Inside the `model` package, create a `TicketRequest` record.

📌 **Example:**

```java
package com.example.model;

public record TicketRequest(String issue) {
}
```

This record is used to accept the issue details from the LLM.

📝 **Note:** The focus here is on Spring AI concepts. The Spring Boot logic is kept simple, assuming you have a basic understanding of Spring Boot.

In the `createTicket` method:

*   The issue is obtained from the `TicketRequest` object.
*   The username will be obtained using advanced tool-calling concepts (explained in the next lecture).
*   The status is set to "open" by default.
*   The `createdAt` timestamp is set to the current date and time.
*   The ETA is set to seven days from the current date.

💡 **Tip:** In real-world applications, the ETA calculation would be more complex, considering factors like severity and criticality.

The `getTicketsByUsername` method uses the `findByUsername` method from the repository to retrieve a list of tickets.

We've now completed the database table setup. In the next lecture, we'll build the necessary tools for the LLM to interact with this setup during conversations with users.

---

## 6. Building the Help Desk Chat Client and Tools

This section details the process of creating a dedicated chat client and associated tools for a help desk scenario. The goal is to provide a tailored experience with specific system prompts and database interaction capabilities.

### Creating a New Chat Client

1.  Copy the existing time chart client configuration.
2.  Paste the configuration into the `config` package.
3.  Rename the class to `HelpDeskChartClientConfig`.
4.  Name the bin `helpDeskChartClient`.
    📝 **Note:** A separate chat client bin is created for the help desk to provide a specific system prompt message tailored to this scenario.
5.  It has the same configuration as the timeChatClient => Injected the existing time tools under the default tools.
6.  We will inject new tools for database interactions directly using the `tools` method.

### Adding a System Message

1.  Create a new prompt template file named `HelpdeskSystemPromptTemplate` under the `resources/prompt- templates` folder.
2.  Add the following system message to the template file (`helpDeskSystemPromptTemplate.st`):

    ```text
    You are a virtual help desk assistant for Easy Bytes, responsible for assisting employees and customers with their issues.
    Your primary goal is to provide clear, accurate, and helpful solutions to common problems.
    If a user's issue cannot be resolved through your response, offer to create a new Help Desk ticket on their behalf.
    Check the status of the existing helpdesk tickets or guide them to the appropriate support channel if needed.
    Always aim to be polite, proactive, and solution oriented if there is already a ticket is present regarding the same issue.
    Please don't create duplicate ones when responding about the status of the existing issues.
    Please keep the response short with the status of the ticket and ETA.
    ```

    📝 **Note:** This system message instructs the language model (LM) to offer ticket creation, fetch ticket statuses, and follow specific guidelines for interacting with users.
3.  Inject the system prompt into the `HelpDeskChartClientConfig`.
    *   Read the message from the template file into the Java class using `@Value` annotation and `@Resource`.
    *   Pass the `systemPromptTemplate` variable as an input to the `defaultSystem` method.

```java
@Value("classpath:/prompt-templates/helpDeskSystemPromptTemplate.st")
Resource helpDeskSystemPromptTemplate;

@Bean("helpDeskChartClient")
public ChatClient helpDeskChartClient(ChatClient.Builder chatClientBuilder,
            ChatMemory chatMemory,
            TimeTools timeTools) {
    // ...
    return chatClientBuilder
            .defaultSystem(helpDeskSystemPromptTemplate)
            // ...
}
```

### Building the Help Desk Tools

1.  Create a new Java class named `HelpdeskTools` under the `tools` package.
2.  Annotate the class with `@Component` and `@RequiredArgsConstructor`.
3.  Inject the `HelpdeskTicketService` bean into this class.
    📝 **Note:** Lombok's `@RequiredArgsConstructor` handles constructor injection.
4.  Create a method named `createTicket` to handle ticket creation.
    *   Annotate the method with `@Tool`.
    *   Use `@ToolParam` to accept the ticket details from the LM model.
        📌 **Example:**
        ```java
        @ToolParam(description = "Details to create a support ticket", required = true)
        TicketRequest ticketRequest
        ```
    *   Accept `TicketRequest` as a parameter to receive issue details.
    *   Accept `ToolContext` as a second parameter to get the username.
        📝 **Note:** `ToolContext` is populated by the framework, not the LM model. It allows storing and retrieving information like a session.
    *   Retrieve the username from the `ToolContext`:

        ```java
        String username = (String) toolContext.getContext().get("username");
        ```

    *   Pass the `ticketRequest` object and `username` to the `createTicket` method of the service object.
    *   Return a string indicating the successful creation of the ticket.
        📌 **Example:**
```java
@Tool(
    name = "createTicket",
    description = "Create a new Support ticket"
)
public String createTicket(
        @ToolParam(
            required = true,
            description = "Details to create a Support ticket"
        ) TicketRequest ticketRequest,
        ToolContext toolContext
) {
    String username = (String) toolContext.getContext().get("username");
    log.info("Creating ticket for user: {}", username);
    HelpDeskTicket ticket = helpDeskTicketService.createTicket(ticketRequest, username);
    log.info("Ticket #{} created successfully for user: {}", ticket.getId(), username);
    return "Ticket #" + ticket.getId() + " created successfully for user: " + username;
}
```
5.  Create another tool method to retrieve existing help desk tickets for a user.
    *   This method does not accept input from the LM model.
    *   It uses `ToolContext` to fetch the username.
    *   It calls a method in the service class to retrieve tickets based on the username.
    *   It returns the retrieved tickets as output.

```java
@Tool(
    name = "getTicketStatus",
    description = "Fetch the status of the open tickets based on a given username"
)
public List<HelpDeskTicket> getTicketStatus(ToolContext toolContext) {
    String username = (String) toolContext.getContext().get("username");
    log.info("Fetching ticket status for user: {}", username);
    List<HelpDeskTicket> tickets = helpDeskTicketService.getTicketsByUsername(username);
    log.info("Found {} tickets for user: {}", tickets.size(), username);
    return tickets;
}
```

### Creating the REST API Controller

1.  Create a new controller class named `HelpDeskController`.
2.  Annotate the class with `@RestController` and `@RequestMapping("/api/tools")`.
3.  Inject the `chatClient` bean (specifically the `helpDeskChatClient` bin) and `HelpdeskTools` into this class using `@Qualifier`.
4.  Create a new REST API endpoint using `@GetMapping`.
    *   The endpoint should accept the username in the request header and the message as a request parameter.
    *   Inject the `HelpdeskTools` bean using the `tools` method.
        💡 **Tip:** This allows injecting tools specific to this REST API.
    *   Use the `toolContext` method to pass a HashMap containing the username to the tool context.
        * 📝 **Note:** This allows passing extra information to the tool context, which can be accessed during tool execution.
        * 💡 **Tip:** Use `toolContext` for information the LM model might not be capable of providing, such as session IDs.

```java
@RestController
@RequestMapping("/api/tools")
@RequiredArgsConstructor
public class HelpDeskController {
    private final ChatClient helpDeskChartClient;
    private final HelpdeskTools helpdeskTools;

    @GetMapping("/helpdesk")
    public ResponseEntity<String> helpDesk(@RequestHeader String username, @RequestParam String message) {
        String answer = helpDeskChartClient.prompt()
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, username))
                .user(message)
                .tools(helpdeskTools)
                .toolContext(Map.of("username", username))
                .call()
                .content();
        return ResponseEntity.ok(answer);
    }
}
```

---

## 7. Exploring Tool Calling with Language Models

After restarting the application, a new table should have been created in the H2 database. Let's verify this. Navigating to the H2 console, we can see a new table named `helpdesk_tickets` has been created.

Querying this table currently returns no records, but the columns are based on the fields defined in our entity class.

Now, let's test our new REST API endpoint, `help_desk`, using Postman.

First, we'll invoke the API by asking the LLM a question: "What is the status of my ticket?" `curl --location 'http://localhost:8080/api/tools/help-desk?message=what%20is%20the%20status%20of%20my%20ticket' \
--header 'username: madan24'`

Even though no tickets have been created yet, the LLM should invoke our tool to query the database for existing issues for the user.

Asking "What is the status of my ticket?" should trigger the LLM to request the Spring application to invoke one of our exposed tools. The breakpoint should hit.

Debugging shows the username fetched from the context is indeed "madan24". The database query returns empty results.

The LLM responds: "It appears that you don't have any existing tickets. If you need assistance with an issue, please let me know. I can create a new support ticket on your behalf." This is the expected behavior.

Next, let's send the message: "I am not able to log in into my account. Seems the account is locked." `curl --location 'http://localhost:8080/api/tools/help-desk?message=I%20am%20not%20able%20to%20log%20in%20into%20my%20account.%20Seems%20the%20account%20is%20locked.' \
--header 'username: madan24'`

The LLM first checks for open tickets for the user. Since none exist, it responds: "It seems that you don't have any existing tickets related to your account login issue. I can create a new support ticket for you to address the logged account. Would you like me to proceed with that?"

Responding with "yes" (`curl --location 'http://localhost:8080/api/tools/help-desk?message=yes' \
--header 'username: madan24'`) results in: "I have successfully created a new support ticket for your issue, unable to log in into account. Account seems to be logged. Your ticket number is three. You will receive updates regarding your ticket shortly. If you have any other questions or need further assistance, feel free to ask."

Querying the `helpdesk_tickets` table now shows a new record with the logged issue and a status of "open" for the username "madan24".

This demonstrates the power of tool calling! 🚀 The logic can be anything: sending emails, triggering APIs, or starting jobs. The possibilities are endless.

Let's ask the same question again: "I am not able to login into my account since the account is locked."

The response is: "Your existing ticket regarding the locked account is still open. The estimated time for a resolution is July 18th. If you need further assistance or have any other questions, feel free to ask."

The LLM doesn't create a duplicate ticket because the prompt template instructs it not to.

Asking "What is the status of my ticket?" returns: "Your ticket regarding the locked account is currently open. This is the so and so estimated time. If you have any further questions or need assistance, feel free to ask."

Using the tools we've developed, the LLM can perform actions that are far more powerful than simple RAG applications.

RAG applications are useful for answering questions based on private documents or information. However, tool calling allows LLMs to execute actions autonomously.

A common question is: How are Java objects converted to a format that the LLM can understand (JSON) when sending responses? And how is the JSON object converted into a Java object?

The Spring Framework handles this conversion. ⚙️

There are interfaces like `ToolCallResultConverter`. The primary responsibility of this interface is to convert tool call results to a string that can be sent back to the AI model.

```java
// Example of a ToolCallResultConverter interface
public interface ToolCallResultConverter {
    String convert(@Nullable Object result, @Nullable Type returnType);
}
```

📌 **Example:** There's an implementation class for this interface that uses a JSON parser to convert a given result object into JSON. You can set a breakpoint inside this class to understand the process.

I hope this demo was helpful. We'll discuss more advanced topics around tool calling in the next lecture.

---

## 8. Using Return Direct for Tool Execution

When a tool is executed, the default behavior is to send the tool's response back to the Language Model (LM). However, there are scenarios where you might want to skip this step and send the output directly to the client application or end-user. This can be achieved using the `returnDirect` configuration.

To enable this, you need to use the `tools` annotation and set the `returnDirect` flag to `true`. By default, it is set to `false`.

📌 **Example:** Tool annotation with `returnDirect` set to `true`:

```java
@Tool(name = "MyTool", description = "A sample tool", returnDirect = true)
public String myToolMethod(String input) {
    // Tool logic here
    return "This is the tool's direct output.";
}
```

When `Return Direct` is set to `true`, the output from the tool will be sent directly to the client application or end-user, bypassing the LM.

### When to Use `Return Direct` 🤔

Use this configuration in the following scenarios:

*   When the tool generates a final and human-readable message that you don't want the model to alter, summarize, or continue reasoning after the tool execution.
*   When you want to reduce latency by skipping the post-processing steps on the LM model.
*   When the tool's output is already suitable for direct presentation to the end-user.

### How to Configure `Return Direct` ⚙️

Similar to how you populate the `name` and `description` properties in the tool annotation, you can also set the `Return Direct` flag.

```java
@Tool(name = "createTicket", description = "Create a new Support ticket", returnDirect = true)
public String createTicket(String issue) {
    // Logic to create a ticket
}
```

Once the application is built and restarted with this configuration, any output from the `Create Ticket` tool will be sent directly to the client application.

### Behind the Scenes 🕵️‍♀️

When the LM model is invoked, the framework provides instructions to the LM about the available tools and how to use them. This information is shared with the LM in the form of a JSON schema.

For example, the schema for the `Create Ticket` tool might look like this:

```json
{
  "type": "object",
  "properties": {
    "ticketRequest": {
      "type": "object",
      "properties": {
        "issue": {
          "type": "string",
          "description": "Description of the issue"
        }
      },
      "required": [
        "issue"
      ]
    }
  }
}
```

The LM model uses this schema to send requests to the tool in a JSON format. The framework then converts this JSON request into a Java object before invoking the tool's method.

### Demonstration 🎬

When a user submits a request that triggers the `Create Ticket` tool with `Return Direct` set to `true`, the tool's output is directly sent back to the user's application (e.g., Postman) without any modification by the LM.

📝 **Note:** This configuration is useful when you want the tool's result to be sent directly to the client application and bypass the LM model.

---

## 9. Handling Runtime Exceptions in Tools

Let's explore what happens when a runtime exception occurs during the execution of a tool.

*   If your tool throws an error, Spring AI wraps it in a **Tool Execution Exception**.
*   By default, these exception details are sent as part of the message to the Language Model (LM).
*   The LM uses these details to provide a meaningful response to the client application.

However, you might want to throw the exception directly to the client application or end-user. In such cases, you need to create a bean of `ToolExecutionExceptionProcessor`.

*   This is an interface with an implementation class.
*   When creating the object of this implementation class, pass the Boolean value `true` to the constructor.
*   This ensures that any exception details inside the tool are sent directly to the client application.

Currently, we rely on the default bean configurations provided by the framework.

*   The Spring Boot framework and the Spring framework create these beans behind the scenes.
*   They create them with the Boolean value set to `false`.
*   With this `false` setting, the exception details are sent to the LM, which then sends a different message to the client application.

In most scenarios, the default behavior is sufficient.

⚠️ **Warning:** Avoid sending exception details directly to the client application unless absolutely necessary.

*   If the client is more interested in the actual exception details and has implemented efficient handling logic on their side, you can configure the `true` Boolean value.

Let's look at a demo of this in tool execution.

First, we'll examine the default behavior inside the `getTicketStatus` method.

📌 **Example:** Throwing a runtime exception:

```java
throw new RuntimeException("Unable to fetch ticket status");
```

When invoking the REST API with a message like "What is the status of my ticket for this username?", and a ticket already exists, the default response might be: "I'm currently unable to fetch the status of your ticket. Would you like me to try again or assist you with something else?"

This is because the LM received the runtime exception details from the tool execution.

Now, let's configure the application to send these exception details directly to the client.

Instead of sending to the LM, we need to create a new bean in the class where the chat client bean is created.

📌 **Example:** Creating a `ToolExecutionExceptionProcessor` bean inside the `HelpDeskChartClientConfig` class:

```java
@Bean
public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
    return new DefaultToolExecutionExceptionProcessor(true);
}
```

Here, the Boolean value `true` indicates that we want to throw the exception back to the client.

After building and restarting the application, sending the same message should now result in the exception details being sent directly.

The response will be a 500 status with an "Internal Server Error" and the path that was invoked.

⚠️ **Warning:** In most scenarios, it's not recommended to throw exception details directly to client applications. Unless they handle them gracefully, it can lead to a bad user experience.

📝 **Note:** The default behavior is generally preferred.

Since we are done with the demo, let's comment out the code changes.

📌 **Example:** Commenting out the exception throwing and bean configuration:

```java
// throw new RuntimeException("Unable to fetch ticket status");
```

```java
// @Bean
// public ToolExecutionExceptionProcessor toolExecutionExceptionProcessor() {
//     return new DefaultToolExecutionExceptionProcessor(true);
// }
```

After building and restarting again, sending the same message should now result in a proper response, such as: "Your ticket regarding the issue is currently open and this is the estimated time."

---

## 10. The Generative AI Journey: From LLMs to Agentic AI

This lecture provides an overview of how generative AI has evolved over the past few years, from simple LLMs to sophisticated Agentic AI systems.

Initially, **LLMs** like ChatGPT were successful because they could answer questions based on their training data. However, these models had limitations:

*   They couldn't answer questions about events after their training period. ⚠️ **Warning:** This "knowledge cutoff" was a significant drawback.

To address these limitations, the industry developed several key advancements:

1.  **Retrieval-Augmented Generation (RAG)**: 
    *   RAG allows LLMs to access external data sources, such as private documents or news websites. 
    *   By incorporating real-time information, LLMs can provide more accurate and up-to-date answers. 📝 **Note:** RAG enhances the knowledge base of LLMs.

2.  **Tool Calling**: 
    *   Tool calling enables LLMs to interact with external tools and APIs. 
    *   These tools can fetch real-time data (e.g., weather, stock prices) or perform actions (e.g., updating databases, sending emails). 💡 **Tip:** Tool calling empowers LLMs to go beyond just providing information.

3.  **AI Agents**: 
    *   An AI agent is an LLM equipped with access to numerous tools and actions. 
    *   AI agents can autonomously make decisions and execute multi-step plans. 📌 **Example:** GitHub Copilot is an AI agent that assists with coding tasks. Cursor IDE.

Let's illustrate the evolution with an example:

*   **LLM (Basic):** "Plan a vacation to London."  The LLM provides general recommendations.
*   **LLM + RAG:** "Plan a vacation to London based on my company policy documents." The LLM considers company policies for better recommendations.
*   **LLM + Tools:** The LLM checks your calendar, flight availability, and hotel options to provide a personalized plan.

An **AI agent** would encompass all these capabilities within a dedicated application, handling the entire vacation planning process autonomously.

```
# Example: AI Agent for Travel Planning
class TravelAgent:
    def __init__(self, llm, calendar_tool, flight_tool, hotel_tool):
        self.llm = llm
        self.calendar_tool = calendar_tool
        self.flight_tool = flight_tool
        self.hotel_tool = hotel_tool

    def plan_vacation(self, destination, company_policy):
        # 1. Use LLM to understand user preferences
        # 2. Use calendar_tool to check availability
        # 3. Use flight_tool to find flights
        # 4. Use hotel_tool to find hotels
        # 5. Consider company_policy
        # 6. Return the best plan
        pass
```

Finally, we arrive at **Agentic AI**:

*   Agentic AI involves multiple AI agents working together to solve complex problems. 
*   📌 **Example:** One agent books flights, another books hotels, and a third arranges transportation.
*   Agentic AI systems are highly intelligent and can handle ambiguous requests. 💡 **Tip:** Think of Agentic AI as a team of specialized AI agents.

The generative AI journey can be summarized as follows:

1.  **LLMs:** Sharing knowledge.
2.  **LLMs + RAG:** Sharing personalized knowledge.
3.  **LLMs + Live Data:** Leveraging real-time information.
4.  **LLMs + Actions:** Performing actions.
5.  **AI Agents:** Performing complex actions autonomously.
6.  **Agentic AI:** Solving complex problems collaboratively.

These advancements are leading to more powerful and versatile AI systems. Protocols like MCP are emerging to support the development of Agentic AI.

---