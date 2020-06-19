package otus.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.Main;
import ru.otus.configuration.YamlProps;
import ru.otus.dao.ExercisesDao;
import ru.otus.domain.Exercise;
import ru.otus.service.StudentTestingService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Main.class, properties = {
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false",
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
@ActiveProfiles("test")
class StudentTestingServiceTest {
    @MockBean
    private Scanner scanner;
    @MockBean
    private ExercisesDao exercisesDAO;
    @Autowired
    private StudentTestingService studentTestingService;

    @BeforeEach
    public void init() {
        when(exercisesDAO.getAllExercises()).then((Answer<List<Exercise>>) invocationOnMock ->
                Collections.singletonList(new Exercise("2+2",
                        List.of(new ru.otus.domain.Answer("4", true),
                                new ru.otus.domain.Answer("5", false)))));

        when(scanner.nextLine()).then((Answer<String>) invocationOnMock -> "Test");
        // when(exercisesDAO.getAllExercises().size()).then((Answer<Integer>) invocationOnMock -> 5);
    }

    @DisplayName("Тест получения отформатированной строки с вопросами из теста")
    @Test
    void getExercisesString() {
        final var exercisesString = studentTestingService.getExercisesString();

        assertNotEquals("Failed to load questions!", exercisesString);
    }

    @DisplayName("Тест получения положительного результата тестирования")
    @Test
    void isPass() {
        final var result = studentTestingService.isPass(List.of("а"));

        assertTrue(result);
    }

    @DisplayName("Тест получения отрицательного результата тестирования")
    @Test
    void notPass() {
        final var result = studentTestingService.isPass(List.of("б"));

        assertFalse(result);
    }

    @DisplayName("Тест получения ответов от пользователя")
    @Test
    void getAnswers() {
        final var answers = studentTestingService.getAnswers();

        final var expectedValue = List.of("Test");

        assertArrayEquals(expectedValue.toArray(), answers.toArray());
    }

    @TestConfiguration
    static class StudentTestConfiguration {
        @Bean
        public BufferedReader getBufferedReader(YamlProps yamlProps) {
            final var prefix = "_ru_RU.csv";

            return new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(yamlProps.getPath() + prefix)));
        }
    }
}