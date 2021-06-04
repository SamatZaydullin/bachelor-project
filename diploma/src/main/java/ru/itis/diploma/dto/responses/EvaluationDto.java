package ru.itis.diploma.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.diploma.models.AnswerScore;
import ru.itis.diploma.models.Interview;
import ru.itis.diploma.models.Question;
import ru.itis.diploma.models.UserAnswer;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluationDto {

    private Long id;

    private String theme;

    private String userAnswerText;

    private String questionText;

    private String correctAnswer;

    private String answerScore;

    private String complexity;

}
