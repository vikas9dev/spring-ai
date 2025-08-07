## Building an AI Assistant for Technical Issue Reporting

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
package com.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "help_desk_tickets")
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

📝 **Note:** This assumes familiarity with Spring Data JPA concepts. If you're new to Spring Boot, consider reviewing a Spring Boot course first.

Annotations used:

*   `@Entity`: Marks the class as a JPA entity.
*   `@Table(name = "help_desk_tickets")`: Specifies the database table name.
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
package com.example.repository;

import com.example.entity.HelpDeskTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {
    List<HelpDeskTicket> findByUsername(String username);
}
```

This interface extends `JpaRepository` to provide CRUD operations. We also define a derived query method, `findByUsername`, which will query the `help_desk_tickets` table based on the username.

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

Create a `model` package and a `TicketRequest` record.

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
