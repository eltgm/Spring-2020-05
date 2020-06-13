package ru.otus.service;

import java.util.List;

public interface StudentTestingService {
    String getExercisesString();

    int getExercisesCount();

    boolean isPass(List<String> answers);
}
