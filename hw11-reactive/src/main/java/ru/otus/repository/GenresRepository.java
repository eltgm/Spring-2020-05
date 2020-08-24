package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.domain.Genre;

public interface GenresRepository extends ReactiveMongoRepository<Genre, String> {
}
