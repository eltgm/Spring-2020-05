package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.BooksRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "books", fallbackMethod = "buildFallbackBooks")
    public List<Book> getAllBooks() {
        return booksRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "books", fallbackMethod = "buildFallbackBook")
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
    @HystrixCommand(commandKey = "books", fallbackMethod = "buildFallbackBooks")
    public List<Book> getAllBooksByAuthor(String authorId) {
        return booksRepository.findAllByAuthor_Id(authorId);
    }

    public List<Book> buildFallbackBooks() {
        return Collections.singletonList(Book.builder()
                .publishDate("publishDate")
                .genre(Genre.builder()
                        .name("genre")
                        .build())
                .author(Author.builder()
                        .name("author")
                        .build())
                .name("name")
                .build());
    }

    public Book buildFallbackBook() {
        return Book.builder()
                .publishDate("publishDate")
                .genre(Genre.builder()
                        .name("genre")
                        .build())
                .author(Author.builder()
                        .name("author")
                        .build())
                .name("name")
                .build();
    }
}
