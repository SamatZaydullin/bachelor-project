package ru.itis.diploma.controllers;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.diploma.components.InterviewSettings;
import ru.itis.diploma.dto.responses.EvaluationDto;
import ru.itis.diploma.services.EvaluationsService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluations")
public class EvaluationController {

    @Autowired
    private EvaluationsService evaluationsService;

    @Autowired
    private InterviewSettings interviewSettings;


    @GetMapping("/{uuid}")
    public ResponseEntity<List<EvaluationDto>> get(@PathVariable String uuid){
        if (StringUtils.isBlank(uuid)){
            return ResponseEntity.ok(evaluationsService.getAll());
        }else {
            if (!interviewSettings.getInterviewAndQuestionIds().isEmpty()
                    && !interviewSettings.getInterviewAndQuestionIds().get(uuid).isEmpty()){
                interviewSettings.getInterviewAndQuestionIds().remove(uuid);
            }
            if (!interviewSettings.getInterviewAndQuestionCount().isEmpty()
                    && interviewSettings.getInterviewAndQuestionCount().get(uuid) != null){
                interviewSettings.getInterviewAndQuestionCount().remove(uuid);
            }
            return ResponseEntity.ok(evaluationsService.getAllByInterviewId(uuid));
        }
    }
}
