package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.otus.domain.Author;

public interface AuthorsRepository extends ReactiveMongoRepository<Author, String> {

}
