package ru.itis.diploma.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.diploma.models.Complexity;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionRequestDto {
    private Long specialityId;
    private String theme;
    private String text;
    private List<String> answers;
    private Complexity complexity;
}
