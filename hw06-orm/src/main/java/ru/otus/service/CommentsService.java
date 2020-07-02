package ru.otus.service;

import ru.otus.domain.Comment;

import java.util.List;

public interface CommentsService {
    List<Comment> getAllComments();
}
