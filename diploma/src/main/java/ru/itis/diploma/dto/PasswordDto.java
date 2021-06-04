package ru.itis.diploma.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@Builder(toBuilder = true)
public class PasswordDto {

    @NotEmpty
    private String password;

    @NotEmpty
    @NotBlank
    private String confirmPassword;
}
