package ru.otus.component;

import java.util.List;

public interface ConsoleInterface {
    void startTesting();

    List<String> getAnswers(int questionsCount);
}
