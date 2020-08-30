package ru.otus.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.domain.User;

public interface UsersRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
