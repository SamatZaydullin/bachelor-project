package ru.itis.diploma.utils;

import org.owasp.encoder.Encode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.UUID;

@Component
public class SpeechSynthesizeUtilImpl implements SpeechSynthesizeUtil {

    @Value("${speech.recognition.topic}")
    private String topic;

    @Value("${speech.voice.speed}")
    private String speed;

    @Value("${speech.lang}")
    private String lang;

    @Value("${speech.emotion.list}")
    private String emotions;

    @Value("${speech.voice.list}")
    private String voices;

    //URI for speech recognition
    @Value("${speech.synthesize.uri}")
    private String speechRecognitionURI;

    @Value("${speech.recognition.api-key}")
    private String API_KEY;

    @Value("audio.synth.path")
    private String responseAudioPath;


    @Override
    public String synthesizeVoice(String text) {
        String uuid = UUID.randomUUID().toString();
        File res = new File(String.format(responseAudioPath, uuid));
        try {

            String params = prepareParams(text);

            URL url = new URL(speechRecognitionURI);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url.toURI())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Api-Key " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(params.getBytes(StandardCharsets.UTF_8)))
                    .build();

            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());

            try (InputStream in = response.body()) {
                new BufferedOutputStream(new FileOutputStream(res)).write(in.readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (MalformedURLException c) {
            c.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return uuid;
    }

    private String prepareParams(String text){
        String[] emotion = emotions.split(",");
        String[] voice = voices.split(",");

        String params = String.format(
                "lang=%s&text=%s&speed=%s&voice=%s&emotion=%s",
                lang,
                Encode.forHtml(text.replaceAll("&", "")),
                speed,
                voice[new Random().nextInt(voice.length)],
                emotion[new Random().nextInt(emotion.length)]
        );

        return params;
    }
}
