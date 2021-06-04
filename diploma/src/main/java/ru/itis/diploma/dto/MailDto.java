package ru.itis.diploma.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class MailDto {
    private String from;
    private String to;
    private String subject;
    private Map<String, Object> model;
    private String template;
}
