package ru.itis.diploma.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InterviewResponseDto {
    private String audioId;
    private String questionText;
    private Long questionId;
    private Integer questionCount;
}
