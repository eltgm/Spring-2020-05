package otus.component;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import ru.otus.component.ConsoleInterface;
import ru.otus.dao.ExercisesDao;
import ru.otus.domain.Exercise;

import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.Mockito.lenient;

@ExtendWith(MockitoExtension.class)
class ConsoleComponentTest {
    @Mock
    private static ExercisesDao exercisesDAO;
    @Mock
    private static Scanner scanner;
    @InjectMocks
    private static ConsoleInterface consoleComponent;

    @BeforeEach
    public void init() {
        lenient().when(exercisesDAO.getAllExercises()).then((Answer<List<Exercise>>) invocationOnMock ->
                Collections.singletonList(new Exercise("2+2",
                        List.of(new ru.otus.domain.Answer("4", true),
                                new ru.otus.domain.Answer("5", false)))));
        lenient().when(scanner.nextLine()).then((Answer<String>) invocationOnMock -> "Test");
    }

    @Test
    void getAnswers() {
        final var answers = consoleComponent.getAnswers(5);

        final var expectedValue = List.of("Test", "Test", "Test", "Test", "Test");

        assertArrayEquals(expectedValue.toArray(), answers.toArray());
    }
}