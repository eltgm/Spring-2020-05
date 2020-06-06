package ru.otus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import ru.otus.component.ConsoleComponent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

@ComponentScan
@Configuration
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        final var consoleController = context.getBean(ConsoleComponent.class);

        consoleController.startTesting();
    }

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }

    @Bean
    public BufferedReader getBufferedReader(@Value("${questions.path}") String exercisesFileName) {
        return new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(exercisesFileName)));
    }
}