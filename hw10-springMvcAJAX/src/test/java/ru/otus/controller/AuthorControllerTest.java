package ru.otus.controller;

import lombok.SneakyThrows;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.domain.Author;
import ru.otus.domain.Book;
import ru.otus.domain.Genre;
import ru.otus.service.AuthorsService;
import ru.otus.service.AuthorsServiceImpl;
import ru.otus.service.BooksService;
import ru.otus.service.BooksServiceImpl;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorControllerImpl.class)
@Import({BooksServiceImpl.class, AuthorsServiceImpl.class})
@AutoConfigureDataMongo
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private AuthorsService authorsService;
    @MockBean
    private BooksService booksService;

    @SneakyThrows
    @Test
    void showAllAuthors() {
        when(authorsService.getAllAuthors())
                .then((Answer<List<Author>>) invocationOnMock
                        -> Collections.singletonList(new Author("1", "Arthur")));

        this.mvc.perform(get("/api/author"))
                .andExpect(status().isOk())
                .andExpect(content().string(new Matcher<>() {
                    @Override
                    public boolean matches(Object o) {
                        return ((String) o).contains("Arthur");
                    }

                    @Override
                    public void describeMismatch(Object o, Description description) {

                    }

                    @Override
                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

                    }

                    @Override
                    public void describeTo(Description description) {

                    }
                }));
    }

    @SneakyThrows
    @Test
    void showAllAuthorBooks() {
        when(booksService.getAllBooksByAuthor(anyString()))
                .then((Answer<List<Book>>) invocationOnMock
                        -> Collections.singletonList(Book.builder()
                        .name("newBook")
                        .id("1")
                        .author(new Author("1", "Author"))
                        .genre(new Genre("1", "Genre"))
                        .publishDate("2020")
                        .build()));

        this.mvc.perform(get("/api/author/1/book"))
                .andExpect(status().isOk())
                .andExpect(content().string(new Matcher<>() {
                    @Override
                    public boolean matches(Object o) {
                        return ((String) o).contains("newBook");
                    }

                    @Override
                    public void describeMismatch(Object o, Description description) {

                    }

                    @Override
                    public void _dont_implement_Matcher___instead_extend_BaseMatcher_() {

                    }

                    @Override
                    public void describeTo(Description description) {

                    }
                }));
    }
}