package ru.otus.component;

public interface ConsoleInterface {
    void showAllBooks();

    void showBookById(String id);

    void deleteBookById(String id);

    void createNewBook(String bookName, String publishDate, String authorId, String genreId);

    void updateBook(String bookId, String bookName, String publishDate, String authorId, String genreId);

    void showAllComments();

    void showAllCommentsByBook(String bookId);

    void showAllAuthorBooks(String authorId);
}
