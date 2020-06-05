package ru.otus.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.otus.dao.ExercisesDAO;
import ru.otus.domain.Exercise;
import ru.otus.service.StudentTestingService;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleControllerTest {
    private static final ExercisesDAO exercisesDAO = mock(ExercisesDAO.class);
    private static final StudentTestingService studentTestingService = mock(StudentTestingService.class);
    private static final Scanner scanner = mock(Scanner.class);
    private static ConsoleController consoleController;

    @BeforeAll
    public static void init() {
        when(exercisesDAO.getAllExercises()).then((Answer<List<Exercise>>) invocationOnMock ->
                Collections.singletonList(new Exercise("2+2",
                        List.of(new ru.otus.domain.Answer("4", true),
                                new ru.otus.domain.Answer("5", false)))));
        when(scanner.nextLine()).then((Answer<String>) invocationOnMock -> "Test");

        consoleController = new ConsoleController(studentTestingService, scanner);
    }

    @Test
    void getAnswers() {
        final var answers = consoleController.getAnswers(5);

        final var expectedValue = List.of("Test", "Test", "Test", "Test", "Test");

        assertArrayEquals(expectedValue.toArray(), answers.toArray());
    }
}