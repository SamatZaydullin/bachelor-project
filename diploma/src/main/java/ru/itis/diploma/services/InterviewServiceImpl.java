package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.diploma.components.InterviewSettings;
import ru.itis.diploma.dto.requests.InterviewDto;
import ru.itis.diploma.dto.requests.UserAnswerDto;
import ru.itis.diploma.dto.responses.InterviewInfoResponseDto;
import ru.itis.diploma.dto.responses.InterviewResponseDto;
import ru.itis.diploma.models.*;
import ru.itis.diploma.repositories.EvaluationsRepository;
import ru.itis.diploma.repositories.InterviewsRepository;
import ru.itis.diploma.repositories.QuestionsRepository;
import ru.itis.diploma.repositories.ThemesRepository;
import ru.itis.diploma.utils.PyDataTextAnalizer;
import ru.itis.diploma.utils.SpeechSynthesizeUtil;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class InterviewServiceImpl implements InterviewService {

    @Autowired
    private InterviewSettings interviewSettings;

    @Autowired
    private InterviewsRepository interviewsRepository;

    @Autowired
    private ThemesRepository themesRepository;

    @Autowired
    private EvaluationsRepository evaluationsRepository;

    @Autowired
    private QuestionsRepository questionsRepository;

    @Autowired
    private PyDataTextAnalizer pyDataTextAnalizer;

    @Autowired
    private SpeechSynthesizeUtil speechSynthesizeUtil;

    @Override
    public List<InterviewInfoResponseDto> getAll(User user) {
        DateTimeFormatter formatter =
                DateTimeFormatter.ofLocalizedDateTime( FormatStyle.FULL )
                        .withLocale( Locale.forLanguageTag("RU") )
                        .withZone( ZoneId.systemDefault() );

        return interviewsRepository.findAllByUserIdOrderByDateTimeDesc(user.getId()).stream()
                .map(interview ->
                    InterviewInfoResponseDto.builder()
                    .dateTime(formatter.format(interview.getDateTime()))
                    .interviewUUID(interview.getInterviewUUID())
                    .veryBadScoreCount(
                        interview.getEvaluations().stream()
                                .filter(evaluation ->
                                        evaluation.getAnswerScore().equals(AnswerScore.VERY_BAD)
                                ).count()
                    ).badScoreCount(
                        interview.getEvaluations().stream()
                                .filter(evaluation ->
                                        evaluation.getAnswerScore().equals(AnswerScore.BAD)
                                ).count()
                    ).goodScoreCount(
                        interview.getEvaluations().stream()
                                .filter(evaluation ->
                                        evaluation.getAnswerScore().equals(AnswerScore.GOOD)
                                ).count()
                    ).excellentScoreCount(
                        interview.getEvaluations().stream()
                                .filter(evaluation ->
                                        evaluation.getAnswerScore().equals(AnswerScore.EXCELLENT)
                                ).count()
                    ).questionCount(interview.getQuestionCount()).build())
                .collect(Collectors.toList());
    }

    @Override
    public String createNew(InterviewDto interviewDto, User user) {
        String interviewUUID = UUID.randomUUID().toString();
        List<Theme> themes = themesRepository.findAllById(interviewDto.getThemes());
        Interview interview = Interview.builder()
                .interviewUUID(interviewUUID)
                .questionCount(interviewDto.getQuestionsCount())
                .themes(themes)
                .dateTime(Instant.now())
                .user(user)
                .build();
        interviewSettings.getInterviewAndQuestionCount().put(interviewUUID, interviewDto.getQuestionsCount());
        interviewSettings.getInterviewAndQuestionIds().put(interviewUUID, new HashSet<>());
        interviewsRepository.save(interview);

        return interviewUUID;
    }

    @Override
    public void evaluate(String uuid, UserAnswerDto userAnswerDto, User user) {
        Interview interview = interviewsRepository.findById(uuid).get();
        Question question = questionsRepository.findById(userAnswerDto.getQuestionId()).get();
        UserAnswer userAnswer = UserAnswer.builder()
                .user(user)
                .recognizedSpeech(userAnswerDto.getAnswerText())
                .build();

        double result = pyDataTextAnalizer.findSimilarity(userAnswerDto.getAnswerText(), userAnswerDto.getQuestionId());

        Evaluation evaluation = Evaluation.builder()
                .interview(interview)
                .question(question)
                .answerScore(AnswerScore.get(Math.round(result*10)))
                .userAnswer(userAnswer)
                .build();

        evaluationsRepository.save(evaluation);
    }

    @Override
    public InterviewResponseDto getQuestion(String uuid) {
        Interview interview = interviewsRepository.findById(uuid).get();
        int count = interviewSettings.getInterviewAndQuestionCount().get(uuid);
        count--;
        interviewSettings.getInterviewAndQuestionCount().put(uuid, count);

        Set<Long> questionIds = interviewSettings.getInterviewAndQuestionIds().get(uuid);

        List<Theme> themes = interview.getThemes();
        int themesCount = themes.size();
        Long questionId;
        String questionText;
        do {
            List<Question> questions = themes.get(new Random().nextInt(themesCount)).getQuestions();
            int questionsSize = questions.size();
            Question question = questions.get(new Random().nextInt(questionsSize));
            questionText = question.getText();
            questionId = question.getId();
        } while (questionIds.contains(questionId));

        questionIds.add(questionId);
        interviewSettings.getInterviewAndQuestionIds().put(uuid, questionIds);

        String audioId = speechSynthesizeUtil.synthesizeVoice(questionText);


        InterviewResponseDto interviewResponseDto = InterviewResponseDto.builder()
                .questionCount(count)
                .questionText(questionText)
                .questionId(questionId)
                .audioId(audioId)
                .build();

        return interviewResponseDto;

    }
}
