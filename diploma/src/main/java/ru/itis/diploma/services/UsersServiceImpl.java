package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.itis.diploma.components.JwtSettings;
import ru.itis.diploma.dto.*;
import ru.itis.diploma.dto.requests.LoginDto;
import ru.itis.diploma.dto.requests.RegistrationDto;
import ru.itis.diploma.dto.responses.JwtTokenDto;
import ru.itis.diploma.dto.responses.UserResponseDto;
import ru.itis.diploma.exceptions.RegistrationException;
import ru.itis.diploma.exceptions.UserConfirmException;
import ru.itis.diploma.exceptions.UserNotFoundException;
import ru.itis.diploma.models.Role;
import ru.itis.diploma.models.User;
import ru.itis.diploma.repositories.UsersRepository;
import ru.itis.diploma.security.details.UserDetailsImpl;
import ru.itis.diploma.security.details.UserDetailsServiceImpl;
import ru.itis.diploma.security.providers.JwtTokenProvider;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtSettings jwtSettings;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private final String EMAIL_SUBJECT = "Account activation link";


    @Override
    public JwtTokenDto login(final LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()));

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtTokenProvider.createToken(userDetails.getUser());
        return JwtTokenDto.builder()
                .token(token)
                .build();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void register(final RegistrationDto registrationDto) {

        Optional<User> userOptional =
                usersRepository.findByEmail(registrationDto.getEmail().toLowerCase());
        if (!userOptional.isPresent()){

            String confirmString = UUID.randomUUID().toString();

            User user = User.builder()
                    .email(registrationDto.getEmail())
                    .passwordHash(
                            encoder.encode(registrationDto.getPassword())
                    )
                    .firstName(registrationDto.getFirstName())
                    .lastName(registrationDto.getLastName())
                    .roles(Collections.singleton(Role.ROLE_USER))
                    .confirmString(confirmString)
                    .isEnabled(true)
                    .build();

            usersRepository.save(user);

            /*String url = String.format("%s://%s:%s",
                    request.getScheme(),
                    request.getServerName(),
                    request.getServerPort());

            Map<String, Object> model = new HashMap<>() {
                {
                    put("activationUrl", url + "/activate" + "?confirm=" + confirmString);
                }
            };

            MailDto mailDto = MailDto.builder()
                    .to(registrationDto.getEmail())
                    .from(mailFrom)
                    .model(model)
                    .subject(EMAIL_SUBJECT)
                    .template("account-activation")
                    .build();

            emailService.sendMail(mailDto);*/
        }else {
            throw new RegistrationException(registrationDto.toString());
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changePassword(final User user, final String newPassword) {
        user.setPasswordHash(encoder.encode(newPassword));
        usersRepository.save(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void confirmRegistration(final String confirm) {

        if (!confirm.equals("")){
            Optional<User> userOptional = usersRepository
                    .findByConfirmString(confirm);
            if (userOptional.isPresent()){
                User user = userOptional.get();
                user.setConfirmString("");
                user.setIsEnabled(true);
                usersRepository.save(user);
            }else {
                throw new UserConfirmException(confirm);
            }
        }else {
            throw new UserConfirmException(confirm);
        }
    }

    @Override
    public UserResponseDto getOne(final Long id) {
        Optional<User> userOptional = usersRepository.findById(id);
        if (userOptional.isPresent()){
            return UserResponseDto.from(userOptional.get());
        }else {
            throw new UserNotFoundException(id.toString());
        }
    }

    private String getClientIP() {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null){
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
}
