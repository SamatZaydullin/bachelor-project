package ru.itis.diploma.services;

import ru.itis.diploma.dto.responses.ThemeResponseDto;

import java.util.List;

public interface ThemesService {
    List<ThemeResponseDto> getAll();
    List<ThemeResponseDto> getAllBySpeciality(Long id);

}
