package ru.otus.dao;

import ru.otus.domain.Exercise;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExercisesDAOImpl implements ExercisesDAO {
    private final String fileName;
    private boolean isInit = false;
    private static final String COMMA_DELIMITER = ",";
    private final List<Exercise> exercises = new ArrayList<>();

    public ExercisesDAOImpl(String exercisesFileName) {
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
        records.forEach(strings -> exercises.add(new Exercise(strings.get(0), strings.subList(1, strings.size()))));
    }
}
