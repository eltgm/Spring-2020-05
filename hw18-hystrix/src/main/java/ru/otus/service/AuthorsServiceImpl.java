package ru.otus.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.domain.Author;
import ru.otus.repository.AuthorsRepository;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorsServiceImpl implements AuthorsService {
    private final AuthorsRepository authorsRepository;

    @Override
    @Transactional(readOnly = true)
    @HystrixCommand(commandKey = "authors", fallbackMethod = "buildFallbackAuthors")
    public List<Author> getAllAuthors() {
        return authorsRepository.findAll();
    }

    public List<Author> buildFallbackAuthors() {
        return Collections.singletonList(Author.builder()
                .name("name")
                .build());
    }
}
