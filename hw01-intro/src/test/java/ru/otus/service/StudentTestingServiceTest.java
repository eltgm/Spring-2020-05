package ru.otus.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import ru.otus.dao.ExercisesDAO;
import ru.otus.domain.Exercise;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentTestingServiceTest {
    private static final ExercisesDAO exercisesDAO = mock(ExercisesDAO.class);
    private static StudentTestingService studentTestingService;

    @BeforeAll
    public static void init() {
        when(exercisesDAO.getAllExercises()).then((Answer<List<Exercise>>) invocationOnMock ->
                Collections.singletonList(new Exercise("", Collections.emptyList())));

        studentTestingService = new StudentTestingServiceImpl(exercisesDAO);
    }

    @DisplayName("Тест получения отформатированной строки с вопросами из теста")
    @Test
    void getExercisesString() {
        final var exercisesString = studentTestingService.getExercisesString();

        assertNotEquals("Failed to load questions!", exercisesString);
    }
}