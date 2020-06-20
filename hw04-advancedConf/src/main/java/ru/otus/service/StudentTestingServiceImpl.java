package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.configuration.LocaleProps;
import ru.otus.configuration.YamlProps;
import ru.otus.dao.ExercisesDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Exercise;
import ru.otus.exception.NotCorrectException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class StudentTestingServiceImpl implements StudentTestingService {
    private final ExercisesDao exercisesDAO;
    private final MessageSource messageSource;
    private final LocaleProps localeProps;
    private final YamlProps yamlProps;
    private final Scanner scanner;

    @Override
    public String getExercisesString() {
        final var allExercises = exercisesDAO.getAllExercises();
        final var stringBuilder = new StringBuilder();

        stringBuilder.append("\n");
        for (Exercise exercise : allExercises) {
            final var answers = exercise.getAnswers();
            String message;

            if (answers.size() == 1) {
                message = messageSource.getMessage("exam.write", new String[]{exercise.getQuestion()}, localeProps.getLocale());
                stringBuilder.append(message);
            } else {
                message = messageSource.getMessage("exam.choose", new String[]{exercise.getQuestion()}, localeProps.getLocale());
                stringBuilder.append(message);

                char answerNumber = messageSource.getMessage("exam.variantStart", null, localeProps.getLocale()).charAt(0);
                for (Answer answer : answers) {
                    stringBuilder.append(String.format("  %c. %s;%n", answerNumber, answer.getAnswerText()));
                    answerNumber++;
                }
            }

            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    @Override
    public boolean isPass(List<String> answers) {
        final var exercises = exercisesDAO.getAllExercises();
        int fails = 0;
        final var answersCount = exercises.size();
        if (answersCount != answers.size()) {
            throw new NotCorrectException(messageSource.getMessage("exam.answererror", null, localeProps.getLocale()));
        }

        for (int i = 0; i < answersCount; i++) {
            final var exercise = exercises.get(i);
            final var exerciseSize = exercise.getAnswers().size();

            if (exerciseSize > 1) {
                int charOffset = Integer.parseInt(messageSource.getMessage("exam.offset", null, localeProps.getLocale()));
                final var answerVariant = (answers.get(i).toCharArray())[0] - charOffset;

                if (!exercise.getAnswers().get(answerVariant).isRight()) {
                    fails++;
                }
            } else {
                if (!exercise.getAnswers().get(0).getAnswerText().equals(answers.get(i))) {
                    fails++;
                }
            }

        }
        final var rightAnswers = answersCount - fails;
        final var resultAnswer = messageSource.getMessage("exam.result", new String[]{String.valueOf(rightAnswers), String.valueOf(answersCount)}, localeProps.getLocale());
        System.out.println(resultAnswer);

        return rightAnswers >= yamlProps.getToPass();
    }

    @Override
    public List<String> getAnswers() {
        final var answers = new ArrayList<String>();
        final var questionsCount = getExercisesCount();

        for (int i = 1; i <= questionsCount; i++) {
            System.out.println(i + ": ");
            answers.add(scanner.nextLine());
        }

        return answers;
    }

    private int getExercisesCount() {
        return exercisesDAO.getAllExercises().size();
    }
}
