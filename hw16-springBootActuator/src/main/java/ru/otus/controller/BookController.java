package ru.otus.controller;

import org.springframework.ui.Model;
import ru.otus.domain.Book;

public interface BookController {
    String showAllBooks(Model model, String id);

    String deleteBookById(Model model, String id);

    String createNewBook(Book book);

    String updateBook(Model model, Book book);
}
