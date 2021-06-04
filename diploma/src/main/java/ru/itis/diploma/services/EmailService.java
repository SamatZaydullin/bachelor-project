package ru.itis.diploma.services;

import ru.itis.diploma.dto.MailDto;

public interface EmailService {
    void sendMail(MailDto mailDto);
}
