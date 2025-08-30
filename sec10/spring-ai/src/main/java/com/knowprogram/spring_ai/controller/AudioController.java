package com.knowprogram.spring_ai.controller;

import org.springframework.ai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.OpenAiAudioSpeechOptions;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.OpenAiAudioTranscriptionOptions;
import org.springframework.ai.openai.api.OpenAiAudioApi;
import org.springframework.ai.openai.audio.speech.SpeechModel;
import org.springframework.ai.openai.audio.speech.SpeechPrompt;
import org.springframework.ai.openai.audio.speech.SpeechResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api")
public class AudioController {
    private final OpenAiAudioTranscriptionModel openAiAutoTranscriptionModel;
    private final SpeechModel speechModel;

    public AudioController(OpenAiAudioTranscriptionModel openAiAutoTranscriptionModel, SpeechModel speechModel) {
        this.openAiAutoTranscriptionModel = openAiAutoTranscriptionModel;
        this.speechModel = speechModel;
    }

    @GetMapping("/transcribe")
    public String transcribe(@Value("classpath:SpringAI.mp3") Resource audioFile) {
        return openAiAutoTranscriptionModel.call(audioFile);
    }

    @GetMapping("/transcribe-options")
    public String transcribeWithOptions(@Value("classpath:SpringAI.mp3") Resource audioFile) {
        var audioTranscriptionResponse = openAiAutoTranscriptionModel.call(new AudioTranscriptionPrompt(audioFile,
                OpenAiAudioTranscriptionOptions.builder()
                        .prompt("Talking about Spring AI")
                        .language("en")
                        .temperature(0.5f)
                        .responseFormat(OpenAiAudioApi.TranscriptResponseFormat.VTT)
                        .build()));
        return audioTranscriptionResponse.getResult().getOutput();
    }

    @GetMapping("/speech")
    public String speech(@RequestParam String message) throws IOException {
        byte[] audioBytes = speechModel.call(message);
        Path path = Paths.get("output.mp3");
        Files.write(path, audioBytes);
        return "MP3 saved successfully to " + path.toAbsolutePath();
    }

    @GetMapping("/speech-options")
    public String speechWithOptions(@RequestParam String message) throws IOException {
        SpeechResponse speechResponse = speechModel.call(new SpeechPrompt(message,
                OpenAiAudioSpeechOptions.builder()
                        .voice(OpenAiAudioApi.SpeechRequest.Voice.NOVA)
                        .speed(2.0f)
                        .responseFormat(OpenAiAudioApi.SpeechRequest.AudioResponseFormat.MP3)
                        .build()));
        Path path = Paths.get("speech-options.mp3");
        Files.write(path, speechResponse.getResult().getOutput());
        return "MP3 saved successfully to " + path.toAbsolutePath();
    }

}
