package ru.itis.diploma.dto.responses;

import lombok.Builder;
import lombok.Data;
import org.owasp.encoder.Encode;
import ru.itis.diploma.models.Role;
import ru.itis.diploma.models.User;

import java.util.Set;

@Data
@Builder(toBuilder = true)
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Set<Role> roles;
    private Boolean isEnabled;

    public static UserResponseDto from(final User user){
        return UserResponseDto.builder()
                .id(user.getId())
                .firstName(Encode.forHtml(user.getFirstName()))
                .lastName(Encode.forHtml(user.getLastName()))
                .roles(user.getRoles())
                .isEnabled(user.getIsEnabled())
                .build();
    }
}
