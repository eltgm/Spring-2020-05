package ru.otus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.domain.Book;
import ru.otus.domain.BookDto;
import ru.otus.repository.AuthorsRepository;
import ru.otus.repository.BooksRepository;
import ru.otus.repository.CommentsRepository;

import java.net.URI;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.created;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;


@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public RouterFunction<ServerResponse> composedRoutes(BooksRepository booksRepository, AuthorsRepository authorsRepository,
                                                         CommentsRepository commentsRepository) {
        return route()
                .GET("/api/book", request -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(booksRepository.findAll(), Book.class))
                .GET("/api/book/{id}", request -> booksRepository.findById(request.pathVariable("id"))
                        .flatMap(book -> ok()
                                .contentType(APPLICATION_JSON)
                                .body(fromValue(book))))
                .POST("/api/book", contentType(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .flatMap(bookDto -> booksRepository.save(bookDto.toPojo()))
                                .flatMap(saved -> created(URI.create("/api/book/" + saved.getId())).build()))
                .PUT("/api/book", contentType(APPLICATION_JSON),
                        request -> request.bodyToMono(BookDto.class)
                                .flatMap(bookDto -> booksRepository.save(bookDto.toPojo()))
                                .flatMap(saved -> ok().build()))
                .DELETE("/api/book/{id}",
                        request -> booksRepository.deleteById(request.pathVariable("id"))
                                .flatMap(book -> ok()
                                        .build()))

                .GET("/api/author", request -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(authorsRepository.findAll(), Book.class))
                .GET("/api/author/{authorId}/book",
                        request -> ok()
                                .contentType(APPLICATION_JSON)
                                .body(booksRepository.findAllByAuthor_Id(request.pathVariable("authorId")), Book.class))

                .GET("/api/comment", request -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(commentsRepository.findAll(), Book.class))
                .GET("/api/comment/{bookId}", request -> ok()
                        .contentType(APPLICATION_JSON)
                        .body(commentsRepository.findAllByBookId(request.pathVariable("bookId")), Book.class))
                .build();
    }
}
