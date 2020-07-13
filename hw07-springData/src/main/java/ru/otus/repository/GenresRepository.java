package ru.otus.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.domain.Genre;

public interface GenresRepository extends JpaRepository<Genre, Long> {
}
