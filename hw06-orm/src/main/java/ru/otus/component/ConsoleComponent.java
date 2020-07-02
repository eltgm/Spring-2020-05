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
import ru.otus.service.CommentsService;

import java.util.Arrays;

@ShellComponent
@RequiredArgsConstructor
public class ConsoleComponent implements ConsoleInterface {
    private final CommentsService commentsService;
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
        final var author = Author.builder()
                .id(authorId)
                .build();
        final var genre = Genre.builder()
                .id(genreId)
                .build();
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
        final var author = Author.builder()
                .id(authorId)
                .build();
        final var genre = Genre.builder()
                .id(genreId)
                .build();
        booksService.updateBook(Book.builder()
                .id(bookId)
                .author(author)
                .genre(genre)
                .name(bookName)
                .publishDate(publishDate)
                .build());

        System.out.println("Success updated!");
    }

    @ShellMethod(value = "Show all comments", key = {"s-c", "show-comments"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void showAllComments() {
        System.out.println(Arrays.deepToString(commentsService.getAllComments().toArray()));
    }

    private Availability isLibraryCommandAvailable() {
        return userName == null ? Availability.unavailable("Login first") : Availability.available();
    }
}
