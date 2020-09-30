package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Genre;

public interface GenresRepository extends MongoRepository<Genre, String> {
}
