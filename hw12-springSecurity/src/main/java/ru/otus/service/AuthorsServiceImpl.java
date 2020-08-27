package ru.otus.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsServiceImpl implements AuthorsService {
    private final AuthorsRepository authorsRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorsRepository.findAll();
    }
}
