package ru.otus.service;

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
@Import(BooksServiceImpl.class)
class BooksServiceImplTest {
    @Autowired
    private BooksServiceImpl booksService;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен вернуть все книги")
    @Test
    void getAllBooks() {
        final var all = booksService.getAllBooks();

        assertThat(all).isNotNull().hasSize(3)
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> !book.getName().isEmpty())
                .allMatch(book -> !book.getPublishDate().isEmpty())
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> !book.getComments().isEmpty());
    }

    @DisplayName("должен вернуть книгу по id")
    @Test
    void getBookById() {
        final var book = booksService.getBookById(1);

        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Animal Farm"))
                .matches(book1 -> book1.getAuthor().getName().equals("George Orwell"))
                .matches(book1 -> book1.getGenre().getName().equals("Satire"));
    }

    @DisplayName("должен удалить книгу")
    @Test
    void deleteBookById() {
        booksService.deleteBookById(1);

        final var book = booksService.getBookById(1);

        assertThat(book).isEqualTo(Book.builder().build());
    }

    @DisplayName("должен добавить книгу в бд")
    @Test
    void createNewBook() {
        final var author = Author.builder()
                .id(2L)
                .build();
        final var genre = Genre.builder()
                .id(4L)
                .build();
        final var newBook = Book.builder()
                .id(4L)
                .name("Miecz przeznaczenia")
                .publishDate("1992")
                .author(author)
                .genre(genre)
                .build();

        booksService.createNewBook(newBook);
        final var book = em.find(Book.class, 4L);
        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Miecz przeznaczenia"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Aventure"));
    }

    @DisplayName("должен обновить книгу в бд")
    @Test
    void updateBook() {
        var author = new Author(2L, "Andrew Sapkowski", null);
        var genre = new Genre(3L, "Fantasy", null);

        final var updatedBook = Book.builder()
                .id(3L)
                .name("Wiedźmin")
                .publishDate("1986")
                .author(author)
                .genre(genre)
                .build();
        booksService.updateBook(updatedBook);

        final var book = em.find(Book.class, 3L);
        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Wiedźmin"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Fantasy"))
                .matches(book12 -> book12.getPublishDate().equals("1986"));
    }

    @DisplayName("должен получить все книги автора")
    @Test
    void getAllBooksByAuthor() {
        final var books = booksService.getAllBooksByAuthor(1);

        assertThat(books).hasSize(2)
                .anyMatch(book -> book.getName().equals("Animal Farm") && book.getPublishDate().equals("17.08.1945"))
                .anyMatch(book -> book.getName().equals("Nineteen Eighty-Four") && book.getPublishDate().equals("8.06.1949"));
    }
}