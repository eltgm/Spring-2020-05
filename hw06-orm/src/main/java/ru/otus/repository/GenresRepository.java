package ru.otus.repository;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenresRepository {
    void create(Genre genre);

    List<Genre> getAll();
}
