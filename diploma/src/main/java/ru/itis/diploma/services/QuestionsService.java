package ru.itis.diploma.services;

import ru.itis.diploma.dto.requests.QuestionRequestDto;
import ru.itis.diploma.dto.responses.QuestionResponseDto;
import ru.itis.diploma.dto.responses.QuestionResponseMainPageDto;

import java.util.List;

public interface QuestionsService {
    void add(QuestionRequestDto questionDto);
    List<QuestionResponseDto> getAll();
    List<QuestionResponseMainPageDto> getAllForMainPage();
    QuestionResponseDto get(Long id);
}
