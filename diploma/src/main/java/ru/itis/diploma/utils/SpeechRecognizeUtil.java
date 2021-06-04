package ru.itis.diploma.utils;

import java.io.File;
import java.io.IOException;

public interface SpeechRecognizeUtil {
    String convertSpeechToText(String filepath);
    String convertSpeechToText(File file) throws IOException;
}
