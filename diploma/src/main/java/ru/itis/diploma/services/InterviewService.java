package ru.itis.diploma.services;

import ru.itis.diploma.dto.requests.InterviewDto;
import ru.itis.diploma.dto.requests.UserAnswerDto;
import ru.itis.diploma.dto.responses.InterviewInfoResponseDto;
import ru.itis.diploma.dto.responses.InterviewResponseDto;
import ru.itis.diploma.models.User;

import java.util.List;

public interface InterviewService {
    List<InterviewInfoResponseDto> getAll(User user);
    String createNew(InterviewDto interviewDto, User user);
    void evaluate(String uuid, UserAnswerDto userAnswerDto, User user);
    InterviewResponseDto getQuestion(String uuid);
}
