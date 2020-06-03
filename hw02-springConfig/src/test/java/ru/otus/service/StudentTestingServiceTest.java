package ru.otus.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.otus.dao.ExercisesDAO;
import ru.otus.domain.Exercise;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentTestingServiceTest {
    private static final ExercisesDAO exercisesDAO = mock(ExercisesDAO.class);
    private static StudentTestingService studentTestingService;

    @BeforeAll
    public static void init() {
        when(exercisesDAO.getAllExercises()).then((Answer<List<Exercise>>) invocationOnMock ->
                Collections.singletonList(new Exercise("2+2",
                        List.of(new ru.otus.domain.Answer("4", true),
                                new ru.otus.domain.Answer("5", false)))));

        studentTestingService = new StudentTestingServiceImpl(exercisesDAO, 1);
    }

    @DisplayName("Тест получения отформатированной строки с вопросами из теста")
    @Test
    void getExercisesString() {
        final var exercisesString = studentTestingService.getExercisesString();

        assertNotEquals("Failed to load questions!", exercisesString);
    }

    @DisplayName("Тест получения количества вопросов")
    @Test
    void getExercisesCount() {
        final var exercisesCount = studentTestingService.getExercisesCount();

        assertEquals(1, exercisesCount);
    }

    @DisplayName("Тест получения положительного результата тестирования")
    @Test
    void isPass() {
        final var result = studentTestingService.isPass(List.of("a"));

        assertTrue(result);
    }

    @DisplayName("Тест получения отрицательного результата тестирования")
    @Test
    void notPass() {
        final var result = studentTestingService.isPass(List.of("b"));

        assertFalse(result);
    }
}