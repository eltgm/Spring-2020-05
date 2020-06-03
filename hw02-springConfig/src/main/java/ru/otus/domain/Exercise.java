package ru.otus.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Exercise {
    private final String question;
    private final List<Answer> answers;
}
