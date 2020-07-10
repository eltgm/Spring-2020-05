package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Book;
import ru.otus.domain.Comment;
import ru.otus.repository.BooksRepository;
import ru.otus.repository.CommentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;
    private final BooksRepository booksRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllComments() {
        return commentsRepository.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllCommentsByBook(long bookId) {
        final var comments = booksRepository.findById(bookId).orElseGet(Book::new).getComments();
        comments.isEmpty();
        return comments;
    }
}
