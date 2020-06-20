package ru.otus.service;

import java.util.List;

public interface StudentTestingService {
    String getExercisesString();

    boolean isPass(List<String> answers);

    List<String> getAnswers();
}
