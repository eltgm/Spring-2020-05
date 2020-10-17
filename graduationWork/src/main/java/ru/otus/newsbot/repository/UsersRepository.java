package ru.otus.newsbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.newsbot.domain.NewsUser;

import java.util.Optional;

public interface UsersRepository extends MongoRepository<NewsUser, String> {
    Optional<NewsUser> findByUsername(String username);
}
