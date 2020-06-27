package ru.otus.dao;

import ru.otus.domain.Author;

import java.util.List;

public interface AuthorsDao {
    void create(Author author);

    List<Author> getAll();
}
