package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.repository.AuthorsRepository;
import ru.otus.repository.BooksRepository;
import ru.otus.repository.GenresRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {
    private final BooksRepository booksRepository;
    private final AuthorsRepository authorsRepository;
    private final GenresRepository genresRepository;

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
    @Transactional
    public void deleteBookById(String id) {
        booksRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void createNewBook(Book book) {
        final var genreOptional = genresRepository.findById(book.getGenre().getId());
        final var authorOptional = authorsRepository.findById(book.getAuthor().getId());
        final var author = authorOptional.orElseGet(Author::new);

        book.setAuthor(author);
        book.setGenre(genreOptional.orElseGet(Genre::new));

        booksRepository.save(book);
    }

    @Override
    @Transactional
    public void updateBook(Book book) {
        final var genreOptional = genresRepository.findById(book.getGenre().getId());
        final var authorOptional = authorsRepository.findById(book.getAuthor().getId());

        book.setAuthor(authorOptional.orElseGet(Author::new));
        book.setGenre(genreOptional.orElseGet(Genre::new));

        booksRepository.save(book);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooksByAuthor(String authorId) {
        return booksRepository.findAllByAuthor_Id(authorId);
    }
}
