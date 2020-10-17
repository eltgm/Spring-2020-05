package ru.otus.newsbot.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.newsbot.domain.Channel;

public interface ChannelsRepository extends MongoRepository<Channel, String> {
}
