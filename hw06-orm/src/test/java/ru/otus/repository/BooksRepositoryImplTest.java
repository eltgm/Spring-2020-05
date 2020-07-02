package ru.otus.repository;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами:")
@DataJpaTest
@Import(BooksRepositoryImpl.class)
class BooksRepositoryImplTest {
    @Autowired
    private BooksRepositoryImpl booksRepository;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен добавить книгу в бд")
    @Test
    void create() {
        final var author = Author.builder()
                .name("Andrew Sapkowski")
                .build();
        final var genre = Genre.builder()
                .name("Aventure")
                .build();
        final var newBook = Book.builder()
                .id(4L)
                .name("Miecz przeznaczenia")
                .publishDate("1992")
                .author(author)
                .genre(genre)
                .build();

        booksRepository.create(newBook);
        final var book = em.find(Book.class, 4L);
        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Miecz przeznaczenia"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Aventure"));
    }

    @DisplayName("должен обновить книгу в бд")
    @Test
    void update() {
        var author = new Author(2L, "Andrew Sapkowski", null);
        var genre = new Genre(3L, "Fantasy", null);

        final var updatedBook = Book.builder()
                .id(3L)
                .name("Wiedźmin")
                .publishDate("1986")
                .author(author)
                .genre(genre)
                .build();
        booksRepository.update(updatedBook);

        final var book = em.find(Book.class, 3L);
        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Wiedźmin"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Fantasy"))
                .matches(book12 -> book12.getPublishDate().equals("1986"));
    }

    @DisplayName("должен вернуть книгу по id")
    @Test
    void getById() {
        final var book = booksRepository.getById(1);

        assertThat(book.get()).isNotNull()
                .matches(book1 -> book1.getName().equals("Animal Farm"))
                .matches(book1 -> book1.getAuthor().getName().equals("George Orwell"))
                .matches(book1 -> book1.getGenre().getName().equals("Satire"));
    }

    @DisplayName("должен вернуть все книги")
    @Test
    void getAll() {
        final var all = booksRepository.getAll();

        assertThat(all).isNotNull().hasSize(3)
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> !book.getName().isEmpty())
                .allMatch(book -> !book.getPublishDate().isEmpty())
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> !book.getComments().isEmpty());
    }

    @DisplayName("должен удалить книгу")
    @Test
    void deleteById() {
        booksRepository.deleteById(1);

        final var book = booksRepository.getById(1);

        assertThat(book.orElse(null)).isNull();
    }
}