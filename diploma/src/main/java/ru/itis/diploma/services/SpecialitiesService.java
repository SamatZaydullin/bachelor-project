package ru.itis.diploma.services;

import ru.itis.diploma.dto.responses.SpecialityResponseDto;

import java.util.List;

public interface SpecialitiesService {
    List<SpecialityResponseDto> getSpecialities();
}
