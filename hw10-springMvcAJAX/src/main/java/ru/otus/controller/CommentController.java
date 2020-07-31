package ru.otus.controller;

import ru.otus.domain.Comment;

import java.util.List;

public interface CommentController {
    List<Comment> showComments();

    List<Comment> showCommentsByBookId(String bookId);
}
