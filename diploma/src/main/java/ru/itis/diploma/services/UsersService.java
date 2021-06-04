package ru.itis.diploma.services;

import ru.itis.diploma.dto.responses.JwtTokenDto;
import ru.itis.diploma.dto.requests.LoginDto;
import ru.itis.diploma.dto.requests.RegistrationDto;
import ru.itis.diploma.dto.responses.UserResponseDto;
import ru.itis.diploma.models.User;

public interface UsersService {
    JwtTokenDto login(LoginDto loginDto);
    void register(RegistrationDto registrationDto);
    void changePassword(User user, String newPassword);
    void confirmRegistration(String confirm);
    UserResponseDto getOne(Long id);
}
