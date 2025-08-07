## Leveraging Spring AI for Audio Transcription

In this section, we'll explore how to use Spring AI to understand voice/audio and generate images. We'll start with audio transcription, which is the process of converting spoken audio into text. We'll also look at the reverse process: generating speech or audio from text.

This lecture focuses on implementing audio transcription using Spring AI and OpenAI models.

### Setting Up the Project

For this demo, I've created a basic Spring application. Here's how to configure your `pom.xml`:

1.  Add the following dependencies:
    *   `spring-boot-starter-web`
    *   `spring-ai-spring-boot-starter-openai`

    ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.ai</groupId>
        <artifactId>spring-ai-spring-boot-starter-openai</artifactId>
    </dependency>
    ```

    📝 **Note:** If you're using a different LLM, add the corresponding starter project instead of the OpenAI one.

2.  Configure `application.properties`:

    ```properties
    spring.application.name=audio-transcription-demo
    logging.pattern.console=%clr(%d{yyyy-MM-dd HH:mm:ss.SSS}){faint} %clr(%5p) %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n%wEx
    logging.level.org.springframework.ai=DEBUG
    spring.ai.openai.api-key=${OPENAI_API_KEY}
    ```

    ⚠️ **Warning:**  Make sure to set the `OPENAI_API_KEY` environment variable. You can do this in your IDE's run configuration.

### Creating the Audio Controller

1.  Create a new package named `controller`.
2.  Inside the `controller` package, create a Java class named `AudioController`.

    ```java
    package com.example.ai.controller;

    import org.springframework.ai.openai.audio.OpenAIAutoTranscriptionModel;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.core.io.Resource;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class AudioController {

        private final OpenAIAutoTranscriptionModel openAIAutoTranscriptionModel;

        @Value("classpath:springai.mp3")
        private Resource audioFile;

        public AudioController(OpenAIAutoTranscriptionModel openAIAutoTranscriptionModel) {
            this.openAIAutoTranscriptionModel = openAIAutoTranscriptionModel;
        }

        @GetMapping("/transcribe")
        public String transcribe() {
            return openAIAutoTranscriptionModel.call(audioFile);
        }
    }
    ```

3.  Annotate the class with `@RestController`.
4.  Use `OpenAIAutoTranscriptionModel` to perform the transcription. This class is provided by the framework when you add the OpenAI starter project.

    📌 **Example:**  This class takes an audio file as input and returns the transcribed text.

    💡 **Tip:**  This can be useful in enterprise applications where users record voice complaints. You can then use the transcribed text to understand the context of the complaint and route it to the appropriate team.

5.  Inject `OpenAIAutoTranscriptionModel` using constructor injection.
6.  Create a `GET` endpoint `/transcribe` that takes an audio file as input and returns the transcribed text.

    ```java
    @GetMapping("/transcribe")
    public String transcribe() {
        return openAIAutoTranscriptionModel.call(audioFile);
    }
    ```

7.  Load the audio file from the classpath using `@Value`.

    ```java
    @Value("classpath:springai.mp3")
    private Resource audioFile;
    ```

    📝 **Note:**  In real applications, you would typically load the audio file from a storage system or accept it through a UI.

8.  The `springai.mp3` audio file contains a brief description of Spring AI. You can find this file in the GitHub repository.

    ⚠️ **Warning:**  Listen to the audio file before testing to verify the accuracy of the transcription.

9.  Invoke the `call` method of the `OpenAIAutoTranscriptionModel` to transcribe the audio file.

    ```java
    return openAIAutoTranscriptionModel.call(audioFile);
    ```

### Running the Application

1.  Build the application.
2.  Run the application in debug mode.
3.  Use Postman or a similar tool to invoke the `/transcribe` endpoint.  No request body is needed as the audio file is loaded from the classpath.
4.  The response will contain the transcribed text from the audio file.

The transcribed text should closely match the content of the audio file. Spring AI simplifies the process of using LLMs, allowing developers to focus on business problems rather than the complexities of AI.
