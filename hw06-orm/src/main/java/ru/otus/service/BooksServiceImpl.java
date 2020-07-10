package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.repository.AuthorsRepository;
import ru.otus.repository.BooksRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return booksRepository.getAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(long id) {
        return booksRepository.getById(id).orElseGet(() -> Book.builder().build());
    }

    @Override
    @Transactional
    public void deleteBookById(long id) {
        booksRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createNewBook(Book book) {
        final var id = booksRepository.getCount() + 1;
        book.setId(id);

        booksRepository.create(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        booksRepository.update(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByAuthor(long authorId) {
        return authorsRepository.getById(authorId).orElseGet(Author::new).getBooks();
    }
}
