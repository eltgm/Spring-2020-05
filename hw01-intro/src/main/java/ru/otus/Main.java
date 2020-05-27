package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.service.StudentTestingService;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("/spring-context.xml");

        final var testingService = context.getBean(StudentTestingService.class);

        System.out.println(testingService.getExercisesString());
    }
}