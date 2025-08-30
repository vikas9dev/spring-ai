# 10 Transcription, Speech, And Image Generation Using Spring Ai


Sections-
- [10 Transcription, Speech, And Image Generation Using Spring Ai](#10-transcription-speech-and-image-generation-using-spring-ai)
  - [1. Leveraging Spring AI for Audio Transcription](#1-leveraging-spring-ai-for-audio-transcription)
    - [Setting Up the Project](#setting-up-the-project)
    - [Creating the Audio Controller](#creating-the-audio-controller)
    - [Running the Application](#running-the-application)
  - [2. Understanding OpenAI Audio Transcription Behind the Scenes](#2-understanding-openai-audio-transcription-behind-the-scenes)
    - [Customizing Transcription Requests with Options](#customizing-transcription-requests-with-options)
    - [Default Model and Customization](#default-model-and-customization)
    - [OpenAI Audio and Speech API](#openai-audio-and-speech-api)
    - [Limitations and Supported Languages](#limitations-and-supported-languages)
  - [3. Generating Speech from Text using Gen AI Models](#3-generating-speech-from-text-using-gen-ai-models)
  - [4. Generating Images with Spring AI and OpenAI](#4-generating-images-with-spring-ai-and-openai)
    - [Creating an Image Controller](#creating-an-image-controller)
    - [Understanding ImagePrompt](#understanding-imageprompt)
    - [Building a Simple REST API](#building-a-simple-rest-api)
    - [Testing the API](#testing-the-api)
    - [Configuring Image Options](#configuring-image-options)
    - [Testing with Options](#testing-with-options)
    - [Exploring Other Image Models](#exploring-other-image-models)


---

## 1. Leveraging Spring AI for Audio Transcription

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
    logging.pattern.console=%green(%d{HH:mm:ss.SSS}) %blue(%-5level) %red([%thread]) %yellow(%logger{15}) - %msg%n
    spring.ai.openai.api-key=${OPENAI_API_KEY}
    logging.level.org.springframework.ai.chat.client.advisor=DEBUG
    ```

    ⚠️ **Warning:**  Make sure to set the `OPENAI_API_KEY` environment variable. You can do this in your IDE's run configuration.

### Creating the Audio Controller

1.  Create a new package named `controller`.
2.  Inside the `controller` package, create a Java class named `AudioController`.

    ```java
    @RestController
    @RequestMapping("/api")
    public class AudioController {
        private final OpenAiAudioTranscriptionModel openAiAutoTranscriptionModel;

        public AudioController(OpenAiAudioTranscriptionModel openAiAutoTranscriptionModel) {
            this.openAiAutoTranscriptionModel = openAiAutoTranscriptionModel;
        }

        @GetMapping("/transcribe")
        public String transcribe(@Value("classpath:SpringAI.mp3") Resource audioFile) {
            return openAiAutoTranscriptionModel.call(audioFile);
        }
    }
    ```

3.  Annotate the class with `@RestController`.
4.  Use `OpenAIAutoTranscriptionModel` to perform the transcription. This class is provided by the framework when you add the OpenAI starter project.

    📌 **Example:**  This class takes an audio file as input and returns the transcribed text.

    💡 **Tip:**  This can be useful in enterprise applications where users record voice complaints. You can then use the transcribed text to understand the context of the complaint and route it to the appropriate team.

5.  Inject `OpenAIAutoTranscriptionModel` using constructor injection.
6.  Create a `GET` endpoint `/transcribe` that takes an audio file as input and returns the transcribed text.
7.  Load the audio file from the classpath using `@Value`.

    📝 **Note:**  In real applications, you would typically load the audio file from a storage system or accept it through a UI.

8.  The `springai.mp3` audio file contains a brief description of Spring AI. You can find this file in the GitHub repository.

    ⚠️ **Warning:**  Listen to the audio file before testing to verify the accuracy of the transcription.

9.  Invoke the `call` method of the `OpenAIAutoTranscriptionModel` to transcribe the audio file.

### Running the Application

1.  Build the application.
2.  Run the application in debug mode.
3.  Use Postman or a similar tool to invoke the `/transcribe` endpoint (`curl --location 'http://localhost:8080/api/transcribe'`).  No request body is needed as the audio file is loaded from the classpath.
4.  The response will contain the transcribed text from the audio file.

The transcribed text should closely match the content of the audio file. Spring AI simplifies the process of using LLMs, allowing developers to focus on business problems rather than the complexities of AI.

---

## 2. Understanding OpenAI Audio Transcription Behind the Scenes

Let's delve into what happens behind the scenes when we use the `call` method for audio transcription.

Essentially, the requests we send are converted into an audio transcription prompt. 

Let's examine the relevant class.

This class has a constructor that accepts the audio file. 
The supported audio file types are:
*   MP3
*   MP4
*   MPEG
*   MPG
*   M4A
*   WAV
*   WebM

There's also another constructor that accepts both the audio file and audio transcription options.

Let's explore the `AudioTranscriptionOptions` interface.

By using the options class, we can customize our request by configuring various options. 📌 **Example:**

*   **Model:** Configure the OpenAI model for transcription.
*   **Prompt:** Provide a clue to the model about the audio content.
*   **Language:** Specify the language of the audio.
*   **Temperature:** Adjust the temperature value (default is 0.7).

Let's look at the `TranscriptResponseFormat` enum.

The enum constants include:
*   JSON
*   Text (default)
*   SRT
*   Verbose JSON
*   VTT

By default, the output is in text format. We can configure it to use other formats using this enum.

### Customizing Transcription Requests with Options

Let's see a demo of using `OpenAiAudioTranscriptionOptions` to customize our transcription request.

If you don't need customizations, the simple `call` method (accepting only the audio file) is sufficient. However, for enterprise scenarios, customizations are often required.

Here's how to use the audio transcription options:

1.  Create a new API endpoint (e.g., `/transcribe-options`).
2.  Call the `call` method that accepts an `audio transcription prompt`.
3.  Create an object of `audio transcription prompt`.
    *   Provide the audio file.
    *   Provide a list of options.

```java
@GetMapping("/transcribe-options")
public String transcribeWithOptions(@Value("classpath:SpringAI.mp3") Resource audioFile) {
    var audioTranscriptionResponse = openAiAutoTranscriptionModel.call(
        new AudioTranscriptionPrompt(
            audioFile,
            OpenAiAudioTranscriptionOptions.builder()
                .prompt("Talking about Spring AI")
                .language("en")
                .temperature(0.5f)
                .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.VTT)
                .build()
        )
    );

    return audioTranscriptionResponse.getResult().getOutput();
}
```

*   **Prompt:** Give a clue to the model. 📌 **Example:** "talking about spring ai".
*   **Language:** Specify the language. 📌 **Example:** "en" for English.
*   **Temperature:** Adjust the temperature. Default is 0.7.
*   **Response Format:** Choose the desired format. 📌 **Example:** `VTT` for subtitles.

By providing language and prompt details, you simplify the model's job. 💡 **Tip:** While OpenAI models can identify language, providing it explicitly improves accuracy.

📝 **Note:** As of now, OpenAI models primarily support output generation in English, but future support for other languages is expected.

The `VTT` format is used for generating subtitles. It provides timestamps for each line of text, which is useful for video players.

The `call` method that accepts the `AudioTranscriptionPrompt` returns an `AudioTranscriptionResponse`. Extract the output from this response object.

API call:- `curl --location 'http://localhost:8080/api/transcribe-options'`

The API will now return the output in the specified format (e.g., VTT).

```vtt
WEBVTT

00:00:00.000 --> 00:00:05.280
Spring AI makes it easy to integrate large language models into your Spring Boot applications.

00:00:05.280 --> 00:00:09.040
It's powerful, flexible, and designed for Java developers.
```

### Default Model and Customization

For most scenarios, the default model and text format are sufficient. However, you can customize the model if needed.

To determine the default model, set a breakpoint inside the `call` method and inspect the `create request` method.

The default model is `whisper-1`. On output, `whisper-1` supports a range of formats (json, text, srt, verbose_json, vtt); the newer `gpt-4o-mini-transcribe` and `gpt-4o-transcribe` snapshots currently only support json or plain text responses.

If you need to use a different model, consult the OpenAI documentation and choose the best model for your use case. See more [here](https://platform.openai.com/docs/guides/speech-to-text).

### OpenAI Audio and Speech API

The OpenAI platform has a section dedicated to audio and speech. It highlights use cases like transcription and translation, both powered by the `whisper-1` model.

### Limitations and Supported Languages

⚠️ File uploads are currently limited to 25 MB, and the following input file types are supported: mp3, mp4, mpeg, mpga, m4a, wav, and webm.

Your audio file can be in any of the supported languages listed on the OpenAI website. However, the output is currently only supported in English.

Hopefully, this clarifies how to implement the transcription process using OpenAI and the Spring AI framework.

---

## 3. Generating Speech from Text using Gen AI Models

Gen AI models can be used to generate speech or audio files from a given text, which is the opposite of transcription. 🗣️

In **text-to-speech**, the input is text (prompts), and the output is a speech, voice, or audio file.

You can request Gen AI models to generate various styles of voices or speeches, such as male or female voices, based on your requirements. 🧑‍💼👩‍💼

These features can be used in various enterprise application scenarios.

📌 **Example:** A bank application can use text-to-speech to send IVR notifications to customers about credit card bill due dates. Instead of robotic-sounding IVR messages, Gen AI models can generate more natural-sounding voices.

Let's look at a demo of converting text to speech.

There is an interface called `SpeechModel` with overloaded abstract methods named `call`:

*   The first `call` method accepts a message and returns audio content as bytes.
*   The second `call` method accepts a request in the form of `SpeechPrompt` and returns a `SpeechResponse`.

If you are fine with the default configurations, you can use the first `call` method.

The implementation class for this interface is `OpenAIaudioSpeechModel`. A bean of this class is created by the Spring Boot framework during application startup.

To convert text to audio, you need to inject the bean of type `SpeechModel`.

```java
@Autowired
private SpeechModel speechModel;
```

Next, create a REST API for speech generation.

```java
@Autowired
private final SpeechModel speechModel;

// In Constructor
public AudioController(..., SpeechModel speechModel) {
    // ...
    this.speechModel = speechModel;
}

@GetMapping("/speech")
public String speech(@RequestParam String message) throws IOException {
    byte[] audioBytes = speechModel.call(message);

    Path path = Paths.get("output.mp3");
    Files.write(path, audioBytes);

    return "MP3 saved successfully to " + path.toAbsolutePath();
}
```

This REST API accepts a message as a request parameter and passes it to the `call` method of the `SpeechModel`. The returned byte array is then saved as an MP3 file.

After building the application, you can trigger the `/speech` REST API from Postman with a message like "How are you my friend?" (`curl --location 'http://localhost:8080/api/speech?message=How%20are%20you%20my%20friend%3F'`). This will create an `output.mp3` file with the generated speech.

In the example above, we invoked the `call` method directly with the message. Behind the scenes, it creates a `SpeechPrompt` object.

The `SpeechPrompt` class has a constructor that accepts the message and `OpenAIaudioSpeechOptions`. This allows you to configure:

*   The model to use.
*   The input message.
*   The voice type.

The available voices for the OpenAI models include: alloy, echo, fable, onyx, nova, and shimmer.

You can also configure the audio response format (default is MP3, but you can use opus, aac, and flac).

The audio speed can also be controlled. The default speed is 1.0 (normal), but you can set it to 0.25 for the slowest speed or 4.0 for the fastest.

Let's configure these options and generate another audio file.

```java
@GetMapping("/speech-options")
public String speechWithOptions(@RequestParam String message) throws IOException {
    SpeechResponse speechResponse = speechModel.call(
        new SpeechPrompt(
            message,
            OpenAiAudioSpeechOptions.builder()
                .voice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
                .speed(2.0f)
                .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                .build()
        )
    );

    Path path = Paths.get("speech-options.mp3");
    Files.write(path, speechResponse.getResult().getOutput());

    return "MP3 saved successfully to " + path.toAbsolutePath();
}
```

This API creates a `SpeechPrompt` object with configured options (voice, speed, and response format) and passes it to the `call` method:- `curl --location 'http://localhost:8080/api/speech-options?message=Spring%20AI%20is%20an%20exciting%20framework%20that%20simplifies%20building%20AI-powered%20applications.%20It%20integrates%20seamlessly%20with%20Spring%20Boot%2C%20enabling%20developers%20to%20leverage%20large%20language%20models%20and%20create%20intelligent%2C%20scalable%20solutions%20with%20ease'`

After building, you can invoke the `/speech-options` API with a message. The generated audio file will have the configured voice and speed.

To generate speech from text, inject the bean of type `SpeechModel` into your controller class.

---

## 4. Generating Images with Spring AI and OpenAI

This section explores how to generate images using Spring AI and OpenAI models. Similar to speech models, image models allow you to generate images based on a given prompt. Let's dive into some demos.

### Creating an Image Controller

First, we'll create a new controller named `ImageController`.

1.  Annotate the class for REST API support.
2.  Inject an `ImageModel` bean into the class.

```java
@RestController
@RequestMapping("/api")
public class ImageController {

    private final ImageModel imageModel;

    public ImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    // ... more code follows
}
```

The `ImageModel` interface has a single abstract method that accepts an `ImagePrompt` as a request.

### Understanding ImagePrompt

The `ImagePrompt` class offers several constructors. For simple scenarios, you can use the constructor that accepts a prompt message. For more control, you can use constructors that accept an `ImageMessage`.

The `ImageMessage` class includes fields like `text` and `weight`. You can use these to provide detailed instructions. 📌 **Example:** If you want to emphasize a "red car" in front of a building, assigning a higher weight to "red car" will guide the model to prioritize it.

### Building a Simple REST API

Let's create a REST API endpoint to generate images.

1.  Use the `@GetMapping` annotation to map the `/image` path.
2.  Create a method that accepts a `message` request parameter.
3.  Invoke the `call` method of the `ImageModel` with an `ImagePrompt` object.
4.  Extract the image URL from the `ImageResponse`.

```java
@GetMapping("/image")
public String generateImage(@RequestParam("message") String message) {
    ImageResponse imageResponse = imageModel.call(new ImagePrompt(message));
    return imageResponse.getResult().getOutput().getUrl();
}
```

The OpenAI model generates an image, uploads it to a location, and provides a URL. ⚠️ **Warning:** This URL is temporary and expires after a short period.

📝 **Note:** If you need to store the image, use the `getBase64Json` method to get the image data as a byte array in JSON format. You can then create a file and store it in a storage system or database.

### Testing the API

After building the application, invoke the `/image` REST API with a detailed prompt. 📌 **Example:**

```
/image?message=black and white, close up of a smiling woman with closed eyes, her hair framing her face naturally, excluding warmth and joy
```

The more specific you are, the better the generated image will be.

### Configuring Image Options

To customize image generation, use the `OpenAIImageOptions` class.

1.  Create a new REST API endpoint, for example, `/image-options`.
2.  Use `OpenAIImageOptions.builder()` to configure various options.

Available options include:

*   `n`: Number of images to generate (1-10, but only 1 is supported for Dall-E 3).
*   `model`: Image generation model (default is Dall-E 3).
*   `width`: Image width (valid values: 256, 512, 1024).
*   `height`: Image height (valid values: 256, 512, 1024).
*   `quality`: Image quality (`normal` or `hd`).
*   `responseFormat`: Response format (`url` or `b64_json`).
*   `size`: Image size (e.g., "256x256").
*   `style`: Image style (`vivid` or `natural`). `vivid` produces hyper-real images, while `natural` produces more natural-looking images. This parameter is only supported for Dall-E 3.
*   `user`: User identifier (username or email) for monitoring and abuse detection.

```java
@GetMapping("/image-options")
public String generateImageWithOptions(@RequestParam("message") String message) {
    var imageResponse = imageModel.call(
        new ImagePrompt(
            message,
            OpenAiImageOptions.builder()
                .N(1)
                .quality("hd")
                .style("natural")
                .height(1024)
                .width(1024)
                .responseFormat("url")
                .build()
        )
    );

    return imageResponse.getResult().getOutput().getUrl();
}
```

The `user` field is used by OpenAI to provide attribute information under the metadata of the image and to detect abuse. 💡 **Tip:** Providing a user identifier helps OpenAI monitor and prevent abuse, such as generating excessive images.

### Testing with Options

Invoke the `/image-options` endpoint with a message and the configured options. 📌 **Example:**

```
/image-options?message=three people collaborate in a modern office discussing a presentation projected on the wall. Minimalistic design with warm lighting.
```

### Exploring Other Image Models

Spring AI supports various image models. While this example uses OpenAI, you can explore other models like Stability AI. ⚠️ **Warning:** Each model may require a separate billing account. Always refer to the Spring AI documentation for more details.

---

EazyBytes GitHub: [https://github.com/eazybytes/spring-ai](https://github.com/eazybytes/spring-ai)