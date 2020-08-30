package ru.otus.controller;

import org.springframework.ui.Model;

public interface CommentController {
    String showComments(Model model, String bookId);
}
