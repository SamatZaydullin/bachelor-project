package ru.itis.diploma.components;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Data
@Component
@ConfigurationProperties(prefix = "jwt.token")
public class JwtSettings {
    private String secret;
    private Long expiredMinutes;
    private String signingKey;
    private String signingValue;

    public void setSecret(String secret) {
        this.secret = Base64.getEncoder().encodeToString(
                secret.getBytes());
    }
}
