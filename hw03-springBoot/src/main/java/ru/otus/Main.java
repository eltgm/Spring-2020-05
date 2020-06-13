package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.otus.component.ConsoleInterface;
import ru.otus.configuration.LocaleProps;
import ru.otus.configuration.YamlProps;

@SpringBootApplication
@EnableConfigurationProperties({YamlProps.class, LocaleProps.class})
public class Main {

    public static void main(String[] args) {
        final var context = SpringApplication.run(Main.class, args);

        final var consoleController = context.getBean(ConsoleInterface.class);
        final var result = consoleController.startTesting();

        System.exit(result);
    }
}