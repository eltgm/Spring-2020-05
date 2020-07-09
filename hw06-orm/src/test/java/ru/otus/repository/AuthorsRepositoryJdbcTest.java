package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами:")
@DataJpaTest
@Import(AuthorsRepositoryImpl.class)
class AuthorsRepositoryJdbcTest {
    @Autowired
    private AuthorsRepositoryImpl authorsRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен добавить автора в бд")
    @Test
    void create() {
        var author = Author.builder()
                .name("George Raymond Richard Martin")
                .build();

        authorsRepository.create(author);

        final var authors = em.find(Author.class, author.getId());
        assertThat(authors)
                .matches(author1 -> author1.getName().equals("George Raymond Richard Martin"));
    }

    @DisplayName("должен вернуть всех авторов")
    @Test
    void getAll() {
        final var all = authorsRepository.getAll();

        assertThat(all).isNotNull().hasSize(2)
                .allMatch(author -> author.getId() != 0)
                .allMatch(author -> !author.getName().isEmpty())
                .allMatch(author -> !author.getBooks().isEmpty());
    }

    @DisplayName("должен вернуть книгу по authorId")
    @Test
    void getAllByAuthor() {
        final var books = authorsRepository.getById(1).get().getBooks();

        assertThat(books).hasSize(2)
                .anyMatch(book -> book.getName().equals("Animal Farm") && book.getPublishDate().equals("17.08.1945"))
                .anyMatch(book -> book.getName().equals("Nineteen Eighty-Four") && book.getPublishDate().equals("8.06.1949"));
    }
}