package ru.otus.repository;

import ru.otus.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BooksRepository {
    void create(Book book);

    void update(Book book);

    Optional<Book> getById(long id);

    List<Book> getAll();

    void deleteById(long id);

    long getCount();
}
