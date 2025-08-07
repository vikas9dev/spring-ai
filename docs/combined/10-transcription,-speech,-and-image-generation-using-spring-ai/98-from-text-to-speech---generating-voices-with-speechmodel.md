## Generating Speech from Text using Jenny Models

Jenny models can be used to generate speech or audio files from a given text, which is the opposite of transcription. 🗣️

In **text-to-speech**, the input is text (prompts), and the output is a speech, voice, or audio file.

You can request Jenny models to generate various styles of voices or speeches, such as male or female voices, based on your requirements. 🧑‍💼👩‍💼

These features can be used in various enterprise application scenarios.

📌 **Example:** A bank application can use text-to-speech to send IVR notifications to customers about credit card bill due dates. Instead of robotic-sounding IVR messages, Jenny models can generate more natural-sounding voices.

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
@RestController
public class AudioController {

    @Autowired
    private SpeechModel speechModel;

    @GetMapping("/speech")
    public String generateSpeech(@RequestParam String message) throws IOException {
        byte[] audioBytes = speechModel.call(message);
        Path path = Paths.get("output.mp3");
        Files.write(path, audioBytes);
        return "MP3 saved successfully to output.mp3";
    }
}
```

This REST API accepts a message as a request parameter and passes it to the `call` method of the `SpeechModel`. The returned byte array is then saved as an MP3 file.

After building the application, you can trigger the `/speech` REST API from Postman with a message like "How are you my friend?". This will create an `output.mp3` file with the generated speech.

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
public String generateSpeechWithOptions(@RequestParam String message) throws IOException {
    SpeechPrompt speechPrompt = SpeechPrompt.builder()
            .message(message)
            .options(OpenAIaudioSpeechOptions.builder()
                    .voice(OpenAIaudioSpeechOptions.Voice.NOVA)
                    .speed(2.0f)
                    .responseFormat(OpenAIaudioSpeechOptions.ResponseFormat.MP3)
                    .build())
            .build();

    SpeechResponse speechResponse = speechModel.call(speechPrompt);
    byte[] audioBytes = speechResponse.getResult().getOutput();
    Path path = Paths.get("speech-options.mp3");
    Files.write(path, audioBytes);
    return "MP3 saved successfully to speech-options.mp3";
}
```

This API creates a `SpeechPrompt` object with configured options (voice, speed, and response format) and passes it to the `call` method.

After building, you can invoke the `/speech-options` API with a message. The generated audio file will have the configured voice and speed.

To generate speech from text, inject the bean of type `SpeechModel` into your controller class.
