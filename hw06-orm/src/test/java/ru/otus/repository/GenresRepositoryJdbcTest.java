package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Genre;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с жанрами:")
@DataJpaTest
@Import(GenresRepositoryImpl.class)
class GenresRepositoryJdbcTest {
    @Autowired
    private GenresRepositoryImpl genresRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен добавить жанр в бд")
    @Test
    void create() {
        var genre = Genre.builder()
                .name("Cyberpunk")
                .build();

        genresRepository.create(genre);

        final var genres = em.find(Genre.class, genre.getId());
        assertThat(genres)
                .matches(genre1 -> genre1.getName().equals("Cyberpunk"));
    }

    @DisplayName("должен вернуть все жанры")
    @Test
    void getAll() {
        final var all = genresRepository.getAll();

        assertThat(all).isNotNull().hasSize(4)
                .allMatch(genre -> genre.getId() != 0)
                .allMatch(genre -> !genre.getName().isEmpty());
    }
}