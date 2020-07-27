package ru.otus.component;

import lombok.RequiredArgsConstructor;
import ru.otus.service.BooksService;
import ru.otus.service.CommentsService;

//@ShellComponent
@RequiredArgsConstructor
public class ConsoleComponent implements ConsoleInterface {
    private final CommentsService commentsService;
    private final BooksService booksService;
    private String userName;

    @Override
    public void showAllBooks() {

    }

    @Override
    public void showBookById(String id) {

    }

    @Override
    public void deleteBookById(String id) {

    }

    @Override
    public void createNewBook(String bookName, String publishDate, String authorId, String genreId) {

    }

    @Override
    public void updateBook(String bookId, String bookName, String publishDate, String authorId, String genreId) {

    }

    @Override
    public void showAllComments() {

    }

    @Override
    public void showAllCommentsByBook(String bookId) {

    }

    @Override
    public void showAllAuthorBooks(String authorId) {

    }

    /*@ShellMethod(value = "Login command", key = {"l", "login"})
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
    public void showBookById(@ShellOption String id) {
        System.out.println(booksService.getBookById(id));
    }

    @ShellMethod(value = "Delete book by id if exist", key = {"d", "delete"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void deleteBookById(@ShellOption String id) {
        booksService.deleteBookById(id);

        System.out.println("Success deleted!");
    }

    @ShellMethod(value = "Create new book", key = {"c", "create"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void createNewBook(@ShellOption String bookName, @ShellOption String publishDate,
                              @ShellOption String authorId, @ShellOption String genreId) {
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
    public void updateBook(@ShellOption String bookId, @ShellOption String bookName,
                           @ShellOption String publishDate, @ShellOption String authorId, @ShellOption String genreId) {
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

    @ShellMethod(value = "Show all comments by book ID", key = {"s-c-b", "show-comments-book"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void showAllCommentsByBook(String bookId) {
        System.out.println(Arrays.deepToString(commentsService.getAllCommentsByBook(bookId).toArray()));
    }

    @ShellMethod(value = "Show all books by author ID", key = {"s-a-a", "show-all-author"})
    @ShellMethodAvailability(value = "isLibraryCommandAvailable")
    @Override
    public void showAllAuthorBooks(String authorId) {
        System.out.println(Arrays.deepToString(booksService.getAllBooksByAuthor(authorId).toArray()));
    }

    private Availability isLibraryCommandAvailable() {
        return userName == null ? Availability.unavailable("Login first") : Availability.available();
    }*/
}
