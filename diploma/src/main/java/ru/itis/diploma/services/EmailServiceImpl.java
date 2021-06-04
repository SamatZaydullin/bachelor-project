package ru.itis.diploma.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import ru.itis.diploma.dto.MailDto;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    @Qualifier("customMailSender")
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;


    @Override
    public void sendMail(final MailDto mailDto) {
        try{
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Context context = new Context();
            context.setVariables(mailDto.getModel());
            String html = templateEngine.process(
                    mailDto.getTemplate(), context);
            helper.setTo(mailDto.getTo());
            helper.setText(html, true);
            helper.setSubject(mailDto.getSubject());
            helper.setFrom(mailDto.getFrom());
        }catch (Exception e){
            throw new MailSendException(e.getMessage());
        }
    }
}
