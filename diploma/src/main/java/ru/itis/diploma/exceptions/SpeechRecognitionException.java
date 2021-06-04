package ru.itis.diploma.exceptions;

public class SpeechRecognitionException extends RuntimeException{
    public SpeechRecognitionException() {
        super();
    }

    public SpeechRecognitionException(String message) {
        super(message);
    }

    public SpeechRecognitionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SpeechRecognitionException(Throwable cause) {
        super(cause);
    }
}
