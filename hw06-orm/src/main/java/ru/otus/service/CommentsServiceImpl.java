package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Comment;
import ru.otus.repository.CommentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentsServiceImpl implements CommentsService {
    private final CommentsRepository commentsRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Comment> getAllComments() {
        return commentsRepository.getAll();
    }
}
