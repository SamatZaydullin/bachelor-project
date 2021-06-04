package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.diploma.dto.requests.QuestionRequestDto;
import ru.itis.diploma.dto.responses.AnswerResponseDto;
import ru.itis.diploma.dto.responses.QuestionResponseDto;
import ru.itis.diploma.dto.responses.QuestionResponseMainPageDto;
import ru.itis.diploma.models.*;
import ru.itis.diploma.repositories.AnswersRepository;
import ru.itis.diploma.repositories.QuestionsRepository;
import ru.itis.diploma.repositories.SpecialitiesRepository;
import ru.itis.diploma.repositories.ThemesRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class QuestionsServiceImpl implements QuestionsService {

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private ThemesRepository themesRepository;

    @Autowired
    private AnswersRepository answersRepository;

    @Autowired
    private SpecialitiesRepository specialitiesRepository;

    @Override
    public void add(QuestionRequestDto questionDto) {
        Optional<Speciality> speciality = specialitiesRepository.findById(questionDto.getSpecialityId());
        Optional<Theme> theme = themesRepository.findThemeByTitle(questionDto.getTheme());
        if (!theme.isPresent()){
            theme = Optional.ofNullable(Theme.builder()
                    .speciality(speciality.get())
                    .title(questionDto.getTheme())
                    .build());
            theme = Optional.of(themesRepository.save(theme.get()));
        }
        List<Answer> answers = new ArrayList<>();
        for (String answerString: questionDto.getAnswers()) {
            if (isBlank(answerString)) continue;
            Answer answer = Answer.builder()
                    .description(answerString)
                    .build();
            answers.add(answersRepository.save(answer));
        }
        Question question = Question.builder()
                .theme(theme.get())
                .complexity(questionDto.getComplexity())
                .state(State.DRAFT)
                .text(questionDto.getText())
                .answers(answers)
                .build();
        questionsRepository.save(question);
    }

    @Override
    public List<QuestionResponseDto> getAll() {
        return questionsRepository.findAll().stream()
                .map(question -> QuestionResponseDto.builder()
                    .id(question.getId())
                    .text(question.getText())
                        .themeTitle(question.getTheme().getTitle())
                        .complexity(question.getComplexity().name())
                        .answers(
                                question.getAnswers().stream()
                                .map(answer -> AnswerResponseDto.builder()
                                    .id(answer.getId())
                                        .title(answer.getTitle())
                                        .description(answer.getDescription())
                                        .build()
                                ).collect(Collectors.toList())
                        ).build()
                ).collect(Collectors.toList());
    }

    @Override
    public List<QuestionResponseMainPageDto> getAllForMainPage() {
        return questionsRepository.findAll().stream()
                .map(question -> QuestionResponseMainPageDto.builder()
                        .id(question.getId())
                        .text(question.getText())
                        .themeTitle(question.getTheme().getTitle())
                        .complexity(question.getComplexity().name())
                        .answer(question.getAnswers().get(0).getDescription()).build()
                ).collect(Collectors.toList());
    }

    @Override
    public QuestionResponseDto get(Long id) {
        Question question = questionsRepository.findById(id).get();
        return QuestionResponseDto.builder()
                .id(question.getId())
                .text(question.getText())
                .themeTitle(question.getTheme().getTitle())
                .complexity(question.getComplexity().name())
                .answers(
                        question.getAnswers().stream()
                                .map(answer -> AnswerResponseDto.builder()
                                        .id(answer.getId())
                                        .title(answer.getTitle())
                                        .description(answer.getDescription())
                                        .build()
                                ).collect(Collectors.toList())
                ).build();
    }
}
