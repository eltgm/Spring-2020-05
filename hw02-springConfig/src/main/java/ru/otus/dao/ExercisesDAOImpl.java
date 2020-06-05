package ru.otus.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.otus.domain.Answer;
import ru.otus.domain.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class ExercisesDAOImpl implements ExercisesDAO {
    private static final String COMMA_DELIMITER = ",";
    private final String fileName;
    private final List<Exercise> exercises = new ArrayList<>();
    private boolean isInit = false;

    public ExercisesDAOImpl(@Value("${questions.path}") String exercisesFileName) {
        this.fileName = exercisesFileName;
    }

    @Override
    public List<Exercise> getAllExercises() {
        if (!isInit) {
            init();
        }

        return exercises;
    }

    public void init() {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        isInit = true;

        records.forEach(strings -> {
            final var answerStrings = strings.subList(1, strings.size());
            final var answers = new ArrayList<Answer>();

            for (int i = 0; i < answerStrings.size(); i++) {
                answers.add(new Answer(answerStrings.get(i), i == 0));
            }

            Collections.shuffle(answers);
            exercises.add(new Exercise(strings.get(0), answers));
        });
    }
}
