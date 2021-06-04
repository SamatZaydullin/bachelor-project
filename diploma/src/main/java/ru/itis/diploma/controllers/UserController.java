package ru.itis.diploma.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.diploma.dto.responses.UserResponseDto;
import ru.itis.diploma.models.User;
import ru.itis.diploma.services.UsersService;

import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/{:id}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable("id") Long userId){
        return ResponseEntity.ok(usersService.getOne(userId));
    }

    @GetMapping("/profile")
    @Secured("ROLE_USER")
    public ResponseEntity<UserResponseDto> getUserInfo(Authentication authentication){
        User current = (User) authentication.getPrincipal();
        UserResponseDto userResponseDto = UserResponseDto.from(current);
        System.out.println(userResponseDto.getFirstName());
        return ResponseEntity.ok(userResponseDto);
    }
}
