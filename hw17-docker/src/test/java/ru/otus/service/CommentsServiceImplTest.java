package ru.otus.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.domain.Comment;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями:")
@DataMongoTest
@Import(CommentsServiceImpl.class)
class CommentsServiceImplTest {
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private MongoTemplate template;

    @BeforeEach
    void initdb() {
        var com1 = new Comment("1", "1", "Vladislav", "test");
        var com2 = new Comment("2", "2", "Andrew", "test2");
        var com3 = new Comment("3", "2", "George", "test3");
        var com4 = new Comment("4", "3", "Arthur", "test4");

        template.insertAll(List.of(com1, com2, com3, com4));
    }

    @AfterEach
    void clear() {
        template.dropCollection(Comment.class);
    }

    @DisplayName("должен вернуть все комментарии")
    @Test
    void getAllComments() {
        final var allComments = commentsService.getAllComments();

        assertThat(allComments).hasSize(4)
                .allMatch(comment -> comment.getUserName() != null)
                .allMatch(comment -> comment.getText() != null);
    }

    @DisplayName("должен вернуть все комментарии для книги")
    @Test
    void getAllCommentsByBook() {
        final var allComments = commentsService.getAllCommentsByBook("2");

        assertThat(allComments).hasSize(2)
                .anyMatch(comment -> comment.getUserName().equals("Andrew") && comment.getText().equals("test2"))
                .anyMatch(comment -> comment.getUserName().equals("George") && comment.getText().equals("test3"));
    }
}