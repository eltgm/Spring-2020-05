package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BooksService {
    List<Book> getAllBooks();

    Book getBookById(long id);

    void deleteBookById(long id);

    void createNewBook(Book book);

    void updateBook(Book book);
}
