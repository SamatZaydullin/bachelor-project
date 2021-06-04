package ru.itis.diploma.services;

import ru.itis.diploma.dto.responses.EvaluationDto;

import java.util.List;

public interface EvaluationsService {
    List<EvaluationDto> getAll();
    List<EvaluationDto> getAllByInterviewId(String uuid);
}
