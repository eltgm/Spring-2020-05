package otus.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.domain.Genre;
import ru.otus.service.BooksServiceImpl;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с книгами:")
@DataMongoTest
@Import(BooksServiceImpl.class)
class BooksServiceImplTest {
    @Autowired
    private BooksServiceImpl booksService;
    @Autowired
    private MongoTemplate template;

    @BeforeEach
    void initdb() {
        var social = template.save(new Genre("1", "Social science fiction"));
        var satire = template.save(new Genre("2", "Satire"));
        var fantasy = template.save(new Genre("3", "Fantasy"));
        template.save(new Genre("4", "Aventure"));

        var orwell = template.save(new Author("1", "George Orwell"));
        var sapkowski = template.save(new Author("2", "Andrew Sapkowski"));

        var com1 = new Comment("1", "1", "Vladislav", "test");
        var com2 = new Comment("2", "2", "Andrew", "test2");
        var com3 = new Comment("3", "2", "George", "test3");
        var com4 = new Comment("4", "3", "Arthur", "test4");

        template.insertAll(List.of(com1, com2, com3, com4));

        var doc1 = new Book("1", "Animal Farm", "17.08.1945", orwell, satire);
        var doc2 = new Book("2", "Nineteen Eighty-Four", "8.06.1949", orwell, social);
        var doc3 = new Book("3", "Ostatnie życzenie", "1996", sapkowski, fantasy);

        template.insertAll(List.of(doc1, doc2, doc3));
    }

    @AfterEach
    void clear() {
        template.dropCollection(Book.class);
        template.dropCollection(Comment.class);
        template.dropCollection(Genre.class);
        template.dropCollection(Author.class);
    }

    @DisplayName("должен вернуть все книги")
    @Test
    void getAllBooks() {
        final var all = booksService.getAllBooks();

        assertThat(all).isNotNull().hasSize(3)
                .allMatch(book -> book.getAuthor() != null)
                .allMatch(book -> !book.getName().isEmpty())
                .allMatch(book -> !book.getPublishDate().isEmpty())
                .allMatch(book -> book.getGenre() != null);
    }

    @DisplayName("должен вернуть книгу по id")
    @Test
    void getBookById() {
        final var book = booksService.getBookById("2");

        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Nineteen Eighty-Four"))
                .matches(book1 -> book1.getAuthor().getName().equals("George Orwell"))
                .matches(book1 -> book1.getGenre().getName().equals("Social science fiction"));
    }

    @DisplayName("должен удалить книгу")
    @Test
    void deleteBookById() {
        booksService.deleteBookById("1");

        final var book = booksService.getBookById("1");

        assertThat(book).isEqualTo(Book.builder().build());
    }

    @DisplayName("должен добавить книгу в бд")
    @Test
    void createNewBook() {
        final var author = Author.builder()
                .id("2")
                .build();
        final var genre = Genre.builder()
                .id("4")
                .build();
        final var newBook = Book.builder()
                .id("4")
                .name("Miecz przeznaczenia")
                .publishDate("1992")
                .author(author)
                .genre(genre)
                .build();

        booksService.createNewBook(newBook);
        final var book = template.findOne(new Query(Criteria.where("_id").is("4")), Book.class);
        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Miecz przeznaczenia"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Aventure"));
    }

    @DisplayName("должен обновить книгу в бд")
    @Test
    void updateBook() {
        var author = new Author("2", "Andrew Sapkowski");
        var genre = new Genre("3", "Fantasy");

        final var updatedBook = Book.builder()
                .id("3")
                .name("Wiedźmin")
                .publishDate("1986")
                .author(author)
                .genre(genre)
                .build();
        booksService.updateBook(updatedBook);

        final var book = template.findOne(new Query(Criteria.where("_id").is("3")), Book.class);
        assertThat(book).isNotNull()
                .matches(book1 -> book1.getName().equals("Wiedźmin"))
                .matches(book1 -> book1.getAuthor().getName().equals("Andrew Sapkowski"))
                .matches(book1 -> book1.getGenre().getName().equals("Fantasy"))
                .matches(book12 -> book12.getPublishDate().equals("1986"));
    }

    @DisplayName("должен получить все книги автора")
    @Test
    void getAllBooksByAuthor() {
        final var books = booksService.getAllBooksByAuthor("1");

        assertThat(books).hasSize(2)
                .anyMatch(book -> book.getName().equals("Animal Farm") && book.getPublishDate().equals("17.08.1945"))
                .anyMatch(book -> book.getName().equals("Nineteen Eighty-Four") && book.getPublishDate().equals("8.06.1949"));
    }
}