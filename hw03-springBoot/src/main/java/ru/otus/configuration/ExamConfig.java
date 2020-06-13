package ru.otus.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

@Configuration
public class ExamConfig {

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Bean
    public BufferedReader getBufferedReader(YamlProps yamlProps, LocaleProps localeProps, MessageSource messageSource) {
        final var prefix = messageSource.getMessage("exam.filePrefix", null, localeProps.getLocale());
        return new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(yamlProps.getPath() + prefix)));
    }
}
