package ru.itis.diploma.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.diploma.models.Question;
import ru.itis.diploma.repositories.QuestionsRepository;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class PyDataTextAnalizerImpl implements PyDataTextAnalizer {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Value("url.similarity.calculation")
    private String similarityCalculationURL;

    @SneakyThrows
    @Override
    public Double findSimilarity(String userAnswer, Long questionId) {
        URL url = new URL(similarityCalculationURL);

        List<String> corrects = new ArrayList<>();
        Question question = questionsRepository.findById(questionId).get();
        question.getAnswers().stream()
                .forEach(answer -> corrects.add(answer.getDescription()));


        JsonElement jsonElement = new Gson().toJsonTree(corrects.toArray());

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("answer", userAnswer);
        jsonObject.add("correct", jsonElement);

        HttpClient client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(url.toURI())
                .POST(HttpRequest.BodyPublishers.ofByteArray(jsonObject.toString().getBytes(StandardCharsets.UTF_8)))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return Double.valueOf(response.body());
    }
}
