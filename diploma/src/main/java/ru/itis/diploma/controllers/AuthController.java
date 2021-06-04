package ru.itis.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.itis.diploma.dto.responses.JwtTokenDto;
import ru.itis.diploma.dto.requests.LoginDto;
import ru.itis.diploma.dto.requests.RegistrationDto;
import ru.itis.diploma.services.UsersService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/login")
    public ResponseEntity<JwtTokenDto> login(@RequestBody LoginDto loginDto){
        return ResponseEntity.ok(usersService.login(loginDto));
    }

    @PostMapping("/registration")
    public ResponseEntity<?> register(final @RequestBody RegistrationDto registrationDto){
        usersService.register(registrationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/activate")
    public ResponseEntity<?> confirmRegistration(final @RequestParam String confirm){
        usersService.confirmRegistration(confirm);
        return ResponseEntity.ok().build();
    }
}

