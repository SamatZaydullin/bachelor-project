package ru.itis.diploma.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseDto {
    private Long id;
    private String themeTitle;
    private String text;
    private List<AnswerResponseDto> answers;
    private String complexity;
}
