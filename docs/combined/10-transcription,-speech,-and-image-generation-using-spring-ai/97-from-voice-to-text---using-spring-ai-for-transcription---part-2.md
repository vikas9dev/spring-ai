## Understanding OpenAI Audio Transcription Behind the Scenes 

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

Let's explore the `OpenAI audio transcription options` interface.

By using the options class, we can customize our request by configuring various options. 📌 **Example:**

*   **Model:** Configure the OpenAI model for transcription.
*   **Prompt:** Provide a clue to the model about the audio content.
*   **Language:** Specify the language of the audio.
*   **Temperature:** Adjust the temperature value (default is 0.7).

Let's look at the `transcript response format` enum.

The enum constants include:
*   JSON
*   Text (default)
*   SRT
*   Verbose JSON
*   VTT

By default, the output is in text format. We can configure it to use other formats using this enum.

### Customizing Transcription Requests with Options

Let's see a demo of using `OpenAI audio transcription options` to customize our transcription request.

If you don't need customizations, the simple `call` method (accepting only the audio file) is sufficient. However, for enterprise scenarios, customizations are often required.

Here's how to use the audio transcription options:

1.  Create a new API endpoint (e.g., `/transcribe-options`).
2.  Call the `call` method that accepts an `audio transcription prompt`.
3.  Create an object of `audio transcription prompt`.
    *   Provide the audio file.
    *   Provide a list of options.

```java
OpenAI audioTranscriptionOptions.builder()
    .prompt("talking about spring ai")
    .language("en")
    .temperature(0.5f)
    .responseFormat(OpenAI.Audio.Api.TranscriptResponseFormat.VTT)
    .build();
```

*   **Prompt:** Give a clue to the model. 📌 **Example:** "talking about spring ai".
*   **Language:** Specify the language. 📌 **Example:** "en" for English.
*   **Temperature:** Adjust the temperature. Default is 0.7.
*   **Response Format:** Choose the desired format. 📌 **Example:** `VTT` for subtitles.

By providing language and prompt details, you simplify the model's job. 💡 **Tip:** While OpenAI models can identify language, providing it explicitly improves accuracy.

📝 **Note:** As of now, OpenAI models primarily support output generation in English, but future support for other languages is expected.

The `VTT` format is used for generating subtitles. It provides timestamps for each line of text, which is useful for video players.

The `call` method that accepts the `audio transcription prompt` returns an `audio transcription response`. Extract the output from this response object.

```java
AudioTranscriptionResponse audioTranscriptionResponse = openAIClient.audio().transcribe(audioTranscriptionPrompt);
return audioTranscriptionResponse.getResult().getOutput();
```

The API will now return the output in the specified format (e.g., VTT).

### Default Model and Customization

For most scenarios, the default model and text format are sufficient. However, you can customize the model if needed.

To determine the default model, set a breakpoint inside the `call` method and inspect the `create request` method.

The default model is `whisper-1`.

If you need to use a different model, consult the OpenAI documentation and choose the best model for your use case.

### OpenAI Audio and Speech API

The OpenAI platform has a section dedicated to audio and speech. It highlights use cases like transcription and translation, both powered by the `whisper-1` model.

### Limitations and Supported Languages

⚠️ **Warning:** File uploads are currently limited to 25 MB.

Supported input types include:
*   MP3
*   MP4
*   MPEG
*   MPG
*   M4A
*   WAV
*   WebM

Your audio file can be in any of the supported languages listed on the OpenAI website. However, the output is currently only supported in English.

Hopefully, this clarifies how to implement the transcription process using OpenAI and the Spring AI framework.
