package ru.otus;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.service.StudentTestingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@ComponentScan
@Configuration
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        final var testingService = context.getBean(StudentTestingService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello! Please, write your name - ");
        final var studentName = scanner.nextLine();

        System.out.println(testingService.getExercisesString());

        showTestResult(testingService, studentName);
    }

    private static void showTestResult(StudentTestingService testingService, String studentName) {
        final var answers = getAnswers(testingService.getExercisesCount());

        final var isPassed = testingService.isPass(answers);

        if (isPassed) {
            System.out.println(String.format("Congratulations %s!", studentName));
        } else {
            System.out.println(String.format("Try again later %s!", studentName));
        }
    }

    private static List<String> getAnswers(int questionsCount) {
        Scanner scanner = new Scanner(System.in);
        final var answers = new ArrayList<String>();

        for (int i = 1; i <= questionsCount; i++) {
            System.out.println(i + ": ");
            answers.add(scanner.nextLine());
        }

        return answers;
    }
}