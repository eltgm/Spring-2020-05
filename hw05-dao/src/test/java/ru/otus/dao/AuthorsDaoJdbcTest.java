package ru.otus.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с авторами:")
@JdbcTest
@Import(AuthorsDaoJdbc.class)
class AuthorsDaoJdbcTest {
    @Autowired
    private AuthorsDaoJdbc authorsDaoJdbc;

    @DisplayName("должен добавить автора в бд")
    @Test
    void create() {
        var author = new Author(3, "George Raymond Richard Martin", null);

        authorsDaoJdbc.create(author);

        final var authors = authorsDaoJdbc.getAll();
        assertThat(authors).hasSize(3)
                .anyMatch(author1 -> author1.getName().equals("George Raymond Richard Martin") && author1.getId() == 3);
    }

    @DisplayName("должен вернуть всех авторов")
    @Test
    void getAll() {
        final var all = authorsDaoJdbc.getAll();

        assertThat(all).isNotNull().hasSize(2)
                .allMatch(author -> author.getId() != 0)
                .allMatch(author -> !author.getName().isEmpty());
    }
}