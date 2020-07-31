package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.domain.Book;
import ru.otus.domain.BookDto;
import ru.otus.service.BooksService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {
    private final BooksService booksService;

    @Override
    @GetMapping("/api/book")
    public List<Book> showAllBooks() {
        return booksService.getAllBooks();
    }

    @Override
    @GetMapping("/api/book/{id}")
    public Book showBookById(@PathVariable(name = "id") String id) {
        return booksService.getBookById(id);
    }

    @Override
    @DeleteMapping("/api/book/{id}")
    public void deleteBookById(@PathVariable(name = "id") String id) {
        booksService.deleteBookById(id);
    }

    @Override
    @PostMapping("/api/book")
    public void createNewBook(BookDto book) {
        booksService.createNewBook(book.toPojo());
    }

    @Override
    @PutMapping("/api/book")
    public void updateBook(BookDto book) {
        booksService.updateBook(book.toPojo());
    }
}
