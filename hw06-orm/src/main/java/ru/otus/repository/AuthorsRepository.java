package ru.otus.repository;

import ru.otus.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorsRepository {
    void create(Author author);

    List<Author> getAll();

    Optional<Author> getById(long authorId);
}
