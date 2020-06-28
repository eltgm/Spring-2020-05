package ru.otus.component;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.BooksService;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class ConsoleComponent implements ConsoleInterface {
    private final BooksService booksService;
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Librarian") String userName) {
        this.userName = userName;
        return "Hello, " + userName;
    }

    @ShellMethod(value = "Show all available books", key = {"s-a", "show-all"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void showAllBooks() {
        System.out.println(Arrays.deepToString(booksService.getAllBooks().toArray()));
    }

    @ShellMethod(value = "Show book by id if exist", key = {"s", "show"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void showBookById(@ShellOption long id) {
        System.out.println(booksService.getBookById(id));
    }

    @ShellMethod(value = "Delete book by id if exist", key = {"d", "delete"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void deleteBookById(@ShellOption long id) {
        booksService.deleteBookById(id);

        System.out.println("Success deleted!");
    }

    @ShellMethod(value = "Create new book", key = {"c", "create"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void createNewBook(@ShellOption String bookName, @ShellOption String publishDate,
                              @ShellOption long authorId, @ShellOption long genreId) {
        var author = new Author(authorId, null, null);
        var genre = new Genre(genreId, null, null);
        booksService.createNewBook(Book.builder()
                .name(bookName)
                .author(author)
                .genre(genre)
                .publishDate(publishDate)
                .build());

        System.out.println("Success created!");
    }

    @ShellMethod(value = "Update book", key = {"u", "update"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void updateBook(@ShellOption long bookId, @ShellOption String bookName,
                           @ShellOption String publishDate, @ShellOption long authorId, @ShellOption long genreId) {
        var author = new Author(authorId, null, null);
        var genre = new Genre(genreId, null, null);
        booksService.updateBook(Book.builder()
                .id(bookId)
                .author(author)
                .genre(genre)
                .name(bookName)
                .publishDate(publishDate)
                .build());

        System.out.println("Success updated!");
    }

    private Availability isLibraryCommandAvailable() {
        return userName == null ? Availability.unavailable("Login first") : Availability.available();
    }
}
