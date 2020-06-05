package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.controller.ConsoleController;

import java.util.Scanner;

@ComponentScan
@Configuration
public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        final var consoleController = context.getBean(ConsoleController.class);

        consoleController.startTesting();
    }

    @Bean
    public Scanner getScanner() {
        return new Scanner(System.in);
    }
}