package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.domain.Comment;
import ru.otus.service.CommentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {
    private final CommentsService commentsService;

    @Override
    @GetMapping("/api/comment")
    public List<Comment> showComments() {
        return commentsService.getAllComments();
    }

    @Override
    @GetMapping("/api/comment/{bookId}")
    public List<Comment> showCommentsByBookId(@PathVariable(name = "bookId") String bookId) {
        return commentsService.getAllCommentsByBook(bookId);
    }
}
