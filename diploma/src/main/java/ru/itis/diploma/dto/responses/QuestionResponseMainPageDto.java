package ru.itis.diploma.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseMainPageDto {
    private Long id;
    private String themeTitle;
    private String text;
    private String answer;
    private String complexity;
}
