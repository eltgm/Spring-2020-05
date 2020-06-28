package ru.otus.dao;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Dao для работы с книгами:")
@JdbcTest
@Import(BooksDaoJdbc.class)
class BooksDaoJdbcTest {
    @Autowired
    private BooksDaoJdbc booksDaoJdbc;

    @DisplayName("должен добавить книгу в бд")
    @Test
    void create() {
        var author = new Author(2, "Andrew Sapkowski", null);
        var genre = new Genre(4, "Aventure", null);
        booksDaoJdbc.create(new Book(4, "Miecz przeznaczenia", "1992", author, genre));

        final var book = booksDaoJdbc.getById(4);
        assertThat(book.get()).isNotNull()
                .matches(book1 -> book1.getName().equals("Miecz przeznaczenia"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Aventure"));
    }

    @DisplayName("должен обновить книгу в бд")
    @Test
    void update() {
        var author = new Author(2, "Andrew Sapkowski", null);
        var genre = new Genre(3, "Fantasy", null);
        booksDaoJdbc.update(new Book(3, "Wiedźmin", "1986", author, genre));

        final var book = booksDaoJdbc.getById(3);
        assertThat(book.get()).isNotNull()
                .matches(book1 -> book1.getName().equals("Wiedźmin"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Fantasy"))
                .matches(book12 -> book12.getPublishDate().equals("1986"));
    }

    @DisplayName("должен вернуть книгу по id")
    @Test
    void getById() {
        final var book = booksDaoJdbc.getById(1);

        assertThat(book.get()).isNotNull()
                .matches(book1 -> book1.getName().equals("Animal Farm"))
                .matches(book1 -> book1.getAuthor().getName().equals("George Orwell"))
                .matches(book1 -> book1.getGenre().getName().equals("Satire"));
    }

    @DisplayName("должен вернуть все книги")
    @Test
    void getAll() {
        final var all = booksDaoJdbc.getAll();

        assertThat(all).isNotNull().hasSize(3)
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> !book.getName().isEmpty())
                .allMatch(book -> !book.getPublishDate().isEmpty())
                .allMatch(book -> book.getGenre() != null);
    }

    @DisplayName("должен удалить книгу")
    @Test
    void deleteById() {
        booksDaoJdbc.deleteById(1);

        final var book = booksDaoJdbc.getById(1);

        assertThat(book.orElse(null)).isNull();
    }
}