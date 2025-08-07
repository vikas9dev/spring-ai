## Setting Up Quadrant Vector Database Locally

This section outlines how to set up the Quadrant vector database locally using Docker. Quadrant is an open-source vector database widely used in the industry due to its ease of setup. The concepts and code demonstrated here are applicable regardless of the specific vector database you choose.

### Prerequisites

*   🐳 **Docker Desktop:** Ensure Docker Desktop is installed on your system.
*   🔑 **Docker Account:** Log in to your Docker account within Docker Desktop.

### Steps

1.  **Add Spring Boot Docker Compose Dependency:**
    Add the `spring-boot-docker-compose` dependency to your `pom.xml` file. This dependency facilitates the automatic setup of dependent Docker containers during application startup.

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-docker-compose</artifactId>
    </dependency>
    ```

2.  **Create `compose.yml` File:**
    Create a file named `compose.yml` in the root directory of your project (not under `src` or `resources`).

    📝 **Note:** Using the default name `compose.yml` avoids the need to specify its location in `application.properties`.

3.  **Configure `compose.yml`:**
    Paste the following configuration into the `compose.yml` file. This configuration defines a service named `quadrant` using the `qdrant/qdrant` Docker image and maps ports 6333 (HTTP) and 6334 (gRPC).

    ```yaml
    version: "3.9"
    services:
      qdrant:
        image: qdrant/qdrant
        ports:
          - "6333:6333"
          - "6334:6334"
    ```

    📝 **Note:** These configurations are based on the official Quadrant documentation. Refer to the documentation for more details.

4.  **Add Additional Dependencies to `pom.xml`:**
    Include the following dependencies in your `pom.xml` file:

    ```xml
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-rag</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-spring-store</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-starter-vector-qdrant</artifactId>
    </dependency>
    ```

    *   `spring-ai-rag`: Enables Retrieval-Augmented Generation (RAG) implementation.
    *   `spring-ai-spring-store`: Provides advisors around vector stores.
    *   `spring-ai-starter-vector-qdrant`: Offers seamless integration between Spring Boot and Quadrant.

5.  **Sync Maven Changes:**
    After adding the dependencies, synchronize the Maven changes.

6.  **Configure `application.properties`:**
    Add the following properties to your `application.properties` file:

    ```properties
    spring.docker.compose.lifecycle-management=down
    spring.ai.vectorstore.qdrant.initialize-schema=true
    spring.ai.vectorstore.qdrant.host=localhost
    spring.ai.vectorstore.qdrant.port=6334
    spring.ai.vectorstore.qdrant.collection-name=easybytes
    ```

    *   `spring.docker.compose.lifecycle-management=down`:  Ensures the container is completely deleted when the application stops.
    *   `spring.ai.vectorstore.qdrant.initialize-schema=true`:  Instructs the framework to initialize the required schema upon Quadrant database creation.
    *   `spring.ai.vectorstore.qdrant.host=localhost`: Specifies the host for the Quadrant database (default is localhost).
    *   `spring.ai.vectorstore.qdrant.port=6334`: Sets the port for gRPC communication with the Quadrant database (default is 6334).
    *   `spring.ai.vectorstore.qdrant.collection-name=easybytes`: Defines the name of the collection (similar to a table in SQL databases).

7.  **Start the Application:**
    Run your Spring Boot application. The Spring Boot framework will automatically start a Docker container using the Quadrant Docker image.

8.  **Verify Container Startup:**
    Check the application logs for information related to Docker Compose. You should see logs indicating that the framework detected the `compose.yml` file and executed Docker CLI commands to create and start the container.

    The logs should show steps like:
    *   Creating a network.
    *   Creating a container.
    *   Starting the container.
    *   Waiting for the container to become healthy.

9.  **Access Quadrant Dashboard:**
    Open your Docker Desktop and navigate to the containers section. You should see a container named `spring-ai-qdrant-1` running on ports 6333 and 6334.

    Access the Quadrant dashboard by opening the following URL in your browser: `http://localhost:6333/dashboard`.

    You should see a collection named `easybytes` in the dashboard.

### Conclusion

By following these steps, you should have successfully set up the Quadrant vector database locally. Ensure that your database is running correctly and that you can access its dashboard.
