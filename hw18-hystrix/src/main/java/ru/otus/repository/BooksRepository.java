package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.otus.domain.Book;

import java.util.List;

@Repository
public interface BooksRepository extends MongoRepository<Book, String> {
    List<Book> findAllByAuthor_Id(String authorId);
}
