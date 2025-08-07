## Building the Help Desk Chat Client and Tools

This section details the process of creating a dedicated chat client and associated tools for a help desk scenario. The goal is to provide a tailored experience with specific system prompts and database interaction capabilities.

### Creating a New Chat Client

1.  Copy the existing time chart client configuration.
2.  Paste the configuration into the `config` package.
3.  Rename the class to `HelpDeskChartClientConfig`.
4.  Name the bin `helpDeskChartClient`.
    📝 **Note:** A separate chat client bin is created for the help desk to provide a specific system prompt message tailored to this scenario.
5.  Inject the existing time tools under the default tools.
6.  Inject new tools for database interactions directly using the `tools` method.

### Adding a System Message

1.  Create a new prompt template file named `HelpdeskSystemPromptTemplate` under the `Resources/Prompt Templates` folder.
2.  Add the following system message to the template file:

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
        @Tool(name = "Create Ticket", description = "Create the support ticket")
        public String createTicket(@ToolParam(description = "Details to create a support ticket", required = true) TicketRequest ticketRequest, ToolContext toolContext) {
            String username = (String) toolContext.getContext().get("username");
            SavedTicket savedTicket = helpdeskTicketService.createTicket(ticketRequest, username);
            return "Ticket number " + savedTicket.getId() + " created successfully for user " + username;
        }
        ```
5.  Create another tool method to retrieve existing help desk tickets for a user.
    *   This method does not accept input from the LM model.
    *   It uses `ToolContext` to fetch the username.
    *   It calls a method in the service class to retrieve tickets based on the username.
    *   It returns the retrieved tickets as output.

### Creating the REST API Controller

1.  Create a new controller class named `HelpDeskController`.
2.  Annotate the class with `@RestController` and `@RequestMapping("/helpdesk")`.
3.  Inject the `chatClient` bean (specifically the `helpDeskChatClient` bin) and `HelpdeskTools` into this class using `@Qualifier`.
4.  Create a new REST API endpoint using `@GetMapping`.
    *   The endpoint should accept the username in the request header and the message as a request parameter.
    *   Inject the `HelpdeskTools` bean using the `tools` method.
        💡 **Tip:** This allows injecting tools specific to this REST API.
    *   Use the `toolContext` method to pass a HashMap containing the username to the tool context.
        📌 **Example:**
        ```java
        Map<String, Object> context = new HashMap<>();
        context.put("username", username);
        promptData.setToolContext(context);
        ```
        📝 **Note:** This allows passing extra information to the tool context, which can be accessed during tool execution.
        💡 **Tip:** Use `toolContext` for information the LM model might not be capable of providing, such as session IDs.
