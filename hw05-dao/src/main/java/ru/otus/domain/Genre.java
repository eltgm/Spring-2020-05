package ru.otus.domain;

import lombok.Data;

import java.util.List;

@Data
public class Genre {
    private final long id;
    private final String name;
    private final List<Book> books;
}
