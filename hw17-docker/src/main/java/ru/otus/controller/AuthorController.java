package ru.otus.controller;

import org.springframework.ui.Model;

public interface AuthorController {
    String showAllAuthors(Model model);

    String showAllAuthorBooks(Model model, String authorId);
}
