package ru.otus.component;

import java.util.List;

public interface ConsoleInterface {
    int startTesting();

    List<String> getAnswers(int questionsCount);
}
