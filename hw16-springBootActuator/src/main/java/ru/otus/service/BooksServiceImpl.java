package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.repository.BooksRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(String id) {
        return booksRepository.findById(id).orElseGet(() -> Book.builder().build());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void deleteBookById(String id) {
        booksRepository.deleteById(id);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Transactional
    public void createNewBook(Book book) {
        booksRepository.save(book);
    }

    @Override
    @PreAuthorize("hasPermission(#book, 'WRITE')")
    @Transactional
    public void updateBook(Book book) {
        booksRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByAuthor(String authorId) {
        return booksRepository.findAllByAuthor_Id(authorId);
    }
}
