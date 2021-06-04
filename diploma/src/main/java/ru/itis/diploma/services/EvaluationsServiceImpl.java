package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.diploma.dto.responses.EvaluationDto;
import ru.itis.diploma.repositories.EvaluationsRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EvaluationsServiceImpl implements EvaluationsService {

    @Autowired
    private EvaluationsRepository evaluationsRepository;

    @Override
    public List<EvaluationDto> getAll() {
        return evaluationsRepository.findAll().stream()
                .map(evaluation -> {
                    return EvaluationDto.builder()
                            .id(evaluation.getId())
                            .answerScore(evaluation.getAnswerScore().name())
                            .correctAnswer(evaluation.getQuestion().getAnswers().get(0).getDescription())
                            .questionText(evaluation.getQuestion().getText())
                            .theme(evaluation.getQuestion().getTheme().getTitle())
                            .userAnswerText(evaluation.getUserAnswer().getRecognizedSpeech())
                            .complexity(evaluation.getQuestion().getComplexity().name())
                            .build();
                }).collect(Collectors.toList());
    }

    @Override
    public List<EvaluationDto> getAllByInterviewId(String uuid) {
        return evaluationsRepository.findAllByInterviewInterviewUUID(uuid).stream()
                .map(evaluation -> {
                    return EvaluationDto.builder()
                            .id(evaluation.getId())
                            .answerScore(evaluation.getAnswerScore().name())
                            .correctAnswer(evaluation.getQuestion().getAnswers().get(0).getDescription())
                            .questionText(evaluation.getQuestion().getText())
                            .theme(evaluation.getQuestion().getTheme().getTitle())
                            .userAnswerText(evaluation.getUserAnswer().getRecognizedSpeech())
                            .complexity(evaluation.getQuestion().getComplexity().name())
                            .build();
                }).collect(Collectors.toList());
    }
}
