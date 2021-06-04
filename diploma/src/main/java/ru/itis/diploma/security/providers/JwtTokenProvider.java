package ru.itis.diploma.security.providers;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.itis.diploma.components.JwtSettings;
import ru.itis.diploma.exceptions.JwtAuthenticationException;
import ru.itis.diploma.models.User;
import ru.itis.diploma.security.details.UserDetailsImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtSettings jwtSettings;
    private final UserDetailsService userDetailsService;

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());
        claims.put("roles", user.getRoles());

        Date timeNow = getDateNow();

        Date timeExpiration = new Date(timeNow.getTime() +
                TimeUnit.MINUTES.toMillis(jwtSettings.getExpiredMinutes()));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(timeNow)
                .setExpiration(timeExpiration)
                .signWith(SignatureAlgorithm.HS512, jwtSettings.getSecret())
                .compact();
    }

    private Date getDateNow() {
        return new Date();
    }


    public Authentication getAuthentication(String token) {
        UserDetailsImpl userDetails =
                (UserDetailsImpl) this.userDetailsService.loadUserByUsername(
                        getUsername(token));
        return new UsernamePasswordAuthenticationToken(
                userDetails.getUser(),
                "",
                userDetails.getAuthorities());
    }


    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(jwtSettings.getSecret())
                .parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(jwtSettings.getSigningKey());

        if (bearerToken != null && bearerToken.startsWith(jwtSettings.getSigningValue())) {
            return bearerToken.substring(jwtSettings.getSigningValue().length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser()
                    .setSigningKey(jwtSettings.getSecret()).parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            //throw new JwtAuthenticationException("JWT token is expired or invalid");
            return false;
        }
    }
}
