package ru.otus.service;

import ru.otus.dao.ExercisesDAO;
import ru.otus.domain.Exercise;

public class StudentTestingServiceImpl implements StudentTestingService {
    private final ExercisesDAO exercisesDAO;

    public StudentTestingServiceImpl(ExercisesDAO exercisesDAO) {
        this.exercisesDAO = exercisesDAO;
    }

    @Override
    public String getExercisesString() {
        final var allExercises = exercisesDAO.getAllExercises();
        final var stringBuilder = new StringBuilder();

        stringBuilder.append("\n");
        if (!allExercises.isEmpty()) {
            for (Exercise exercise : allExercises) {
                final var answers = exercise.getAnswers();
                if (answers.size() == 1) {
                    stringBuilder.append(String.format("Write: %s%n ", exercise.getQuestion()));
                } else {
                    stringBuilder.append(String.format("Choose: %s%n", exercise.getQuestion()));

                    var answerNumber = 1;
                    for (String answer : answers) {
                        stringBuilder.append(String.format("  %d.%s;%n", answerNumber, answer));
                        answerNumber++;
                    }
                }

                stringBuilder.append("\n");
            }
        } else {
            stringBuilder.append("Failed to load questions!");
        }

        return stringBuilder.toString();
    }
}
