package ru.otus.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.domain.Comment;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с комментариями:")
@DataJpaTest
@Import(CommentsRepositoryImpl.class)
class CommentsRepositoryImplTest {
    @Autowired
    private CommentsRepositoryImpl commentsRepository;
    @Autowired
    private TestEntityManager em;

    @DisplayName("должен вернуть все комментарии")
    @Test
    void getAll() {
        final var comments = commentsRepository.getAll();

        assertThat(comments).hasSize(4)
                .allMatch(comment -> comment.getUserName() != null)
                .allMatch(comment -> comment.getText() != null);
    }

    @DisplayName("должен добавить комментарий в бд")
    @Test
    void create() {
        var newComment = Comment.builder()
                .bookId(1L)
                .userName("eltgm")
                .text("Вау!!")
                .build();

        commentsRepository.create(newComment);

        final var addedComment = em.find(Comment.class, newComment.getId());

        assertThat(addedComment)
                .matches(comment -> comment.getText().equals("Вау!!"))
                .matches(comment -> comment.getUserName().equals("eltgm"));
    }

    @DisplayName("должен удалить комментарий")
    @Test
    void deleteById() {
        commentsRepository.deleteById(1);

        final var comments = commentsRepository.getAll();

        assertThat(comments).hasSize(3);
    }

    @DisplayName("должен вывести все комментарии к одной книге")
    @Test
    void getAllByBookId() {
        final var comments = commentsRepository.getAllByBookId(2);

        assertThat(comments).hasSize(2)
                .anyMatch(comment -> comment.getUserName().equals("Andrew") && comment.getText().equals("test2"))
                .anyMatch(comment -> comment.getUserName().equals("George") && comment.getText().equals("test3"));
    }
}