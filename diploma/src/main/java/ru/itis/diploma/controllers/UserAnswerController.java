package ru.itis.diploma.controllers;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.itis.diploma.utils.PyDataTextAnalizer;
import ru.itis.diploma.utils.SpeechRecognizeUtil;
import ru.itis.diploma.utils.SpeechSynthesizeUtil;

import javax.sound.sampled.*;
import javax.sound.sampled.AudioFormat;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/audio")
public class UserAnswerController {

    @Autowired
    private SpeechSynthesizeUtil speechSynthesizeUtil;

    @Autowired
    private SpeechRecognizeUtil speechRecognizeUtil;

    @Autowired
    private PyDataTextAnalizer pyDataTextAnalizer;

    @Value("${audio.response.path}")
    private String audioResponsePath;

    @Value("${audio.recognize.path}")
    private String audioRecognizePath;

    @Value("${audio.compress.path}")
    private String audioCompressPath;

    @GetMapping(value = "/{uuid}")
    public ResponseEntity aut(@PathVariable String uuid) throws FileNotFoundException {
        File file = new File(String.format(audioResponsePath, uuid));

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(file.length())
                .body(resource);
    }

    @SneakyThrows
    @PostMapping(value = "/records", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> audio(@RequestBody MultipartFile audio) throws IOException {
        String destination = audioRecognizePath + UUID.randomUUID().toString() +".wav";
        File file = new File(destination);
        audio.transferTo(file);
        File dstFile = null;


        try {
            AudioFormat.Encoding defaultEncoding = AudioFormat.Encoding.PCM_SIGNED;
            float fDefaultSampleRate = 16000;
            int nDefaultSampleSizeInBits = 16;
            int nDefaultChannels = 1;
            int frameSize = 2;
            float frameRate= 16000;
            boolean bDefaultBigEndian = true;

            AudioFileFormat.Type defaultFileType = AudioFileFormat.Type.WAVE;

            dstFile = new File(audioCompressPath + UUID.randomUUID().toString() + ".wav");
            AudioFormat defaultFormat = new AudioFormat(
                    defaultEncoding,
                    fDefaultSampleRate,
                    nDefaultSampleSizeInBits,
                    nDefaultChannels,
                    frameSize,
                    frameRate,
                    bDefaultBigEndian
            );

            AudioFileFormat inputFileFormat = AudioSystem.getAudioFileFormat(file);
            AudioFormat inputFormat = inputFileFormat.getFormat();

            System.out.println(inputFormat.toString());
            if(inputFormat.matches(defaultFormat)){
                System.out.println("No Change");
            }
            AudioInputStream sourceStream = AudioSystem.getAudioInputStream(file);

            AudioInputStream stream = AudioSystem.getAudioInputStream(defaultFormat,sourceStream);
            AudioSystem.write(stream, defaultFileType, dstFile);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String recog = speechRecognizeUtil.convertSpeechToText(dstFile);
        file.delete();

        return ResponseEntity.ok(recog);
    }
}
