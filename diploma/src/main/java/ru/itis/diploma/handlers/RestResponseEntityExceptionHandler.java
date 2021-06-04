package ru.itis.diploma.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ru.itis.diploma.exceptions.SpeechRecognitionException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<Object> handleBadCredentialsException(BadCredentialsException ex, WebRequest request){
        String bodyOfResponse = "Incorrect login or password";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(SpeechRecognitionException.class)
    protected ResponseEntity<Object> handleSpeechRecognitionException(RuntimeException ex, WebRequest request){
        String bodyOfResponse = "Speech recognition error, please try again.";
        HttpHeaders httpHeaders = new HttpHeaders();
        Charset charset = StandardCharsets.UTF_8;
        List<Charset> charsets = new ArrayList<>(1);
        charsets.add(charset);
        httpHeaders.setAcceptCharset(charsets);
        return handleExceptionInternal(ex, bodyOfResponse,
                httpHeaders, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Object> handleRuntimeException(Exception ex, WebRequest request){
        String bodyOfResponse = "Something went wrong, please try again.";
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}

