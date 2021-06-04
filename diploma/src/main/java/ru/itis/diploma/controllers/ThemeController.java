package ru.itis.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.diploma.dto.requests.ThemeDto;
import ru.itis.diploma.dto.responses.ThemeResponseDto;
import ru.itis.diploma.services.ThemesService;

import java.util.List;

@RestController
@RequestMapping("/api/themes")
public class ThemeController {

    @Autowired
    private ThemesService themesService;

    @GetMapping
    public ResponseEntity<List<ThemeResponseDto>> getAll(){
        return ResponseEntity.ok(themesService.getAll());
    }

    @GetMapping("/speciality/{id}")
    public ResponseEntity<List<ThemeResponseDto>> getBySpeciality(@PathVariable Long id){
        return ResponseEntity.ok(themesService.getAllBySpeciality(id));
    }

    @PostMapping
    public ResponseEntity<ThemeDto> add(@RequestBody ThemeDto themeDto){
        return null;
    }
}
