package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentsRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;

    @SneakyThrows
    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "comments", fallbackMethod = "buildFallbackComments")
    public List<Comment> getAllComments() {
        Thread.sleep(3001);
        return commentsRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "comments", fallbackMethod = "buildFallbackComments")
    public List<Comment> getAllCommentsByBook(String bookId) {
        return commentsRepository.findAllByBookId(bookId);
    }

    public List<Comment> buildFallbackComments() {
        return Collections.singletonList(Comment.builder()
                .text("text")
                .userName("userName")
                .build());
    }
}
