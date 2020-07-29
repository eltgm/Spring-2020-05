package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.service.AuthorsService;
import ru.otus.service.BooksService;

@Controller
@RequiredArgsConstructor
public class AuthorControllerImpl implements AuthorController {
    private final AuthorsService authorsService;
    private final BooksService booksService;

    @Override
    @GetMapping("/author")
    public String showAllAuthors(Model model) {
        model.addAttribute("authors", authorsService.getAllAuthors());
        return "authors";
    }

    @Override
    @GetMapping("/author/book")
    public String showAllAuthorBooks(Model model, @RequestParam(name = "authorId") String authorId) {
        model.addAttribute("books", booksService.getAllBooksByAuthor(authorId));
        return "authorBooks";
    }
}
