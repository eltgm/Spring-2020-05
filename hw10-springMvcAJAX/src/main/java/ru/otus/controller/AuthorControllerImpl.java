package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.service.AuthorsService;
import ru.otus.service.BooksService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {
    private final AuthorsService authorsService;
    private final BooksService booksService;

    @Override
    @GetMapping("/api/author")
    public List<Author> showAllAuthors() {
        return authorsService.getAllAuthors();
    }

    @Override
    @GetMapping("/api/author/{authorId}/book")
    public List<Book> showAllAuthorBooks(@PathVariable(name = "authorId") String authorId) {
        return booksService.getAllBooksByAuthor(authorId);
    }
}
