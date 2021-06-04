package ru.itis.diploma.components;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Getter
@Setter
public class InterviewSettings {
    private Map<String, Integer> interviewAndQuestionCount = new HashMap<>();
    private Map<String, Set<Long>> interviewAndQuestionIds = new HashMap<>();

}
