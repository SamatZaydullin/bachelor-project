package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itis.diploma.dto.responses.ThemeResponseDto;
import ru.itis.diploma.repositories.ThemesRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ThemesServiceImpl implements ThemesService {

    @Autowired
    private ThemesRepository themesRepository;

    @Override
    public List<ThemeResponseDto> getAll() {
        return themesRepository.findAll().stream()
                .map(theme ->
                        ThemeResponseDto.builder()
                                .id(theme.getId())
                                .title(theme.getTitle())
                                .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ThemeResponseDto> getAllBySpeciality(Long id) {
        return themesRepository.findAllBySpecialityId(id).stream()
                .map(theme ->
                        ThemeResponseDto.builder()
                                .id(theme.getId())
                                .title(theme.getTitle())
                                .build())
                .collect(Collectors.toList());
    }
}
