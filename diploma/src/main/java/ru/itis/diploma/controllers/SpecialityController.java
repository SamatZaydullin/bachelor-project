package ru.itis.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import ru.itis.diploma.dto.requests.SpecialityDto;
import ru.itis.diploma.dto.responses.SpecialityResponseDto;
import ru.itis.diploma.services.SpecialitiesService;

import java.util.List;

@RestController
@RequestMapping("/api/specialities")
public class SpecialityController {

    @Autowired
    private SpecialitiesService specialitiesService;

    @GetMapping
    public ResponseEntity<List<SpecialityResponseDto>> getAll(){
        return ResponseEntity.ok(specialitiesService.getSpecialities());
    }

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<SpecialityDto> add(@RequestBody SpecialityDto speciality){
        return null;
    }
}
