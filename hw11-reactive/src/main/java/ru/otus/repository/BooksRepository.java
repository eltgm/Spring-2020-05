package ru.otus.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.domain.Book;

@Repository
public interface BooksRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findAllByAuthor_Id(String authorId);
}
