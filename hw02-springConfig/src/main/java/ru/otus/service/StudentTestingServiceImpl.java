package ru.otus.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import ru.otus.dao.ExercisesDao;
import ru.otus.domain.Answer;
import ru.otus.domain.Exercise;
import ru.otus.exception.NotCorrectException;

import java.util.List;

@Service
@PropertySource("classpath:application.properties")
public class StudentTestingServiceImpl implements StudentTestingService {
    private static final int CHAR_OFFSET = 97;
    private final ExercisesDao exercisesDAO;
    private final int toPass;

    public StudentTestingServiceImpl(ExercisesDao exercisesDAO, @Value("${questions.toPass}") int toPass) {
        this.exercisesDAO = exercisesDAO;
        this.toPass = toPass;
    }

    @Override
    public String getExercisesString() {
        final var allExercises = exercisesDAO.getAllExercises();
        final var stringBuilder = new StringBuilder();

        stringBuilder.append("\n");
        for (Exercise exercise : allExercises) {
            final var answers = exercise.getAnswers();
            if (answers.size() == 1) {
                stringBuilder.append(String.format("Write: %s%n ", exercise.getQuestion()));
            } else {
                stringBuilder.append(String.format("Choose: %s%n", exercise.getQuestion()));

                var answerNumber = 'a';
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
    public int getExercisesCount() {
        return exercisesDAO.getAllExercises().size();
    }

    @Override
    public boolean isPass(List<String> answers) {
        final var exercises = exercisesDAO.getAllExercises();
        int fails = 0;
        final var answersCount = exercises.size();
        if (answersCount != answers.size()) {
            throw new NotCorrectException("Answers not correct!");
        }

        for (int i = 0; i < answersCount; i++) {
            final var exercise = exercises.get(i);
            final var exerciseSize = exercise.getAnswers().size();

            if (exerciseSize > 1) {
                final var answerVariant = (answers.get(i).toCharArray())[0] - CHAR_OFFSET;

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
        System.out.println(String.format("Result: %d of %d", rightAnswers, answersCount));

        return rightAnswers >= toPass;
    }
}
