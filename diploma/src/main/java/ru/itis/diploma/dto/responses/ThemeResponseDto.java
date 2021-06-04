package ru.itis.diploma.dto.responses;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ThemeResponseDto {
    private Long id;
    private String title;
}
