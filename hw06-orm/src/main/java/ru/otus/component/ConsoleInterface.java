package ru.otus.component;

public interface ConsoleInterface {
    void showAllBooks();

    void showBookById(long id);

    void deleteBookById(long id);

    void createNewBook(String bookName, String publishDate, long authorId, long genreId);

    void updateBook(long bookId, String bookName, String publishDate, long authorId, long genreId);

    void showAllComments();
}
