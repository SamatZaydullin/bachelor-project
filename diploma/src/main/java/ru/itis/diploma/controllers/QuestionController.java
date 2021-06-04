package ru.itis.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.diploma.dto.requests.QuestionRequestDto;
import ru.itis.diploma.dto.responses.QuestionResponseDto;
import ru.itis.diploma.dto.responses.QuestionResponseMainPageDto;
import ru.itis.diploma.services.QuestionsService;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionsService questionsService;

    @GetMapping
    public ResponseEntity<List<QuestionResponseDto>> getAll(){
        return ResponseEntity.ok(questionsService.getAll());
    }
    @GetMapping("/l")
    public ResponseEntity<List<QuestionResponseMainPageDto>> getAllMainPage(){
        return ResponseEntity.ok(questionsService.getAllForMainPage());
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDto> get(@PathVariable Long id){
        return ResponseEntity.ok(questionsService.get(id));
    }

    @PostMapping
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequestDto questionDto){
        questionsService.add(questionDto);
        return ResponseEntity.ok().build();
    }
}
