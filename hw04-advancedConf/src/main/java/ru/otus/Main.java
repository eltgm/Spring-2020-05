package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.configuration.LocaleProps;
import ru.otus.configuration.YamlProps;

@SpringBootApplication
@EnableConfigurationProperties({YamlProps.class, LocaleProps.class})
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}