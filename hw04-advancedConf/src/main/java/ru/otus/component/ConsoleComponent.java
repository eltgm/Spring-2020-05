package ru.otus.component;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.configuration.LocaleProps;
import ru.otus.service.StudentTestingService;

@ShellComponent
@RequiredArgsConstructor
public class ConsoleComponent implements ConsoleInterface {
    private final StudentTestingService testingService;
    private final MessageSource messageSource;
    private final LocaleProps localeProps;
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Student") String userName) {
        this.userName = userName;
        return messageSource.getMessage("exam.welcome", new String[]{userName}, localeProps.getLocale());
    }

    @Override
    @ShellMethod(value = "Start exam", key = {"s", "start"})
    @ShellMethodAvailability(value = "isExamCommandAvailable")
    public void startTesting() {
        System.out.println(testingService.getExercisesString());

        showTestResult(userName);
    }

    private void showTestResult(String studentName) {
        final var answers = testingService.getAnswers();

        final var isPassed = testingService.isPass(answers);

        String message;
        if (isPassed) {
            message = messageSource.getMessage("exam.success", new String[]{studentName}, localeProps.getLocale());
        } else {
            message = messageSource.getMessage("exam.failed", new String[]{studentName}, localeProps.getLocale());
        }
        System.out.println(message);
    }

    private Availability isExamCommandAvailable() {
        return userName == null ? Availability.unavailable("Сначала залогиньтесь") : Availability.available();
    }
}
