package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.diploma.dto.responses.SpecialityResponseDto;
import ru.itis.diploma.repositories.SpecialitiesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialitiesServiceImpl implements SpecialitiesService {

    @Autowired
    private SpecialitiesRepository specialitiesRepository;

    @Override
    public List<SpecialityResponseDto> getSpecialities() {
        return specialitiesRepository.findAll().stream()
                .map(speciality ->
                        SpecialityResponseDto.builder()
                                .id(speciality.getId())
                                .description(speciality.getDescription())
                                .title(speciality.getTitle())
                                .build())
                .collect(Collectors.toList());
    }
}
