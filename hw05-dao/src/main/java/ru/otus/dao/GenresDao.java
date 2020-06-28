package ru.otus.dao;

import ru.otus.domain.Genre;

import java.util.List;

public interface GenresDao {
    void create(Genre genre);

    List<Genre> getAll();
}
