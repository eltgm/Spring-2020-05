package ru.otus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RouterFunction;
import ru.otus.domain.*;

import java.util.List;

@DisplayName("Тест роутера")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureDataMongo
public class BookRouteTest {
    @Autowired
    private RouterFunction route;
    @Autowired
    private MongoTemplate template;

    @Test
    @DisplayName("должен получить все книги")
    public void testGetAllBooks() {
        final var client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/api/book")
                .exchange()
                .expectBody()
                .json("[{\"id\":\"1\",\"name\":\"Animal Farm\",\"publishDate\":\"17.08.1945\",\"author\":{\"id\":\"1\",\"name\":\"George Orwell\"},\"genre\":{\"id\":\"2\",\"name\":\"Satire\"}},{\"id\":\"2\",\"name\":\"Nineteen Eighty-Four\",\"publishDate\":\"8.06.1949\",\"author\":{\"id\":\"1\",\"name\":\"George Orwell\"},\"genre\":{\"id\":\"1\",\"name\":\"Social science fiction\"}},{\"id\":\"3\",\"name\":\"Ostatnie życzenie\",\"publishDate\":\"1996\",\"author\":{\"id\":\"2\",\"name\":\"Andrew Sapkowski\"},\"genre\":{\"id\":\"3\",\"name\":\"Fantasy\"}}]");
    }

    @Test
    @DisplayName("должен получить книгу")
    public void testGetBook() {
        final var client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.get()
                .uri("/api/book/1")
                .exchange()
                .expectBody()
                .json("{\"id\":\"1\",\"name\":\"Animal Farm\",\"publishDate\":\"17.08.1945\",\"author\":{\"id\":\"1\",\"name\":\"George Orwell\"},\"genre\":{\"id\":\"2\",\"name\":\"Satire\"}}");
    }

    @Test
    @DisplayName("должен создать новую книгу")
    public void testPostBook() {
        final var build = BookDto.builder()
                .name("testbook")
                .publishDate("01.01.1970")
                .author("Test Author")
                .genre("Test Genre")
                .build();

        final var client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.post()
                .uri("/api/book")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(build))
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    @DisplayName("должен обновить книгу")
    public void testPutBook() {
        final var build = BookDto.builder()
                .id("1")
                .name("testbook")
                .publishDate("01.01.1970")
                .author("Test Author")
                .genre("Test Genre")
                .build();

        final var client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.put()
                .uri("/api/book")
                .body(BodyInserters.fromValue(build))
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    @DisplayName("должен удалить книгу")
    public void testDeleteBook() {
        final var client = WebTestClient
                .bindToRouterFunction(route)
                .build();

        client.delete()
                .uri("/api/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }

    @BeforeEach
    void initdb() {
        template.dropCollection(Book.class);
        template.dropCollection(Comment.class);
        template.dropCollection(Genre.class);
        template.dropCollection(Author.class);

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
}
