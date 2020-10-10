package ru.otus.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.service.AuthorsServiceImpl;
import ru.otus.service.BooksServiceImpl;
import ru.otus.service.UsersServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Import({UsersServiceImpl.class, AuthorsServiceImpl.class, BooksServiceImpl.class})
@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookControllerImpl.class)
@AutoConfigureDataMongo
class BookControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Должен позволить админу удалить книгу и сделать redirect")
    @SneakyThrows
    @WithMockUser(
            username = "admin",
            roles = {"ADMIN"}
    )
    public void testAdminDelete() {
        mvc.perform(get("/book/delete/1"))
                .andExpect(status().isFound());
    }
}