package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с жанрами:")
@JdbcTest
@Import(GenresDaoJdbc.class)
class GenresDaoJdbcTest {
    @Autowired
    private GenresDaoJdbc genresDaoJdbc;

    @DisplayName("должен добавить жанр в бд")
    @Test
    void create() {
        var genre = new Genre(5, "Cyberpunk", null);

        genresDaoJdbc.create(genre);

        final var genres = genresDaoJdbc.getAll();
        assertThat(genres).hasSize(5)
                .anyMatch(genre1 -> genre1.getName().equals("Cyberpunk") && genre1.getId() == 5);
    }

    @DisplayName("должен вернуть все жанры")
    @Test
    void getAll() {
        final var all = genresDaoJdbc.getAll();

        assertThat(all).isNotNull().hasSize(4)
                .allMatch(genre -> genre.getId() != 0)
                .allMatch(genre -> !genre.getName().isEmpty());
    }
}