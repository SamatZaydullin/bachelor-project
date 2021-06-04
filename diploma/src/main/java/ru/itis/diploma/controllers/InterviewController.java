package ru.itis.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.itis.diploma.dto.requests.InterviewDto;
import ru.itis.diploma.dto.requests.UserAnswerDto;
import ru.itis.diploma.dto.responses.InterviewInfoResponseDto;
import ru.itis.diploma.dto.responses.InterviewResponseDto;
import ru.itis.diploma.models.User;
import ru.itis.diploma.services.InterviewService;

import java.util.List;

@RestController
@RequestMapping("/api/interviews")
public class InterviewController {

    @Autowired
    private InterviewService interviewService;

    @GetMapping
    public ResponseEntity<List<InterviewInfoResponseDto>> getAll(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(interviewService.getAll(user));
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<InterviewResponseDto> getQuestion(@PathVariable String uuid){
        return ResponseEntity.ok(interviewService.getQuestion(uuid));
    }

    @PutMapping("/{uuid}")
    public ResponseEntity putAndGetNext(@PathVariable String uuid, @RequestBody UserAnswerDto userAnswerDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();

        interviewService.evaluate(uuid, userAnswerDto, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<String> createInterview(@RequestBody InterviewDto interviewDto, Authentication authentication){
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(interviewService.createNew(interviewDto, user));
    }


}
