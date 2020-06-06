package ru.otus.component;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.service.StudentTestingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleComponent {
    private final StudentTestingService testingService;
    private final Scanner scanner;

    public void startTesting() {
        System.out.println("Hello! Please, write your name - ");
        final var studentName = scanner.nextLine();

        System.out.println(testingService.getExercisesString());

        showTestResult(studentName);
    }

    private void showTestResult(String studentName) {
        final var answers = getAnswers(testingService.getExercisesCount());

        final var isPassed = testingService.isPass(answers);

        if (isPassed) {
            System.out.println(String.format("Congratulations %s!", studentName));
        } else {
            System.out.println(String.format("Try again later %s!", studentName));
        }
    }

    public List<String> getAnswers(int questionsCount) {
        final var answers = new ArrayList<String>();

        for (int i = 1; i <= questionsCount; i++) {
            System.out.println(i + ": ");
            answers.add(scanner.nextLine());
        }

        return answers;
    }
}
