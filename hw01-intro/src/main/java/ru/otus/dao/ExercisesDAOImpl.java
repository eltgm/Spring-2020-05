package ru.otus.dao;

import ru.otus.domain.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExercisesDAOImpl implements ExercisesDAO {
    private static final String COMMA_DELIMITER = ",";
    private final List<Exercise> exercises = new ArrayList<>();

    public ExercisesDAOImpl(String exercisesFileName) {
        List<List<String>> records = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(exercisesFileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        records.forEach(strings -> exercises.add(new Exercise(strings.get(0), strings.subList(1, strings.size()))));
    }

    @Override
    public List<Exercise> getAllExercises() {
        return exercises;
    }
}
