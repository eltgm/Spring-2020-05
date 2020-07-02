package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorsRepository {
    void create(Author author);

    List<Author> getAll();
}
