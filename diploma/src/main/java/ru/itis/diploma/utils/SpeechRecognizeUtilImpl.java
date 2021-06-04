package ru.itis.diploma.utils;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Component;
import ru.itis.diploma.exceptions.SpeechRecognitionException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.Locale;
import java.util.Map;

@Component
public class SpeechRecognizeUtilImpl implements SpeechRecognizeUtil {

    @Value("${speech.recognition.topic}")
    private String TOPIC;

    @Value("${speech.recognition.uri}")
    private String SpeechRecognitionURI;

    @Value("${speech.recognition.api-key}")
    private String API_KEY;

    @Autowired
    private Map<String, String> dictionary;

    @Override
    public String convertSpeechToText(String filepath) {
        CustomFileReader fileReader = new CustomFileReader();
        byte[] bytes = fileReader.getBytesFromFile(filepath);
        String text = getString(bytes);

        return postProcess(text);
    }

    @Override
    public String convertSpeechToText(File file) throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());
        System.out.println(bytes);
        String text = getString(bytes);

        return postProcess(text);
    }

    @SneakyThrows
    public String getString(byte[] data){

        String res = "";
        try {
            String params =
                    "lang=ru-RU" +
                    "&topic=" + TOPIC +
                    "&format=lpcm" +
                    "&sampleRateHertz=16000";

            String uri = SpeechRecognitionURI + "?" + params;

            URL url = new URL(uri);

            HttpClient client = HttpClient.newBuilder()
                    .version(HttpClient.Version.HTTP_1_1)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(url.toURI())
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .header("Authorization", "Api-Key " + API_KEY)
                    .POST(HttpRequest.BodyPublishers.ofByteArray(data))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject obj = new JSONObject(response.body());
            res = obj.getString("result");

            System.out.println(res);
            return res;

        } catch (IOException | InterruptedException | URISyntaxException | JSONException e) {
            throw new SpeechRecognitionException(e);
        }
    }

    private String postProcess(String text){
        StringBuilder processed = new StringBuilder();
        String[] tokens = text.split(" ");

        for (int i = 0; i < tokens.length - 1; i++) {
            if (dictionary.containsKey(tokens[i].toLowerCase(Locale.ROOT) + " " + tokens[i+1].toLowerCase(Locale.ROOT))){
                processed.append(dictionary.get(tokens[i].toLowerCase(Locale.ROOT) + " " + tokens[i+1].toLowerCase(Locale.ROOT))).append(" ");
            }else if (dictionary.containsKey(tokens[i].toLowerCase(Locale.ROOT))){
                processed.append(dictionary.get(tokens[i].toLowerCase(Locale.ROOT))).append(" ");
            } else {
                processed.append(tokens[i]).append(" ");
                if (i == tokens.length - 2){
                    processed.append(tokens[i+1]);
                }
            }
        }

        System.out.println("string" + text);
        System.out.println("stringBuilder" + processed.toString());
        return processed.toString();
    }

}
