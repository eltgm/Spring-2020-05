package ru.otus.controller;

import ru.otus.domain.Author;
import ru.otus.domain.Book;

import java.util.List;

public interface AuthorController {
    List<Author> showAllAuthors();

    List<Book> showAllAuthorBooks(String authorId);
}
