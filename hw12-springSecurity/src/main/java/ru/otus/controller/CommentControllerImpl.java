package ru.otus.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.otus.domain.Comment;
import ru.otus.service.CommentsService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentControllerImpl implements CommentController {
    private final CommentsService commentsService;

    @Override
    @GetMapping("/comment")
    public String showComments(Model model, @RequestParam(name = "bookId", required = false) String bookId) {
        List<Comment> allCommentsByBook;
        if (!StringUtils.isEmpty(bookId)) {
            model.addAttribute("bookId", bookId);
            allCommentsByBook = commentsService.getAllCommentsByBook(bookId);
        } else {
            model.addAttribute("bookId", "");
            allCommentsByBook = commentsService.getAllComments();
        }

        model.addAttribute("comments", allCommentsByBook);
        return "comments";
    }
}
