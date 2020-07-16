package ru.otus.service;

import ru.otus.domain.Book;

import java.util.List;

public interface BooksService {
    List<Book> getAllBooks();

    Book getBookById(String id);

    void deleteBookById(String id);

    void createNewBook(Book book);

    void updateBook(Book book);

    List<Book> getAllBooksByAuthor(String authorId);
}
