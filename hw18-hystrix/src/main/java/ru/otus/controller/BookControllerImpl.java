package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.otus.domain.Book;
import ru.otus.service.BooksService;

import java.util.Collections;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookControllerImpl implements BookController {
    private final BooksService booksService;

    @Override
    @GetMapping("/book")
    public String showAllBooks(Model model, @RequestParam(name = "id", required = false) String id) {
        List<Book> allBooks;
        if (!StringUtils.isEmpty(id)) {
            var book = booksService.getBookById(id);

            if (book.getId() == null) {
                allBooks = Collections.emptyList();
            } else {
                allBooks = Collections.singletonList(book);
            }

        } else {
            allBooks = booksService.getAllBooks();
        }

        model.addAttribute("books", allBooks);
        return "books";
    }

    @Override
    @GetMapping("/book/delete/{id}")
    public String deleteBookById(Model model, @PathVariable String id) {
        booksService.deleteBookById(id);
        return "redirect:/book";
    }

    @Override
    @GetMapping("/book/create")
    public String getNewBookPage(Model model) {
        model.addAttribute("book", new Book());
        return "createBook";
    }

    @Override
    @PostMapping("/book/create")
    public String createNewBook(Book book) {
        booksService.createNewBook(book);
        return "redirect:/book";
    }

    @Override
    @GetMapping("/book/edit")
    public String getUpdateBookPage(Model model, @RequestParam("id") String id) {
        final var book = booksService.getBookById(id);
        model.addAttribute("book", book);

        return "bookEdit";
    }

    @Override
    @PostMapping("/book/edit")
    public String updateBook(Model model, Book book) {
        booksService.updateBook(book);
        return "redirect:/book";
    }
}
