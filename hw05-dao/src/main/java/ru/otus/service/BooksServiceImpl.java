package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.dao.BooksDao;
import ru.otus.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksDao booksDao;

    @Override
    public List<Book> getAllBooks() {
        return booksDao.getAll();
    }

    @Override
    public Book getBookById(long id) {
        return booksDao.getById(id).orElseGet(() -> Book.builder().build());
    }

    @Override
    public void deleteBookById(long id) {
        booksDao.deleteById(id);
    }

    @Override
    public void createNewBook(Book book) {
        final var id = booksDao.getCount() + 1;

        book.setId(id);
        booksDao.create(book);
    }

    @Override
    public void updateBook(Book book) {
        booksDao.update(book);
    }
}
