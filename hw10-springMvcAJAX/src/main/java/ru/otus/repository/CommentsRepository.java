package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.Comment;

import java.util.List;

public interface CommentsRepository extends MongoRepository<Comment, String> {
    List<Comment> findAllByBookId(String bookId);
}
