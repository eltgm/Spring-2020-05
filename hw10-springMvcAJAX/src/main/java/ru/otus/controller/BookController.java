package ru.otus.controller;

import ru.otus.domain.Book;
import ru.otus.domain.BookDto;

import java.util.List;

public interface BookController {
    List<Book> showAllBooks();

    Book showBookById(String id);

    void deleteBookById(String id);

    void createNewBook(BookDto book);

    void updateBook(BookDto book);
}
