package ru.otus.repository;

import ru.otus.domain.Comment;

import java.util.List;

public interface CommentsRepository {
    List<Comment> getAll();

    void create(Comment comment);

    void deleteById(long id);
}
