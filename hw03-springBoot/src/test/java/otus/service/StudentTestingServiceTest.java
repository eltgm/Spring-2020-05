package otus.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.context.MessageSource;
import ru.otus.configuration.LocaleProps;
import ru.otus.configuration.YamlProps;
import ru.otus.dao.ExercisesDao;
import ru.otus.domain.Exercise;
import ru.otus.service.StudentTestingService;
import ru.otus.service.StudentTestingServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentTestingServiceTest {
    private static final ExercisesDao exercisesDAO = mock(ExercisesDao.class);
    private static final MessageSource messageSource = mock(MessageSource.class);
    private static StudentTestingService studentTestingService;

    @BeforeAll
    public static void init() {
        when(exercisesDAO.getAllExercises()).then((Answer<List<Exercise>>) invocationOnMock ->
                Collections.singletonList(new Exercise("2+2",
                        List.of(new ru.otus.domain.Answer("4", true),
                                new ru.otus.domain.Answer("5", false)))));

        YamlProps yamlProps = new YamlProps();
        yamlProps.setToPass(1);
        LocaleProps localeProps = new LocaleProps();
        localeProps.setLocale(Locale.getDefault());
        studentTestingService = new StudentTestingServiceImpl(exercisesDAO, messageSource, localeProps, yamlProps);
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