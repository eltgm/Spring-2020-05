package ru.otus.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import ru.otus.configuration.LocaleProps;
import ru.otus.service.StudentTestingService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
@RequiredArgsConstructor
public class ConsoleComponent implements ConsoleInterface {
    private static final int STATUS_OK = 0;

    private final StudentTestingService testingService;
    private final Scanner scanner;
    private final MessageSource messageSource;
    private final LocaleProps localeProps;

    @Override
    public int startTesting() {
        var message = messageSource.getMessage("exam.userName", null, localeProps.getLocale());

        System.out.println(message);
        final var studentName = scanner.nextLine();

        System.out.println(testingService.getExercisesString());

        return showTestResult(studentName);
    }

    @Override
    public List<String> getAnswers(int questionsCount) {
        final var answers = new ArrayList<String>();

        for (int i = 1; i <= questionsCount; i++) {
            System.out.println(i + ": ");
            answers.add(scanner.nextLine());
        }

        return answers;
    }

    private int showTestResult(String studentName) {
        final var answers = getAnswers(testingService.getExercisesCount());

        final var isPassed = testingService.isPass(answers);

        String message;
        if (isPassed) {
            message = messageSource.getMessage("exam.success", new String[]{studentName}, localeProps.getLocale());

            System.out.println(message);
        } else {
            message = messageSource.getMessage("exam.failed", new String[]{studentName}, localeProps.getLocale());

            System.out.println(message);
        }

        return STATUS_OK;
    }
}
