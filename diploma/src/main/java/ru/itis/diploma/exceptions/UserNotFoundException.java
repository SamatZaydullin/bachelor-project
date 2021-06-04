package ru.itis.diploma.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class UserNotFoundException extends BadCredentialsException {
    public UserNotFoundException(String s) {
        super(s);
    }
}
