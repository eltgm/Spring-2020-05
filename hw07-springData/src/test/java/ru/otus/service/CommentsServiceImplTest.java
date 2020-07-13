package ru.otus.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями:")
@DataJpaTest
@Import(CommentsServiceImpl.class)
class CommentsServiceImplTest {
    @Autowired
    private CommentsServiceImpl commentsService;
    @Autowired
    private TestEntityManager em;

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
        final var allComments = commentsService.getAllCommentsByBook(2);

        assertThat(allComments).hasSize(2)
                .anyMatch(comment -> comment.getUserName().equals("Andrew") && comment.getText().equals("test2"))
                .anyMatch(comment -> comment.getUserName().equals("George") && comment.getText().equals("test3"));
    }
}